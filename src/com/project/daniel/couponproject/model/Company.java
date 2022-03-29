package com.project.daniel.couponproject.model;

import lombok.*;

import java.util.List;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Company {
    public Company(final String name, final String email, final String password) {
        this.id = null;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Company(final Long id, final String name, final String email, final String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Company(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    private Long id;
    private String name;
    private String email;
    private String password;
    private List<Coupon> couponList;


}
