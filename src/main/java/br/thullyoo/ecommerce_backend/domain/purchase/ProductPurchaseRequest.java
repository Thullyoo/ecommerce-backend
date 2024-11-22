package br.thullyoo.ecommerce_backend.domain.purchase;

import java.util.UUID;

public record ProductPurchaseRequest(UUID product_id, Long quantity) {
}
