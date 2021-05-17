package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@Setter
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER) // 일대다  실무에서는 manytomay 쓰면 큰일임
    @JoinColumn(name="member_id")
    private Member member;

    //fetch = FetchType.EAGER(즉시로딩)은 예측이 어렵기 때문에 모든 연관관계는 지연로딩으로 설정해야한다.


    @OneToMany(mappedBy="order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderData; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태[ORDER, CANCEL]


}
