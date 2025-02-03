package com.teste.order_service.infra.persistence;

import com.teste.order_service.core.domain.enums.StatusOrder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Document(collection = "orders")
public class OrderEntity {

    @MongoId(targetType = FieldType.STRING)
    private String id;

    @Field(targetType = FieldType.ARRAY)
    private List<String> productsId;

    @Field(targetType = FieldType.STRING)
    private StatusOrder status;

    @Field(targetType = FieldType.DATE_TIME)
    private LocalDateTime createdAt;

    @Field(targetType = FieldType.DATE_TIME)
    private LocalDateTime updatedAt;

    public OrderEntity() {
    }

    public OrderEntity(String id, List<String> productsId, StatusOrder status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.productsId = productsId;
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

    public List<String> getProductsId() {
        return productsId;
    }

    public void setProductsId(List<String> productsId) {
        this.productsId = productsId;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public void setStatus(StatusOrder status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(productsId, that.productsId) && status == that.status && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productsId, status, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id='" + id + '\'' +
                ", productsId=" + productsId +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
