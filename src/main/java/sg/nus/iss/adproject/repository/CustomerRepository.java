package sg.nus.iss.adproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.adproject.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	@Query("Select c FROM Customer c WHERE c.id= :id")
	List<Customer> findCustomerById(@Param("id")int id);
	
	
	
}
