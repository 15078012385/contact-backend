package com.aliyun.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.aliyun.utils.StringUtils;
import com.aliyun.utils.Result;
import com.aliyun.utils.ResultCodeEnum;
import java.lang.reflect.Field;
import javax.annotation.Resource;
import java.util.List;
//增加entity对应的包
import com.aliyun.entity.Contact;
import com.aliyun.service.ContactService;
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
 * 通讯录表 前端控制器
 * </p>
 *
 * @author 啊飞-321395678@qq.com
 * @since 2024-10-25
 */
@RestController
@RequestMapping("/contact")
public class ContactController {

@Resource
private ContactService contactService;

// 新增或者更新
@PostMapping("/save_or_update")
public Result save(@RequestBody Contact contact){
        contactService.saveOrUpdate(contact);
    return Result.success(ResultCodeEnum.SUCCESS);
}
// 批量更新
@PostMapping("/update/batch")
public Result updateBatch(@RequestBody List<Contact> entityList) {
    boolean success = contactService.updateBatchById(entityList);
    if (success) {
        return Result.success(ResultCodeEnum.SUCCESS);
    } else {
        return Result.error(ResultCodeEnum.INTERNAL_SERVER_ERROR);
    }
}

//通过id删除
@DeleteMapping("/{id}")
public Result delete(@PathVariable Integer id) {
        contactService.removeById(id);
    return Result.success(ResultCodeEnum.SUCCESS);
}

//批量删除
@PostMapping("/del/batch")
public Result deleteBatch(@RequestBody List<Integer> ids) {
        contactService.removeByIds(ids);
    return Result.success(ResultCodeEnum.SUCCESS);
}

//查全部
@GetMapping("/find_all")
public Result findAll() {
    return Result.success(contactService.list());
}

//通过id查
@GetMapping("/{id}")
public Result findOne(@PathVariable Integer id) {
    return Result.success(contactService.getById(id));
}

//分页查询
@RequestMapping("/page_by_entity")
public Result findPage(@RequestParam(defaultValue = "1")  Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize, @RequestBody Contact entity) {
    //QueryWrapper是增加条件查询
    QueryWrapper<Contact> queryWrapper = new QueryWrapper<>();
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
    return Result.success(contactService.page(new Page<>(pageNum, pageSize), queryWrapper));
}

/**
* 导出接口
*/
@GetMapping("/export")
public void export(HttpServletResponse response) throws Exception {
    // 从数据库查询出所有的数据
    List<Contact> list = contactService.list();
    // 在内存操作，写出到浏览器
    ExcelWriter writer = ExcelUtil.getWriter(true);

    // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
    writer.write(list, true);

    // 设置浏览器响应的格式
    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
    String fileName = URLEncoder.encode("Contact信息表", "UTF-8");
    response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

    ServletOutputStream out = response.getOutputStream();
    writer.flush(out, true);
    out.close();
    writer.close();

}

/**
 * excel 导入
 * @param file
 * @throws Exception
 */
@PostMapping("/import")
public Result imp(MultipartFile file) throws Exception {
    InputStream inputStream = file.getInputStream();
    ExcelReader reader = ExcelUtil.getReader(inputStream);
    // 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
    List<Contact> list = reader.readAll(Contact.class);

        contactService.saveBatch(list);
    return Result.success(ResultCodeEnum.SUCCESS);
}

}

