package com.aliyun.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordVO {
    private Integer userId;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}