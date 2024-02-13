package sg.nus.iss.adproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.adproject.interfacemethods.CustomerService;
import sg.nus.iss.adproject.model.Customer;
import sg.nus.iss.adproject.repository.CustomerRepository;

@Service
@Transactional(readOnly=true)
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	CustomerRepository customerRepository;
	
	
	@Override
	public List<Customer> findCustomerById(int id) {
		// TODO Auto-generated method stub
		return customerRepository.findCustomerById(id);
	}


}
