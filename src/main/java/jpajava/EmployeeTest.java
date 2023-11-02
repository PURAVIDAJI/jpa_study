package jpajava;

import domain.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EmployeeTest {
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

        //비영속 상태로 객체 한 개 만들기
        Employee emp = new Employee();
        emp.setEmpId("202301");
        emp.setEmpName("박치수");
        emp.setDeptId(1);
        emp.setJoinDate("2023-01-01");
        emp.setSalary(100_000_000L);
        System.out.println("비영속 상태");

        em.persist(emp);

        System.out.println("영속 상태");
        em.find(Employee.class, "202301");
        //캐시에서 들고오기 -> 타입과, id로
        System.out.println("1차 캐시에서 가져옴");
        System.out.println("커밋 전");

        tx.commit();

        System.out.println("커밋 후");
        //SQL문이 날라감,DB로 간다. 이렇게 하면 트렌젝션이 종료된다.
        em.close();
        emf.close();
    }
}
