package br.thullyoo.ecommerce_backend.domain.cartItem;

import java.util.UUID;

public record CardItemRequest(UUID product_id, Integer quantity) {
}
