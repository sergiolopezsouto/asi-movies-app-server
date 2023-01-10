package es.udc.asi.postexamplerest.model.repository;

import java.util.List;

import es.udc.asi.postexamplerest.model.domain.Category;

public interface CategoryDao {

  List<Category> findAll();

  Category findById(Long id);

  void create(Category category);

  void update(Category category);

  void delete(Category category);
}
