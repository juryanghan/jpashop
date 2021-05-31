package jpabook.jpashop.service;

import jpabook.jpashop.Member;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderSerivceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderSerivce orderSerivce;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","용산","2층"));
        em.persist(member);

        Book book = new Book();
        book.setName("JPA");
        book.setPrice(1000);
        book.setStockQunantity(10);
        em.persist(book);

        int orderCount=1;
        //when
        Long orderId = orderSerivce.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는  order", OrderStatus.ORDER, String.valueOf(getOrder.getStatus()));
        assertEquals("주문한 상품 종류 수가 정확해야 한다.",getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.",1000*orderCount,getOrder.getTotalPrice());
        assertEquals(Float.parseFloat("주문 수량만큼 재고가 줄어야 한다."), 8, book.getStockQunantity());

    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //Given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); //이름, 가격, 재고
        int orderCount = 11; //재고보다 많은 수량
        //When
        orderSerivce.order(member.getId(), item.getId(), orderCount);
        //Then
        fail("재고 수량 부족 예외가 발생해야 한다.");

    }


    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = createMember();
        Book item= createBook("시골jpa",10000,100);

        int orderCount = 2;

        Long orderId = orderSerivce.order(member.getId(), item.getId(),orderCount);
        //when
        orderSerivce.cancleOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 cancle이다.",OrderStatus.CANCEL, String.valueOf(getOrder.getStatus()));
        assertEquals(Float.parseFloat("주문이 취소된 상품은 그만큼 재고가 증가해야 한다."),10, item.getStockQunantity());


    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }
    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQunantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }




}