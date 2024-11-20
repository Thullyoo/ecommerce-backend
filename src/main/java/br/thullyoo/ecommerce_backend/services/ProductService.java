package br.thullyoo.ecommerce_backend.services;

import br.thullyoo.ecommerce_backend.domain.product.Product;
import br.thullyoo.ecommerce_backend.domain.product.ProductMapper;
import br.thullyoo.ecommerce_backend.domain.product.ProductRequest;
import br.thullyoo.ecommerce_backend.repositories.ProductRepository;
import br.thullyoo.ecommerce_backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository produtoRepository;

    private final ProductMapper productMapper;

    private final UserRepository userRepository;

    public ProductService(ProductRepository produtoRepository, ProductMapper productMapper, UserRepository userRepository) {
        this.produtoRepository = produtoRepository;
        this.productMapper = productMapper;
        this.userRepository = userRepository;
    }

    public Product registerProduct(ProductRequest productRequest){
        Product product = productMapper.toProduct(productRequest);
        var user = userRepository.findById(productRequest.getUser_id());
        if (user.isEmpty()){
            throw new RuntimeException("Usuário não encontrado");
        }
        product.setUser(user.get());
        List<Product> products = user.get().getProducts();
        products.add(product);
        user.get().setProducts(products);
        userRepository.save(user.get());
        return produtoRepository.save(product);
    }

    public void deleteProduct(UUID product_id, UUID user_id){
        produtoRepository.findById(product_id).ifPresentOrElse((produto) ->{
                var user = userRepository.findById(user_id);
                if (user.isEmpty()){
                    throw new RuntimeException("Usuário não encontrado");
                }
                user.get().getProducts().forEach((product -> {
                    if (product.equals(produto)){
                        produto.setAvailable(false);
                        produtoRepository.save(produto);
                    }
                }));
                },
                ()->{
                throw new RuntimeException("Produto não encotrado");
        });
    }

    public Product editProduct(UUID id, ProductRequest productRequest){
        produtoRepository.findById(id).ifPresentOrElse((produto) ->{
                    var user = userRepository.findById(productRequest.getUser_id());
                    if (user.isEmpty()){
                        throw new RuntimeException("Usuário não encontrado");
                    }
                    user.get().getProducts().forEach((product -> {
                        if (product.equals(produto)){
                            produto.setName(productRequest.getName());
                            produto.setDescription(productRequest.getDescription());
                            produto.setValue(productRequest.getValue());
                            produto.setUrl_image(productRequest.getUrl_image());
                            produtoRepository.save(produto);
                        }
                    }));
                    },
                ()->{
                    throw new RuntimeException("Produto não encotrado");
                });
        return produtoRepository.findById(id).get();
    }

    public List<Product> listProducts(){
        return produtoRepository.findAll();
    }

    public List<Product> listProductByUserId(UUID user_id){
        var user = userRepository.findById(user_id);
        if (user.isEmpty()){
            throw new RuntimeException("Usuário não encontrado");
        }

        return user.get().getProducts();
    }
}
