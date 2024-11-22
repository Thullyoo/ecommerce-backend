package br.thullyoo.ecommerce_backend.domain.purchase;

import br.thullyoo.ecommerce_backend.domain.product.Product;
import br.thullyoo.ecommerce_backend.domain.user.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "TB_PURCHASES")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "purchase_id")
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "purchase_product",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
