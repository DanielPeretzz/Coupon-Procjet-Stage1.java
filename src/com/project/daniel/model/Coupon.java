package com.project.daniel.model;

import com.project.daniel.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

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

    public Coupon(Category Category, String title, String description, LocalDate startDate, LocalDate endDate, int amount, double price, String image) {
        this.Category = Category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    public Coupon(Long companyId, Category Category, String title, String description, LocalDate startDate, LocalDate endDate, int amount, double price, String image) {
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
