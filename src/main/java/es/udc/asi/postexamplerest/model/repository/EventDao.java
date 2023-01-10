package es.udc.asi.postexamplerest.model.repository;

import java.util.List;

import es.udc.asi.postexamplerest.model.domain.Event;
import es.udc.asi.postexamplerest.model.service.dto.EventSortType;

public interface EventDao {
  List<Event> findAll(String filter, String category, EventSortType sort);

  Event findById(Long id);

  void create(Event event);

  void update(Event event);

  void deleteById(Long id);

  List<Event> findAllByCategory(Long categoryId);
}
