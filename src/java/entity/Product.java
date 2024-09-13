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
    int product_id;

    @Column(name = "product_name", length = 50, nullable = false)
    String product_name;

    @Column(name = "product_price", nullable = false)
    double product_price;

    @Column(name = "qty", nullable = false)
    int qty;

    @ManyToOne
    @JoinColumn(name = "Category_category_id")
    Category category;

    @Column(name = "description")
    String Description1;

    @ManyToOne
    @JoinColumn(name = "user_email")
    User user;

}
