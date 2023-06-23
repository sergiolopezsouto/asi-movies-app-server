package es.udc.asi.postexamplerest.model.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.asi.postexamplerest.model.domain.Director;
import es.udc.asi.postexamplerest.model.repository.util.GenericDaoJpa;

@Repository
public class DirectorDaoJpa extends GenericDaoJpa implements DirectorDao {

  @Override
  public List<Director> findAll() {
    return entityManager.createQuery("from Director", Director.class).getResultList();
  }

  @Override
  public Director findById(Long id) {
    return entityManager.find(Director.class, id);
  }

  @Override
  public void create(Director director) {
    entityManager.persist(director);
  }

  @Override
  public void update(Director director) {
    entityManager.merge(director);
  }

  @Override
  public void delete(Director director) {
    entityManager.remove(director);
  }
}
