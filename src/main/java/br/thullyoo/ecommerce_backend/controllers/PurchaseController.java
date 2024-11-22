package br.thullyoo.ecommerce_backend.controllers;

import br.thullyoo.ecommerce_backend.domain.purchase.Purchase;
import br.thullyoo.ecommerce_backend.domain.purchase.PurchaseRequest;
import br.thullyoo.ecommerce_backend.services.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<Purchase> registerPurchase(@RequestBody PurchaseRequest purchaseRequest){
        var purchase = purchaseService.registerPurchase(purchaseRequest);
        return ResponseEntity.ok().body(purchase);
    }

}
