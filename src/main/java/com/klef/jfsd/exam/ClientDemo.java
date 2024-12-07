package com.klef.jfsd.exam;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class ClientDemo {
	public static void main(String[] args) {
		ClientDemo demo = new ClientDemo();
		//demo.add
		demo.performTask();
	}

	public void addCustomer()
	{
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");

		SessionFactory sf = configuration.buildSessionFactory();
		Session session = sf.openSession();
		Transaction t = session.beginTransaction();

		Customer customer = new Customer();
		customer.setName("Saketh");
		customer.setAge(19.5);
		customer.setEmail("saketh@gmail.com");
		customer.setLocation("guntur");

		session.persist(customer);
		t.commit();
		session.close();
		sf.close();
	}
	
	public void performTask() {
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");

		SessionFactory sf = configuration.buildSessionFactory();
		Session session = sf.openSession();

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
		Root<Customer> root = cq.from(Customer.class);

		cq.select(root).where(cb.lessThan(root.get("id"), 5));
		cq.select(root).where(cb.greaterThan(root.get("id"), 1));
		cq.select(root).where(cb.equal(root.get("age"), 19.5));
		cq.select(root).where(cb.notEqual(root.get("location"), "ten"));
		cq.select(root).where(cb.between(root.get("id"), 0, 5));
		cq.select(root).where(cb.like(root.get("location"), "__n"));

		List<Customer> customerList = session.createQuery(cq).getResultList();
		for (var cust : customerList) {
			System.out.println(cust.toString());
		}
	}

}
