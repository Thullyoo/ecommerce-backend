package br.thullyoo.ecommerce_backend.controllers;

import br.thullyoo.ecommerce_backend.domain.product.Product;
import br.thullyoo.ecommerce_backend.domain.product.ProductRequest;
import br.thullyoo.ecommerce_backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produto")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> registrarProduto(@RequestBody ProductRequest productRequest){
        var produto = productService.registerProduct(productRequest);
        return ResponseEntity.ok().body(produto);
    }

    @GetMapping
    public ResponseEntity<List<Product>> listarProdutos(){
        return ResponseEntity.ok().body(productService.listProducts());
    }

    @PutMapping("/{produto_id}")
    public ResponseEntity<Product> editarProduto(@PathVariable("produto_id")  UUID produto_id, @RequestBody ProductRequest productRequest){
        var produtoLista = productService.editProduct(produto_id, productRequest);

        return ResponseEntity.ok().body(produtoLista);
    }

    @DeleteMapping("/{user_id}/{product_id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable("product_id") UUID product_id, @PathVariable("user_id") UUID user_id){
        productService.deleteProduct(product_id, user_id);

        return ResponseEntity.noContent().build();
    }
}
