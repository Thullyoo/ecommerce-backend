package br.thullyoo.ecommerce_backend.domain.cartItem;

import br.thullyoo.ecommerce_backend.domain.product.Product;
import br.thullyoo.ecommerce_backend.domain.purchase.ProductDTO;

public record CartItemResponse(ProductDTO product, int quantity) {
    public CartItemResponse(Product product, int quantity) {
        this(new ProductDTO(product.getName(), product.getValue(), product.getUrl_image()), quantity);
    }
}

