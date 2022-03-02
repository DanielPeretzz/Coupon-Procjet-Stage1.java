package com.project.daniel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Coupon> couponList = new ArrayList<>();

    public Customer(Long id, String firstName, String lastName, String email, final String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
    public Customer( String firstName, String lastName, String email, final String password) {
        this.id = null;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }


}
