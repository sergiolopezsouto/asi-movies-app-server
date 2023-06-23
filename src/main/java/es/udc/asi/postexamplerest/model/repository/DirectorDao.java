package es.udc.asi.postexamplerest.model.repository;

import java.util.List;
import es.udc.asi.postexamplerest.model.domain.Director;

public interface DirectorDao {
	
  List<Director> findAll();
  
  Director findById(Long id);
  
  void create(Director director);
  
  void update(Director director);
  
  void delete(Director director);
  
}
