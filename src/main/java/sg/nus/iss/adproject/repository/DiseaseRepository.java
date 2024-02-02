package sg.nus.iss.adproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.adproject.model.Disease;

public interface DiseaseRepository extends JpaRepository<Disease, Integer>{

}
