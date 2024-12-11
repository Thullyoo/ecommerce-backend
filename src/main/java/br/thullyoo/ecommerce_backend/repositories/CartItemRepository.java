package br.thullyoo.ecommerce_backend.repositories;


import br.thullyoo.ecommerce_backend.domain.cartItem.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
