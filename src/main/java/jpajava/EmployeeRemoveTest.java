package jpajava;

import domain.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EmployeeRemoveTest {
    public static void main(String[] args) {
        //엔티티 메니저를 persistence 객체 도움 받아 만들기
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
        //저게 persistence.xml이다.
        EntityManager em = emf.createEntityManager();
        //엔티티 매니저를 만들어 줘야 함.
        EntityTransaction tx = em.getTransaction();
        //트렌젝션 만들기

        tx.begin();
        try {

            System.out.println("트랜잭션 시작!");
            Employee e1 = em.find(Employee.class, "202301");
            //DB에서 들고옴 처음 들고 오니까
            System.out.println("DB에서 가져옴");
            System.out.println("영속 상태");

            em.remove(e1);
            System.out.println("삭제 상태");
            //영속에서는 삭제되고, db에는 안없어짐

            System.out.println("커밋 전");
            tx.commit();
            //따라서 같은 상태로 만들기 위해 알아서 db에 delete문 날림 -> 디비에서도 삭제됨 --> 지연쓰기
            //find의 경우에는 바로 날림 ,find와 jpql(sql바로쓴 거)은 바로 날라감. insert,update,delete만 지연쓰기가 된다.
            System.out.println("커밋 후");

            Employee e2 = em.find(Employee.class, "202301");
            //맵에서도 지워지고, db에서도 못가져옴 --> db에서 찾으려고 함
            System.out.println("DB에서 가져옴");
            System.out.println("e2 is null? ==>"+(e2));

        } catch (Exception e) {
            e.getMessage();
            tx.rollback();
        }
        finally {
            System.out.println("커밋 후");
            //SQL문이 날라감,DB로 간다. 이렇게 하면 트렌젝션이 종료된다.
            em.close();
            emf.close();

        }



    }
}
