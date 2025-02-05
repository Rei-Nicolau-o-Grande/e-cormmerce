package com.teste.product_service.infra.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, String> {

    boolean existsByCode(String code);

    List<ProductEntity> findAllByActiveTrueAndStockGreaterThan(int stock);
}
