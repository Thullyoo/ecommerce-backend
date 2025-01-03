package br.thullyoo.ecommerce_backend.repositories;


import br.thullyoo.ecommerce_backend.domain.purchase.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUserId(UUID userId);
}
