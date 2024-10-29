package com.aliyun.entity;

import lombok.*;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 啊飞-321395678@qq.com
 * @since 2024-05-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users implements Serializable {


    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 创建时间
     */
    private LocalDate createdAt;

    /**
     * 最后登录
     */
    private LocalDateTime lastLoginTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatetime;

    /**
     * 状态
     */
    private Integer status;


}
