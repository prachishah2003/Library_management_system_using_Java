package com.ibizabroker.lms.entity;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String name;
    private String password;
    private String address;
}

