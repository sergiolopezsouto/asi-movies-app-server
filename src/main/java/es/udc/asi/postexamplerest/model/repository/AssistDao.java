package es.udc.asi.postexamplerest.model.repository;

import java.util.List;

import es.udc.asi.postexamplerest.model.domain.Assist;

public interface AssistDao {
  List<Assist> findAll();

  List<Assist> findByEvent(Long id);
  
  List<Assist> findByUser(Long id);

  void create(Assist assist);

  void delete(Assist assist);
  
}
