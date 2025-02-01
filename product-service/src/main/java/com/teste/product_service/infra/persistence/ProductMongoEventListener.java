package com.teste.product_service.infra.persistence;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class ProductMongoEventListener extends AbstractMongoEventListener<ProductEntity> {

    private final ProductRepository productRepository;

    public ProductMongoEventListener(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<ProductEntity> event) {
        ProductEntity productEntity = event.getSource();

        // Verifica se é uma nova entidade (sem ID)
        if (productEntity.getId() == null) {
            productEntity.setCode(generateCode());
            productEntity.setCreatedAt(LocalDateTime.now());
            productEntity.setUpdatedAt(LocalDateTime.now());
            productEntity.setActive(true);
        } else {
            // Apenas atualiza o `updatedAt` em updates
            productEntity.setUpdatedAt(LocalDateTime.now());
        }
    }

    private String generateCode() {
        String code;
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 8;

        do {
            code = random.ints(0, characters.length())
                    .limit(length)
                    .collect(
                            StringBuilder::new,
                            (sb, i) -> sb.append(characters.charAt(i)),
                            StringBuilder::append
                    )
                    .toString();
        } while (productRepository.existsByCode(code));

        return code;
    }
}
