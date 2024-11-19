package br.thullyoo.ecommerce_backend.services;

import br.thullyoo.ecommerce_backend.domain.produto.Produto;
import br.thullyoo.ecommerce_backend.domain.produto.ProdutoMapper;
import br.thullyoo.ecommerce_backend.domain.produto.ProdutoRequest;
import br.thullyoo.ecommerce_backend.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;

    private ProdutoMapper produtoMapper;

    public Produto registrarProduto(ProdutoRequest produtoRequest){
        Produto produto = produtoMapper.toProduto(produtoRequest);
        return produtoRepository.save(produto);
    }

    public void excluirProduto(Long id){
        produtoRepository.findById(id).ifPresentOrElse((produto) ->{
                produtoRepository.delete(produto);
                },
                ()->{
                throw new RuntimeException("Produto não encotrado");
        });
    }

    public Produto editarProduto(Long id, ProdutoRequest produtoRequest){
        produtoRepository.findById(id).ifPresentOrElse((produto) ->{
                    produto.setNome(produtoRequest.getNome());
                    produto.setDescricao(produtoRequest.getDescricao());
                    produto.setValor(produtoRequest.getValor());
                    produto.setUrl_imagem(produtoRequest.getUrl_imagem());
                    produtoRepository.save(produto);
                    },
                ()->{
                    throw new RuntimeException("Produto não encotrado");
                });
        return produtoRepository.findById(id).get();
    }

    public List<Produto> listarProdutos(){
        return produtoRepository.findAll();
    }

}
