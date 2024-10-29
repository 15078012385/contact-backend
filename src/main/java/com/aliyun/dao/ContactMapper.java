package com.aliyun.dao;

import com.aliyun.entity.Contact;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 通讯录表 Mapper 接口
 * </p>
 *
 * @author 啊飞-321395678@qq.com
 * @since 2024-10-25
 */
@Mapper
public interface ContactMapper extends BaseMapper<Contact> {

}
