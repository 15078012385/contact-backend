package com.aliyun.service.impl;
import java.util.List;
import com.aliyun.entity.Contact;
import com.aliyun.dao.ContactMapper;
import com.aliyun.service.ContactService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * <p>
 * 通讯录表 服务实现类
 * </p>
 *
 * @author 啊飞-321395678@qq.com
 * @since 2024-10-25
 */
@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements ContactService {

//      根据实体类任意字段查询
        List<Contact> queryByEntity(Contact entity) {
        return baseMapper.selectList(new QueryWrapper<>(entity));
        }
        //      统计记录数
        public int countRecords() {
        return Math.toIntExact(baseMapper.selectCount(null));
        }
}
