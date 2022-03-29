package com.project.daniel.couponproject.model;

import com.project.daniel.couponproject.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Coupon {
    private Long id;
    private Long companyId;
    private Category Category;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int amount;
    private double price;
    private String image;

    public Coupon(final Category Category, final String title, final String description, final LocalDate startDate,
                  final LocalDate endDate, final int amount, final double price, final String image) {
        this.Category = Category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    public Coupon(final Long companyId, final Category Category, final String title, final String description,
                  final LocalDate startDate, final LocalDate endDate, final int amount,
                  final double price, final String image) {
        this.companyId = companyId;
        this.Category = Category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

}
