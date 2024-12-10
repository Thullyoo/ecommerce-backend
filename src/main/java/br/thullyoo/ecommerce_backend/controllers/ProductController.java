package br.thullyoo.ecommerce_backend.controllers;

import br.thullyoo.ecommerce_backend.domain.product.Product;
import br.thullyoo.ecommerce_backend.domain.product.ProductRequest;
import br.thullyoo.ecommerce_backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> registerProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("value") Double value,
            @RequestParam("quantity") Long quantity,
            @RequestParam("image") MultipartFile image
            , @AuthenticationPrincipal Jwt jwt)  throws IOException {

        ProductRequest productRequest = new ProductRequest();
        productRequest.setName(name);
        productRequest.setDescription(description);
        productRequest.setValue(value);
        productRequest.setQuantity(quantity);
        productRequest.setImage(image);

        var produto = productService.registerProduct(productRequest, jwt);
        return ResponseEntity.ok().body(produto);
    }

    @GetMapping
    public ResponseEntity<List<Product>> listProduct(){
        return ResponseEntity.ok().body(productService.listProducts());
    }

    @PutMapping("/{product_id}")
    public ResponseEntity<Product> editProduct(
            @PathVariable("product_id") UUID product_id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("value") Double value,
            @RequestParam("quantity") Long quantity,
            @RequestParam(value = "image", required = false) MultipartFile image
            , @AuthenticationPrincipal Jwt jwt)  throws IOException {

        ProductRequest productRequest = new ProductRequest();
        productRequest.setName(name);
        productRequest.setDescription(description);
        productRequest.setValue(value);
        productRequest.setQuantity(quantity);
        productRequest.setImage(image);

        var produtoLista = productService.editProduct(product_id, productRequest, jwt);

        return ResponseEntity.ok().body(produtoLista);
    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity<Void> disableProduct(@PathVariable("product_id") UUID product_id, @AuthenticationPrincipal Jwt jwt){
        productService.disableProduct(jwt, product_id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user")
    public ResponseEntity<List<Product>> listProductsUserById(@AuthenticationPrincipal Jwt jwt){
        List<Product> products = productService.listProductByUserId(jwt);
        return ResponseEntity.ok().body(products);
    }


    @GetMapping("/byName/{name}")
    public ResponseEntity<List<Product>> getProductByName(@PathVariable("name") String name){
        return ResponseEntity.ok().body(productService.getProductsByName(name));
    }

    @GetMapping("/byId/{product_id}")
    public ResponseEntity<Product> getProductById(@PathVariable("product_id") UUID id){
        return ResponseEntity.ok().body(productService.getProductById(id));
    }
}
