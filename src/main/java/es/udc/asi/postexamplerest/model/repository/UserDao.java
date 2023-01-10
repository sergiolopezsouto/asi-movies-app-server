package es.udc.asi.postexamplerest.model.repository;

import java.util.List;

import es.udc.asi.postexamplerest.model.domain.User;

public interface UserDao {
  List<User> findAll();

  User findById(Long id);

  User findByLogin(String login);

  void create(User user);

  void update(User user);

  void deleteById(Long id);
}
