package com.coffee.dto;

import com.coffee.constant.Role;
import com.coffee.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberListDto {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String password;
    private Role role;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;



    public MemberListDto(Member entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.address = entity.getAddress();
        this.password = entity.getPassword();
        this.role = entity.getRole();
        this.regTime = entity.getRegTime();
        this.updateTime = entity.getUpdateTime();

    }
}
