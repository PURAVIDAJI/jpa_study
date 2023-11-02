package jpajava;

import domain.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EmployeeFlushTest {
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
            //db에서 가져옴
            System.out.println("DB에서 가져옴");
            System.out.println("영속 상태");


            System.out.println("수정 전 ==>");
            e1.setDeptId(10);
            System.out.println("수정 발생");
            em.persist(e1);
            System.out.println("수정 후 ==>");

            em.flush();
            System.out.println("flush 직접호출");
            //쓰기 지연 sql문에 있던 쿼리를 날려줘! -> 이때 db에 업데이트 됨

            System.out.println("커밋 전 ==>");
            tx.commit();
            //flush 했으므로 커밋은 일어나지 않음
            System.out.println("커밋 후 ==>");

            Employee e2 = em.find(Employee.class, "202301");
            //1차 캐시에서 가져옴...
            System.out.println("e1 == e2? ==>" + (e1==e2));


        } catch (Exception e) {
            System.out.println(e.getMessage());
            tx.rollback();
        }
        finally {

            em.close();
            emf.close();

        }



    }
}
