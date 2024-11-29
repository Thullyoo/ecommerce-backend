package br.thullyoo.ecommerce_backend.domain.purchase;

import java.util.List;

public class PurchaseRequest {

    private List<ProductPurchaseRequest> products;

    public List<ProductPurchaseRequest> getProducts() {
        return products;
    }

    public void setProducts(List<ProductPurchaseRequest> products) {
        this.products = products;
    }
}
