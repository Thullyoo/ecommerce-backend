package br.thullyoo.ecommerce_backend.services;

import br.thullyoo.ecommerce_backend.domain.product.Product;
import br.thullyoo.ecommerce_backend.domain.product.ProductMapper;
import br.thullyoo.ecommerce_backend.domain.product.ProductRequest;
import br.thullyoo.ecommerce_backend.repositories.ProductRepository;
import br.thullyoo.ecommerce_backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final UserRepository userRepository;

    private final S3Service s3Service;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, UserRepository userRepository, S3Service s3Service) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.userRepository = userRepository;
        this.s3Service = s3Service;
    }

    @Transactional
    public Product registerProduct(ProductRequest productRequest, Jwt jwt) throws IOException {

        String user_idString = jwt.getClaim("id");
        UUID user_id = UUID.fromString(user_idString);

        Product product = productMapper.toProduct(productRequest);
        product.setAvailable(true);

        var user = userRepository.findById(user_id);

        if (user.isEmpty()){
            throw new RuntimeException("Usuário não encontrado");
        }

        product.setUser(user.get());
        List<Product> products = user.get().getProducts();
        products.add(product);
        user.get().setProducts(products);
        
        userRepository.save(user.get());

        if (product.getQuantity() <= 0){
            product.setAvailable(false);
        }

        Product productSaved = productRepository.save(product);

        String url_image = s3Service.registerImage(productSaved.getId(), productRequest.getImage());

        productSaved.setUrl_image(url_image);

        productRepository.save(productSaved);

        return productSaved;
    }

    @Transactional
    public void disableProduct(Jwt jwt, UUID product_id){

        String user_idString = jwt.getClaim("id");
        UUID user_id = UUID.fromString(user_idString);

        productRepository.findById(product_id).ifPresentOrElse((produto) ->{
                var user = userRepository.findById(user_id);
                if (user.isEmpty()){
                    throw new RuntimeException("Usuário não encontrado");
                }
                user.get().getProducts().forEach((product -> {
                    if (product.equals(produto)){
                        produto.setAvailable(false);
                        productRepository.save(produto);
                    }
                }));
                },
                ()->{
                throw new RuntimeException("Produto não encotrado");
        });
    }

    @Transactional
    public Product editProduct(UUID id, ProductRequest productRequest, Jwt jwt){

        String user_idString = jwt.getClaim("id");
        UUID user_id = UUID.fromString(user_idString);

        productRepository.findById(id).ifPresentOrElse((produto) ->{
                    var user = userRepository.findById(user_id);
                    if (user.isEmpty()){
                        throw new RuntimeException("Usuário não encontrado");
                    }
                    user.get().getProducts().forEach((product -> {
                        if (product.getId() == id){
                            produto.setName(productRequest.getName());
                            produto.setDescription(productRequest.getDescription());
                            produto.setValue(productRequest.getValue());
                            try {
                                produto.setUrl_image(s3Service.registerImage(product.getId(), productRequest.getImage()));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            produto.setQuantity(productRequest.getQuantity());
                            productRepository.save(produto);
                        }
                        if (productRequest.getQuantity() > 0){
                            product.setAvailable(true);
                        }
                    }));
                    },
                ()->{
                    throw new RuntimeException("Produto não encotrado");
                });
        return productRepository.findById(id).get();
    }

    public List<Product> listProducts(){
        return productRepository.findAll();
    }

    public List<Product> listProductByUserId(Jwt jwt){

        String user_idString = jwt.getClaim("id");
        UUID user_id = UUID.fromString(user_idString);

        var user = userRepository.findById(user_id);
        if (user.isEmpty()){
            throw new RuntimeException("Usuário não encontrado");
        }

        return user.get().getProducts();
    }

    public List<Product> getProductsByName(String name){
        return productRepository.findByName(name);
    }
}
