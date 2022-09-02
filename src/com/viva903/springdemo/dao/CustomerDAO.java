package com.viva903.springdemo.dao;

import java.util.List;

import com.viva903.springdemo.entity.Customer;

public interface CustomerDAO {

	public List<Customer> getCustomers();

	public void saveCustomer(Customer theCustomer);

	public Customer getCustomer(int theId);

	public void deleteCustomer(int theCustomerId);

	public List<Customer> searchCustomers(String theSearchName);
	
}
