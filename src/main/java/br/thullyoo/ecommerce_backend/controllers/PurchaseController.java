package br.thullyoo.ecommerce_backend.controllers;

import br.thullyoo.ecommerce_backend.domain.purchase.Purchase;
import br.thullyoo.ecommerce_backend.domain.purchase.PurchaseRequest;
import br.thullyoo.ecommerce_backend.services.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<Purchase> registerPurchase(@RequestBody PurchaseRequest purchaseRequest, @AuthenticationPrincipal Jwt jwt){
        var purchase = purchaseService.registerPurchase(purchaseRequest, jwt);
        return ResponseEntity.ok().body(purchase);
    }

    @GetMapping
    public ResponseEntity<List<Purchase>> listPurchase(){
        return ResponseEntity.ok().body(purchaseService.listPurchase());
    }

}
