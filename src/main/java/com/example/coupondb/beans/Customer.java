package com.example.coupondb.beans;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode(of = "id")
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)

public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerid")
    private int id;
    private String first_name;
    private String last_name;
    @Column(unique = true, length = 40)
    private String email;
    private String password;

    @Singular
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "customer_coupons", joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customerid"), inverseJoinColumns = @JoinColumn(name = "coupon_id", referencedColumnName = "id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Coupon> coupons;


}
