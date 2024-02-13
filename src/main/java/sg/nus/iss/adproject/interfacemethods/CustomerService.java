package sg.nus.iss.adproject.interfacemethods;

import java.util.List;

import sg.nus.iss.adproject.model.Customer;

public interface CustomerService {

	List<Customer> findCustomerById(int id);
	

}
