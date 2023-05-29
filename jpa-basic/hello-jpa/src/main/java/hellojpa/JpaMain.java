package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member();
            member1.setUsername("C");

            Member member2 = new Member();
            member2.setUsername("C");

            Member member3 = new Member();
            member3.setUsername("C");

            Member member4 = new Member();
            member4.setUsername("C");

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(member4);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }


        emf.close();
    }
}
