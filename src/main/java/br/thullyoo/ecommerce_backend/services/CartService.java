package br.thullyoo.ecommerce_backend.services;

import br.thullyoo.ecommerce_backend.domain.cart.Cart;
import br.thullyoo.ecommerce_backend.domain.cartItem.CardItemRequest;
import br.thullyoo.ecommerce_backend.domain.cartItem.CartItem;
import br.thullyoo.ecommerce_backend.domain.cartItem.CartItemResponse;
import br.thullyoo.ecommerce_backend.domain.product.Product;
import br.thullyoo.ecommerce_backend.repositories.CartItemRepository;
import br.thullyoo.ecommerce_backend.repositories.CartRepository;
import br.thullyoo.ecommerce_backend.repositories.ProductRepository;
import br.thullyoo.ecommerce_backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    public void addCartItem(CardItemRequest request, Jwt jwt){

        var user = userRepository.findById(UUID.fromString(jwt.getClaim("id")));

        if (user.isEmpty()){
            throw new RuntimeException("User not found");
        }

        Optional<Product> product = productRepository.findById(request.product_id());

        if (product.isEmpty()){
            throw new RuntimeException("Product not found");
        }

        if(product.get().getUser().getId() == user.get().getId()){
            throw new RuntimeException("Owner can't possible to buy your items");
        }

        Optional<Cart> cart = cartRepository.findByUserId(user.get().getId());

        for (CartItem item : cart.get().getItems()) {
            if (item.getProduct().getId() == request.product_id()){
                item.setQuantity(request.quantity());
                cartRepository.save(cart.get());
                return;
            }
        }

        CartItem cartItem = new CartItem();

        cartItem.setQuantity(request.quantity());

        if  (product.get().getQuantity() - request.quantity() < 0){
            throw new RuntimeException("Quantity insufficient");
        }

        cartItem.setProduct(product.get());

        cartItem.setCart(cart.get());

        cart.get().getItems().add(cartItem);

        cartRepository.save(cart.get());
    }

    public void removeItemCart(Long itemCart_id ,Jwt jwt){

        Optional<Cart> cart = this.cartRepository.findByUserId(UUID.fromString(jwt.getClaim("id")));

        Optional<CartItem> cartItem = this.cartItemRepository.findById(itemCart_id);

        if (cart.isEmpty()){
            throw new RuntimeException("Cart not found");
        }

        if (cartItem.isEmpty()){
            throw new RuntimeException("CartItem not found");
        }

        cart.get().getItems().remove(cartItem.get());

        cartRepository.save(cart.get());


        System.out.println("cheguei aqui");
    }

        public List<CartItemResponse> getCartByUserId(Jwt jwt){

            Optional<Cart> cart = this.cartRepository.findByUserId(UUID.fromString(jwt.getClaim("id")));

            if (cart.isEmpty()){
                throw new RuntimeException("Cart not found");
            }

            List<CartItemResponse> cartItemList = new ArrayList<>();

            if(cart.get().getItems().size() <= 0){
                throw new RuntimeException("User doesn't have a product in their cart");
            }

            cart.get().getItems().forEach(item -> {
                cartItemList.add(new CartItemResponse(item.getProduct(), item.getQuantity()));
            });

            return cartItemList;
        }


}
