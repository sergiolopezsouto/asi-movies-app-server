package es.udc.asi.postexamplerest.model.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.asi.postexamplerest.model.domain.Category;
import es.udc.asi.postexamplerest.model.repository.util.GenericDaoJpa;

@Repository
public class CategoryDaoJpa extends GenericDaoJpa implements CategoryDao {

  @Override
  public List<Category> findAll() {
    return entityManager.createQuery("from Category", Category.class).getResultList();
  }

  @Override
  public Category findById(Long id) {
    return entityManager.find(Category.class, id);
  }

  @Override
  public void create(Category category) {
    entityManager.persist(category);
  }

  @Override
  public void update(Category category) {
    entityManager.merge(category);
  }

  @Override
  public void delete(Category category) {
    entityManager.remove(category);
  }
}
