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
@Table(name = "product")
public class Product implements Serializable {

    public Product() {

    }

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int product_id;

    @Column(name = "product_name", length = 50, nullable = false)
    private String product_name;

    @Column(name = "product_price", nullable = false)
    private double product_price;

    @Column(name = "qty", nullable = false)
    private int qty;

    @ManyToOne
    @JoinColumn(name = "Category_category_id")
    private Category category;

    @Column(name = "description")
    private String Description1;

    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    /**
     * @return the product_id
     */
    public int getProduct_id() {
        return product_id;
    }

    /**
     * @param product_id the product_id to set
     */
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    /**
     * @return the product_name
     */
    public String getProduct_name() {
        return product_name;
    }

    /**
     * @param product_name the product_name to set
     */
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    /**
     * @return the product_price
     */
    public double getProduct_price() {
        return product_price;
    }

    /**
     * @param product_price the product_price to set
     */
    public void setProduct_price(double product_price) {
        this.product_price = product_price;
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

    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @return the Description1
     */
    public String getDescription1() {
        return Description1;
    }

    /**
     * @param Description1 the Description1 to set
     */
    public void setDescription1(String Description1) {
        this.Description1 = Description1;
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

    public void getQty(int parseInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
