package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    public Long id;



    private String name;
    private int price;
    private int stockQunantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categoryies = new ArrayList<>();


}
