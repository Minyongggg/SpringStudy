package hellojpa;

import javax.persistence.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin( );

        try {

            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            member.setTeam(team);
            team.getMembers().add(member); // JPA에서는 연관관계의 주인이 아니기 때문에 실행 안되는 코드지만 객체 관계의 관점에서는 양쪽에 세팅하는 것이 맞다. 그리고 flush 이전에 컬렉션에도 값이 있도록 하기 위해
//            member.changeTeam(team);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }


        emf.close();
    }
}
