package br.thullyoo.ecommerce_backend.services;

import br.thullyoo.ecommerce_backend.domain.itempurchase.ItemPurchase;
import br.thullyoo.ecommerce_backend.domain.product.Product;
import br.thullyoo.ecommerce_backend.domain.purchase.ProductPurchaseRequest;
import br.thullyoo.ecommerce_backend.domain.purchase.Purchase;
import br.thullyoo.ecommerce_backend.domain.purchase.PurchaseRequest;
import br.thullyoo.ecommerce_backend.domain.user.User;
import br.thullyoo.ecommerce_backend.repositories.ProductRepository;
import br.thullyoo.ecommerce_backend.repositories.PurchaseRepository;
import br.thullyoo.ecommerce_backend.repositories.UserRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PurchaseService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final PurchaseRepository purchaseRepository;

    public PurchaseService(UserRepository userRepository, ProductRepository productRepository, PurchaseRepository purchaseRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public Purchase registerPurchase(PurchaseRequest purchaseRequest, Jwt jwt) {

        String user_idString = jwt.getClaim("id");
        UUID user_id = UUID.fromString(user_idString);

        Optional<User> user =  userRepository.findById(user_id);

        if (user.isEmpty()){
            throw new RuntimeException("Usuário não encotrado");
        }

        Purchase purchase = new Purchase();

        List<ItemPurchase> itemPurchases = new ArrayList<>();

        Double total = 0.0;

        for(ProductPurchaseRequest productPurchaseRequest : purchaseRequest.getProducts())  {
            Optional<Product> product = productRepository.findById(productPurchaseRequest.product_id());
            if (product.isEmpty()){
                throw new RuntimeException("Produto não encontrado");
            }
            if (!product.get().getAvailable()){
                throw new RuntimeException("Produto não disponível");
            }
            if (product.get().getQuantity() - productPurchaseRequest.quantity() < 0){
                throw new RuntimeException("Quantidade insuficiente disponível");
            }
            total += product.get().getValue() * productPurchaseRequest.quantity();
            product.get().setQuantity(product.get().getQuantity() - productPurchaseRequest.quantity());

            ItemPurchase itemadd =  new ItemPurchase(product.get(), productPurchaseRequest.quantity());

            itemPurchases.add(itemadd);

            itemadd.setPurchase(purchase);

            productRepository.save(product.get());

        }

        purchase.setItemPurchases(itemPurchases);
        purchase.setTotal(total);
        purchase.setDatePurchase(LocalDateTime.now());
        purchase.setUser(user.get());

        purchaseRepository.save(purchase);

        return purchase;
    }

    public List<Purchase> listPurchase(){
        return purchaseRepository.findAll();
    }

}
