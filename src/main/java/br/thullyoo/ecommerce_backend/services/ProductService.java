package br.thullyoo.ecommerce_backend.services;

import br.thullyoo.ecommerce_backend.domain.product.Product;
import br.thullyoo.ecommerce_backend.domain.product.ProductMapper;
import br.thullyoo.ecommerce_backend.domain.product.ProductRequest;
import br.thullyoo.ecommerce_backend.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private ProductRepository produtoRepository;

    private ProductMapper productMapper;

    public Product saveProduct(ProductRequest productRequest){
        Product product = productMapper.toProduct(productRequest);
        return produtoRepository.save(product);
    }

    public void deleteProduct(UUID id){
        produtoRepository.findById(id).ifPresentOrElse((produto) ->{
                produtoRepository.delete(produto);
                },
                ()->{
                throw new RuntimeException("Produto não encotrado");
        });
    }

    public Product editProduct(UUID id, ProductRequest productRequest){
        produtoRepository.findById(id).ifPresentOrElse((produto) ->{
                    produto.setName(productRequest.getName());
                    produto.setDescription(productRequest.getDescription());
                    produto.setValue(productRequest.getValue());
                    produto.setUrl_image(productRequest.getUrl_image());
                    produtoRepository.save(produto);
                    },
                ()->{
                    throw new RuntimeException("Produto não encotrado");
                });
        return produtoRepository.findById(id).get();
    }

    public List<Product> listProducts(){
        return produtoRepository.findAll();
    }

}
