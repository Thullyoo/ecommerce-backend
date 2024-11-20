package br.thullyoo.ecommerce_backend.repositories;

import br.thullyoo.ecommerce_backend.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
