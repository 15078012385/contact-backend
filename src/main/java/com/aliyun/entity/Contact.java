package com.aliyun.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 通讯录表
 * </p>
 *
 * @author 啊飞-321395678@qq.com
 * @since 2024-10-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("tb_contact")
public class Contact implements Serializable {


    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 联系人姓名
     */
    private String name;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 生日
     */
    private LocalDateTime birthday;

    /**
     * 公司名称
     */
    private String company;

    /**
     * 职位
     */
    private String position;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 状态：1-正常 0-已删除
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime created;

    /**
     * 更新时间
     */
    private LocalDateTime updated;


}
