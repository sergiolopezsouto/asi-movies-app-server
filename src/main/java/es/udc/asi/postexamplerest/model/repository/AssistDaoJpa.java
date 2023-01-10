package es.udc.asi.postexamplerest.model.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.asi.postexamplerest.model.domain.Assist;
import es.udc.asi.postexamplerest.model.repository.util.GenericDaoJpa;

@Repository
public class AssistDaoJpa extends GenericDaoJpa implements AssistDao {

  @Override
  public List<Assist> findAll() {
    return entityManager.createQuery("from Assist", Assist.class).getResultList();
  }
  
  @Override
  public List<Assist> findByEvent(Long id) {
    return entityManager.createQuery("from Assist", Assist.class)
    		.setParameter("id_event", id).getResultList();
  }

  @Override
  public List<Assist> findByUser(Long id) {
	return entityManager.createQuery("from Assist", Assist.class)
			.setParameter("id_assistant", id).getResultList();  }
  
  @Override
  public void create(Assist assist) {
    entityManager.persist(assist);
  }

  @Override
  public void delete(Assist assist) {
    entityManager.remove(assist);
  }
}
