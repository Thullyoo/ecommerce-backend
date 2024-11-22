package br.thullyoo.ecommerce_backend.domain.purchase;

import java.util.List;
import java.util.UUID;

public class PurchaseRequest {

    private List<ProductPurchaseRequest> products;

    private UUID user_id;

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public List<ProductPurchaseRequest> getProducts() {
        return products;
    }

    public void setProducts(List<ProductPurchaseRequest> products) {
        this.products = products;
    }
}
