package com.example.coupondb.beans;

import com.example.coupondb.enums.Category;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.net.ssl.SSLSession;
import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "coupon")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode(of = "id")
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int companyid;
    @Enumerated(EnumType.ORDINAL)
    private Category category;
    private String Title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;
    @ManyToMany(mappedBy = "coupons", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<Customer> purchases;

    @PreRemove
    public void removeCouponFromCustomer() {
        for (Customer customer : purchases) {
            customer.getCoupons().remove(this);
        }
    }


}
