package es.udc.asi.postexamplerest.model.repository;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import es.udc.asi.postexamplerest.model.domain.User;
import es.udc.asi.postexamplerest.model.repository.util.GenericDaoJpa;

@Repository
public class UserDaoJpa extends GenericDaoJpa implements UserDao {

  @Override
  public List<User> findAll() {
    return entityManager.createQuery("from User", User.class).getResultList();
  }

  @Override
  public User findById(Long id) {
    return entityManager.find(User.class, id);
  }

  @Override
  public User findByLogin(String login) {
    TypedQuery<User> query = entityManager.createQuery("from User u where u.login = :login", User.class)
        .setParameter("login", login);
    return DataAccessUtils.singleResult(query.getResultList());
  }

  @Override
  public void create(User user) {
    entityManager.persist(user);
  }

  @Override
  public void update(User user) {
    entityManager.merge(user);
  }

  @Override
  public void deleteById(Long id) {
    User user = findById(id);
    delete(user);
  }

  private void delete(User event) {
    entityManager.remove(event);
  }
}
