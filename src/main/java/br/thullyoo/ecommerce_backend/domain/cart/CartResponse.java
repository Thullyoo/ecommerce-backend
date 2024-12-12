package br.thullyoo.ecommerce_backend.domain.cart;

import br.thullyoo.ecommerce_backend.domain.cartItem.CartItemResponse;

import java.util.List;

public record CartResponse(List<CartItemResponse> items) {
}
