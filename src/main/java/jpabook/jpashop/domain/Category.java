package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name="category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name="catagory_item",
               joinColumns = @JoinColumn(name="category_id"),
               inverseJoinColumns = @JoinColumn(name="item_id"))  //다대다관계에는 중간에 연결해주는 테이블(catagory_item)이 필요함 inverseJoinColumn으로 item_id끼리 조인
    private List<Item> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="parent_id")
    private Category parent;

    @OneToMany(mappedBy ="parent")
    private List<Category> child = new ArrayList<>();
}
