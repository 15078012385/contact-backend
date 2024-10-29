package com.aliyun.service.impl;
import java.util.List;
import com.aliyun.entity.Users;
import com.aliyun.dao.UsersMapper;
import com.aliyun.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 啊飞-321395678@qq.com
 * @since 2024-05-05
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

//      根据实体类任意字段查询
        List<Users> queryByEntity(Users entity) {
        return baseMapper.selectList(new QueryWrapper<>(entity));
        }
        //      统计记录数
        public int countRecords() {
        return Math.toIntExact(baseMapper.selectCount(null));
        }
}
