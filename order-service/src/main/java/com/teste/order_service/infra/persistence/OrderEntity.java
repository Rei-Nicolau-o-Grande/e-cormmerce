package com.teste.order_service.infra.persistence;

import com.teste.order_service.core.domain.enums.StatusOrder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Objects;

@Document(collection = "orders")
public class OrderEntity {

    @MongoId(targetType = FieldType.STRING)
    private String id;

    @Field(targetType = FieldType.IMPLICIT)
    private ProductItemEntity productItem;

    @Field(targetType = FieldType.STRING)
    private StatusOrder status;

    @Field(targetType = FieldType.DATE_TIME)
    private String createdAt;

    @Field(targetType = FieldType.DATE_TIME)
    private String updatedAt;

    public OrderEntity() {
    }

    public OrderEntity(String id, ProductItemEntity productItem, StatusOrder status, String createdAt, String updatedAt) {
        this.id = id;
        this.productItem = productItem;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductItemEntity getProductItem() {
        return productItem;
    }

    public void setProductItem(ProductItemEntity productItem) {
        this.productItem = productItem;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public void setStatus(StatusOrder status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(productItem, that.productItem) && status == that.status && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productItem, status, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id='" + id + '\'' +
                ", productItem=" + productItem +
                ", status=" + status +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
