package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart")
public class Cart implements Serializable {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cart_id;

    @ManyToOne
    @JoinColumn(name = "product_product_id")
    private Product product_product_id;

    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    @Column(name = "qty", nullable = false)
    private int qty;

    /**
     * @return the cart_id
     */
    public int getCart_id() {
        return cart_id;
    }

    /**
     * @param cart_id the cart_id to set
     */
    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    /**
     * @return the product_product_id
     */
    public Product getProduct_product_id() {
        return product_product_id;
    }

    /**
     * @param product_product_id the product_product_id to set
     */
    public void setProduct_product_id(Product product_product_id) {
        this.product_product_id = product_product_id;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the qty
     */
    public int getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(int qty) {
        this.qty = qty;
    }

}
