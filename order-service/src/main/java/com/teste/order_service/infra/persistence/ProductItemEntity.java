package com.teste.order_service.infra.persistence;

import java.math.BigDecimal;

public class ProductItemEntity {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String code;

    public ProductItemEntity() {
    }

    public ProductItemEntity(String id, String name, String description, BigDecimal price, String code) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCode() {
        return code;
    }


}
