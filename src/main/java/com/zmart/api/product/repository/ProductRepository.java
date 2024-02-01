package com.zmart.api.product.repository;

import com.zmart.api.product.entity.Product;
import com.zmart.api.product.entity.ProductId;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, ProductId> {
}
