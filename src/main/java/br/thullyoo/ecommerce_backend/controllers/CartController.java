package br.thullyoo.ecommerce_backend.controllers;

import br.thullyoo.ecommerce_backend.domain.cartItem.CardItemRequest;
import br.thullyoo.ecommerce_backend.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Void> addItemCart(@RequestBody CardItemRequest cardItem,@AuthenticationPrincipal Jwt jwt){
        this.cartService.addCartItem(cardItem, jwt);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/remove/{itemCart_id}")
    public ResponseEntity<Void> removeItemCart(@PathVariable("itemCart_id") Long itemCart_id, @AuthenticationPrincipal Jwt jwt){
        this.cartService.removeItemCart(itemCart_id, jwt);
        return ResponseEntity.noContent().build();
    }
}
