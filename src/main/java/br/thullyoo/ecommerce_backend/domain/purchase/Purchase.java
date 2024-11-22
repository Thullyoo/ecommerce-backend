package br.thullyoo.ecommerce_backend.domain.purchase;

import br.thullyoo.ecommerce_backend.domain.itempurchase.ItemPurchase;
import br.thullyoo.ecommerce_backend.domain.product.Product;
import br.thullyoo.ecommerce_backend.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TB_PURCHASES")
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "purchase_id")
    private Long id;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
    private List<ItemPurchase> itemPurchases;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double total;

    private LocalDateTime datePurchase;

    public LocalDateTime getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(LocalDateTime datePurchase) {
        this.datePurchase = datePurchase;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemPurchase> getItemPurchases() {
        return itemPurchases;
    }

    public void setItemPurchases(List<ItemPurchase> itemPurchases) {
        this.itemPurchases = itemPurchases;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
