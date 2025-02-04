package com.teste.product_service.infra.controllers;

import com.teste.product_service.core.domain.entities.Product;
import com.teste.product_service.core.usecase.CreateProductUseCase;
import com.teste.product_service.core.usecase.DeactiveProductUseCase;
import com.teste.product_service.core.usecase.FindProductByIdUseCase;
import com.teste.product_service.infra.dtos.ProductRequestDto;
import com.teste.product_service.infra.dtos.ProductResponseDto;
import com.teste.product_service.infra.mapper.ProductDtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final CreateProductUseCase createProductUseCase;
    private final FindProductByIdUseCase findProductByIdUseCase;
    private final DeactiveProductUseCase deactiveProductUseCase;

    public ProductController(CreateProductUseCase createProductUseCase,
                             FindProductByIdUseCase findProductByIdUseCase,
                             DeactiveProductUseCase deactiveProductUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.findProductByIdUseCase = findProductByIdUseCase;
        this.deactiveProductUseCase = deactiveProductUseCase;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        Product product = createProductUseCase.execute(ProductDtoMapper.toDomain(productRequestDto));
        URI locationProduct = URI.create(String.format("/api/v1/product/%s", product.id()));
        log.info("Request received - Endpoint: POST /api/v1/product, Payload: {}", product);
        return ResponseEntity.created(locationProduct).body(ProductDtoMapper.toDtoResponse(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findProductById(@PathVariable String id) {
        Product product = findProductByIdUseCase.execute(id);
        log.info("Request received - Endpoint: GET /api/v1/product/{}", id);
        return ResponseEntity.ok(ProductDtoMapper.toDtoResponse(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseDto> deactiveProduct(@PathVariable String id) {
        Product product = deactiveProductUseCase.execute(id);
        log.info("Request received - Endpoint: DELETE /api/v1/product/{}", id);
        return ResponseEntity.ok(ProductDtoMapper.toDtoResponse(product));
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testProductService() {
        Map<String, String> response = Map.of("message", "Product service is working.");
        log.info("Request received - Endpoint: GET /api/v1/product/test");
        return ResponseEntity.ok(response);
    }
}
