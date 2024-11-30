package br.thullyoo.ecommerce_backend.controllers;

import br.thullyoo.ecommerce_backend.domain.product.Product;
import br.thullyoo.ecommerce_backend.domain.product.ProductRequest;
import br.thullyoo.ecommerce_backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> registerProduct(@RequestBody ProductRequest productRequest, @AuthenticationPrincipal Jwt jwt){
        var produto = productService.registerProduct(productRequest, jwt);
        return ResponseEntity.ok().body(produto);
    }

    @GetMapping
    public ResponseEntity<List<Product>> listProduct(){
        return ResponseEntity.ok().body(productService.listProducts());
    }

    @PutMapping("/{product_id}")
    public ResponseEntity<Product> editProduct(@PathVariable("product_id")  UUID product_id, @RequestBody ProductRequest productRequest, @AuthenticationPrincipal Jwt jwt){
        var produtoLista = productService.editProduct(product_id, productRequest, jwt);

        return ResponseEntity.ok().body(produtoLista);
    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity<Void> disableProduct(@PathVariable("product_id") UUID product_id, @AuthenticationPrincipal Jwt jwt){
        productService.disableProduct(jwt, product_id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user")
    public ResponseEntity<List<Product>> listProductsById(@AuthenticationPrincipal Jwt jwt){
        List<Product> products = productService.listProductByUserId(jwt);
        return ResponseEntity.ok().body(products);
    }
}
