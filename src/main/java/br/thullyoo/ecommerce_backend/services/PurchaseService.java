package br.thullyoo.ecommerce_backend.services;

import br.thullyoo.ecommerce_backend.domain.cart.Cart;
import br.thullyoo.ecommerce_backend.domain.cartItem.CartItem;
import br.thullyoo.ecommerce_backend.domain.itempurchase.ItemPurchase;
import br.thullyoo.ecommerce_backend.domain.product.Product;
import br.thullyoo.ecommerce_backend.domain.purchase.ProductPurchaseRequest;
import br.thullyoo.ecommerce_backend.domain.purchase.Purchase;
import br.thullyoo.ecommerce_backend.domain.purchase.PurchaseRequest;
import br.thullyoo.ecommerce_backend.domain.user.User;
import br.thullyoo.ecommerce_backend.repositories.CartRepository;
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

    private final CartRepository cartRepository;

    public PurchaseService(UserRepository userRepository, ProductRepository productRepository, PurchaseRepository purchaseRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
        this.cartRepository = cartRepository;
    }

    public void registerPurchase(Jwt jwt) {

        String user_idString = jwt.getClaim("id");
        UUID user_id = UUID.fromString(user_idString);

        Optional<User> user =  userRepository.findById(user_id);

        if (user.isEmpty()){
            throw new RuntimeException("User not found");
        }

        Optional<Cart> cart = cartRepository.findByUserId(user_id);

        if (cart.isEmpty()){
            throw new RuntimeException("Cart not found");
        }

        if (cart.get().getItems().size() <= 0){
            throw new RuntimeException("Cart's empty");
        }

        Purchase purchase = new Purchase();

        List<ItemPurchase> itemPurchases = new ArrayList<>();

        Double total = 0.0;


        for(CartItem cartItem : cart.get().getItems())  {

            Product product = cartItem.getProduct();

            if (!product.getAvailable()){
                throw new RuntimeException("Product's unavailable");
            }

            if (product.getQuantity() - cartItem.getQuantity() < 0){
                throw new RuntimeException("Quantity insufficient");
            }
            total += product.getValue() * cartItem.getQuantity();
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());

            ItemPurchase itemadd =  new ItemPurchase(product, cartItem.getQuantity());

            itemPurchases.add(itemadd);

            itemadd.setPurchase(purchase);

            productRepository.save(product);

        }
        purchase.setItemPurchases(itemPurchases);
        purchase.setTotal(total);
        purchase.setDatePurchase(LocalDateTime.now());
        purchase.setUser(user.get());

        purchaseRepository.save(purchase);

        cart.get().getItems().clear();
        cartRepository.save(cart.get());
    }

    public List<Purchase> listPurchase(){
        return purchaseRepository.findAll();
    }

}
