package com.example.coupondb.beans;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "company")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode(of = "companyid")
@JsonIdentityInfo(property = "companyid", generator = ObjectIdGenerators.PropertyGenerator.class)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "companyid")
    private int companyid;
    @Column(nullable = false,unique = true, length = 40)
    private String name;
    @Column(unique = true, length = 40)
    private String email;
    private String password;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "companyid",
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @Singular
    private List<Coupon> coupons;


}
