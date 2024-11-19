package br.thullyoo.ecommerce_backend.controllers;

import br.thullyoo.ecommerce_backend.domain.produto.Produto;
import br.thullyoo.ecommerce_backend.domain.produto.ProdutoRequest;
import br.thullyoo.ecommerce_backend.services.ProdutoService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> registrarProduto(@RequestBody ProdutoRequest produtoRequest){
        var produto = produtoService.registrarProduto(produtoRequest);
        return ResponseEntity.ok().body(produto);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos(){
        return ResponseEntity.ok().body(produtoService.listarProdutos());
    }

    @PutMapping("/{produto_id}")
    public ResponseEntity<Produto> editarProduto(@PathVariable("produto_id")  Long produto_id, @RequestBody ProdutoRequest produtoRequest){
        var produtoLista = produtoService.editarProduto(produto_id, produtoRequest);

        return ResponseEntity.ok().body(produtoLista);
    }

    @DeleteMapping("/{produto_id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable("produto_id") Long produto_id){
        produtoService.excluirProduto(produto_id);

        return ResponseEntity.noContent().build();
    }
}
