package com.teste.product_service.infra.persistence;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Document(collection = "products")
public class ProductEntity {

    @MongoId(FieldType.STRING)
    private String id;

    @Field(targetType = FieldType.STRING)
    private String name;

    @Field(targetType = FieldType.STRING)
    private String description;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal price;

    @Field(targetType = FieldType.STRING)
    private String code;

    @Field(targetType = FieldType.INT64)
    private Integer stock;

    @Field(targetType = FieldType.DATE_TIME)
    private LocalDateTime createdAt;

    @Field(targetType = FieldType.DATE_TIME)
    private LocalDateTime updatedAt;

    @Field(targetType = FieldType.DATE_TIME)
    private LocalDateTime deactivatedAt;

    @Field(targetType = FieldType.BOOLEAN)
    private Boolean active;

    public ProductEntity() {
    }

    public ProductEntity(String id, String name, String description, BigDecimal price, String code, Integer stock, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deactivatedAt, Boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.code = code;
        this.stock = stock;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deactivatedAt = deactivatedAt;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeactivatedAt() {
        return deactivatedAt;
    }

    public void setDeactivatedAt(LocalDateTime deactivatedAt) {
        this.deactivatedAt = deactivatedAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(code, that.code) && Objects.equals(stock, that.stock) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(deactivatedAt, that.deactivatedAt) && Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, code, stock, createdAt, updatedAt, deactivatedAt, active);
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", code='" + code + '\'' +
                ", stock=" + stock +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deactivatedAt=" + deactivatedAt +
                ", active=" + active +
                '}';
    }
}
