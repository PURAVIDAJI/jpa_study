package jpajava;

import domain.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EmployeeDetachTest {
    public static void main(String[] args) {
        //엔티티 메니저를 persistence 객체 도움 받아 만들기
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
        //저게 persistence.xml이다.
        EntityManager em = emf.createEntityManager();
        //엔티티 매니저를 만들어 줘야 함.
        EntityTransaction tx = em.getTransaction();
        //트렌젝션 만들기

        tx.begin();
        System.out.println("트랜잭션 시작!");
        Employee e1 = em.find(Employee.class, "202301");
        //캐시에서 들고오기 -> 타입과, id로
        System.out.println("DB에서 가져옴");
        System.out.println("영속 상태");

        em.detach(e1);
        System.out.println("준영속 상태");

        Employee e2 = em.find(Employee.class, "202301");
        //영속 상태이므로 두번째 find에서는 캐시에서 가져옴
        System.out.println("DB에서 가져옴");

        System.out.println("el(준영속)==e2==>"+(e1==e2));

        Employee e3 = em.find(Employee.class, "202301");
        //영속 상태이므로 두번째 find에서는 캐시에서 가져옴
        System.out.println("1차캐시 에서 가져옴");

        System.out.println("e3(영속)==e2(영속)==>"+(e3==e2));

        System.out.println("커밋 전");
        tx.commit();
        System.out.println("커밋 후");
        //SQL문이 날라감,DB로 간다. 이렇게 하면 트렌젝션이 종료된다.
        em.close();
        emf.close();


    }
}
