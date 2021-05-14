package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext  //스프링부트가 이 어노테이션이 있으면 매니저를 주입을 해준다.
    private EntityManager em;

    //저장
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    //조회회
    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
