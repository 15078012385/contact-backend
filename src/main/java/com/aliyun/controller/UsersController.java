package com.aliyun.controller;


import com.aliyun.entity.PasswordVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;
import com.aliyun.utils.StringUtils;
import com.aliyun.utils.Result;
import com.aliyun.utils.ResultCodeEnum;

import java.lang.reflect.Field;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
//增加entity对应的包
import com.aliyun.entity.Users;
import com.aliyun.service.UsersService;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import java.io.InputStream;
import javax.servlet.ServletOutputStream;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 啊飞-321395678@qq.com
 * @since 2024-05-05
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    @Resource
    private UsersService usersService;

    // 修改密码接口
    @PostMapping("/change_password")
    public Result changePassword(@RequestBody PasswordVO passwordVO) {
        Integer userId = passwordVO.getUserId();
        String currentPassword = passwordVO.getCurrentPassword();
        String newPassword = passwordVO.getNewPassword();
        String confirmPassword = passwordVO.getConfirmPassword();

        // 检查新密码和确认密码是否一致
        if (!newPassword.equals(confirmPassword)) {
            return Result.error(ResultCodeEnum.BAD_REQUEST, "新密码和确认密码不一致");
        }

        // 查询用户信息
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("password", currentPassword);
        Users user = usersService.getOne(wrapper);

        // 判断用户是否存在且密码正确
        if (user != null) {
            // 更新密码
            user.setPassword(newPassword);
            usersService.updateById(user);
            return Result.success(ResultCodeEnum.SUCCESS);
        } else {
            return Result.error(ResultCodeEnum.BAD_REQUEST, "原密码错误");
        }
    }

    @RequestMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("password", password);
        Users user = usersService.getOne(wrapper);
        if (user != null) {
            // 判断状态
            if (user.getStatus() == 0) {
                user.setLastLoginTime(LocalDateTime.now());
                // 更新用户信息
                usersService.updateById(user);
                return Result.success(user);
            } else {
                return Result.error(ResultCodeEnum.BAD_REQUEST, "已被封禁，讨逆猴。");
            }
        } else {
            return Result.error(ResultCodeEnum.BAD_REQUEST, "账号或密码错误 ！！套你猴子 ");
        }
    }

    @PostMapping("/register")
    public Result register(@RequestBody Users users) {
        // 检查用户名是否已存在
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username", users.getUsername());
        Users existingUser = usersService.getOne(wrapper);

        if (existingUser != null) {
            return Result.error(ResultCodeEnum.BAD_REQUEST, "用户名已存在");
        }
        users.setEmail("default_email@qq.com");
        // 设置创建时间和更新时间
        users.setCreatedAt(LocalDate.now());
        users.setUpdatetime(LocalDateTime.now());
        // 保存用户信息到数据库
        usersService.save(users);
        return Result.success(ResultCodeEnum.SUCCESS);
    }

    // 新增或者更新
    @PostMapping("/save_or_update")
    public Result save(@RequestBody Users users) {
        users.setUpdatetime(LocalDateTime.now());
        usersService.saveOrUpdate(users);
        return Result.success(ResultCodeEnum.SUCCESS);
    }

    // 批量更新
    @PostMapping("/update/batch")
    public Result updateBatch(@RequestBody List<Users> entityList) {
        boolean success = usersService.updateBatchById(entityList);
        if (success) {
            return Result.success(ResultCodeEnum.SUCCESS);
        } else {
            return Result.error(ResultCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }

    //通过id删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        usersService.removeById(id);
        return Result.success(ResultCodeEnum.SUCCESS);
    }

    //批量删除
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        usersService.removeByIds(ids);
        return Result.success(ResultCodeEnum.SUCCESS);
    }

    //查全部
    @GetMapping("/find_all")
    public Result findAll() {
        return Result.success(usersService.list());
    }

    //通过id查
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(usersService.getById(id));
    }

    //分页查询
    @RequestMapping("/page_by_entity")
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize, @RequestBody Users entity) {
        //QueryWrapper是增加条件查询
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        // 全字段模糊查询
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                if (value != null) {
                    queryWrapper.like(StringUtils.humpToLine(field.getName()), value.toString().trim());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return Result.success(usersService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Users> list = usersService.list();
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("Users信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * excel 导入
     *
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        List<Users> list = reader.readAll(Users.class);

        usersService.saveBatch(list);
        return Result.success(ResultCodeEnum.SUCCESS);
    }

}

