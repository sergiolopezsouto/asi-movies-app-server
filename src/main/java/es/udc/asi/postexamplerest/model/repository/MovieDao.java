package es.udc.asi.postexamplerest.model.repository;

import java.util.List;

import es.udc.asi.postexamplerest.model.domain.Event;
import es.udc.asi.postexamplerest.model.domain.Movie;
import es.udc.asi.postexamplerest.model.service.dto.EventSortType;

public interface MovieDao {

/*	
  List<Event> findAll(String filter, String category, EventSortType sort);
*/
	
  List<Movie> findAll();
	
  Movie findById(Long id);

  void create(Movie movie);

  void update(Movie movie);

  void deleteById(Long id);

  List<Movie> findAllByCategory(Long categoryId);
  
}
