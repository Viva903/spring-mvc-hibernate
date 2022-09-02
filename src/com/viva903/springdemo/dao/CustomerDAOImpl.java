package com.viva903.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.viva903.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// create a query ... sort by last name
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class);

		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();

		// return the results
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {

		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// save/upate the customer ... finally LOL
		currentSession.saveOrUpdate(theCustomer);

	}

	@Override
	public Customer getCustomer(int theId) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// now retrieve/read from database using the primary key
		Customer theCustomer = currentSession.get(Customer.class, theId);

		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theCustomerId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// Delete the customer with the specified id.
		// 1st option: 2 db operations: get, delete
//		Customer theCustomer = currentSession.get(Customer.class, theCustomerId);
//		currentSession.delete(theCustomer);

		// 2nd option: 1 db operations: delete
		Query theQuery = currentSession.createQuery("delete from Customer where id=:customerId", Customer.class);
		theQuery.setParameter("customerId", theCustomerId);
		theQuery.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {

		Session currentSession = sessionFactory.getCurrentSession();
		Query theQuery = null;

//		Search query applied when search entry is not null or empty after trimming all spaces leading or trailing
//		else returning the entire list of customers
//		Make use of "like", "%" for substring search also
		if (theSearchName != null && theSearchName.trim().length() > 0) {
			theQuery = currentSession.createQuery(
					"from Customer where lower(lastName) like :theName or lower(firstName) like :theName",
					Customer.class);
			theQuery.setParameter("theName", "%" + theSearchName + "%");
		} else {
			theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class);

		}

		List<Customer> customers = theQuery.getResultList();

		return customers;
	}

}
