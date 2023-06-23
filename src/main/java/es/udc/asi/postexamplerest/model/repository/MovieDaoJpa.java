package es.udc.asi.postexamplerest.model.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.asi.postexamplerest.model.domain.Category;
import es.udc.asi.postexamplerest.model.domain.Event;
import es.udc.asi.postexamplerest.model.domain.Movie;
import es.udc.asi.postexamplerest.model.repository.util.GenericDaoJpa;
import es.udc.asi.postexamplerest.model.service.dto.EventSortType;

import javax.persistence.TypedQuery;

@Repository
public class MovieDaoJpa extends GenericDaoJpa implements MovieDao {

/*	
  @Override
  public List<Event> findAll(String filter, String category, EventSortType sort) {
    Boolean hasFilter = filter != null;
    Boolean hasCategory = category != null;
    Boolean isSorted = sort != null;

    String queryStr = "select e from Event e";

    if (isSorted && sort == EventSortType.AUTHOR_NAME) {
      queryStr += " join e.author a";
    }

    if (hasFilter || hasCategory) {
      if (hasCategory) {
        queryStr += " join e.category c";
      }
      queryStr += " where ";
      if (hasFilter) {
        queryStr += "(e.description like :filter or e.author.login like :filter)";
      }
      if (hasFilter && hasCategory) {
        queryStr += " and ";
      }
      if (hasCategory) {
        queryStr += "c.name like :category";
      }
    }

    String sortStr = "e.id asc";
    if (isSorted) {
      switch (sort) {
        case AUTHOR_NAME:
          sortStr = "a.login asc";
          break;
        case LESS_RECENT:
          sortStr = "e.timestamp asc";
          break;
        case MOST_RECENT:
          sortStr = "e.timestamp desc";
          break;
      }
    }
    queryStr += " order by " + sortStr;

    TypedQuery<Event> query = entityManager.createQuery(queryStr, Event.class);
    if (hasFilter) {
      query.setParameter("filter", "%" + filter + "%");
    }
    if (hasCategory) {
      query.setParameter("category", "%" + category + "%");
    }

    return query.getResultList();
  }
*/
	
  @Override
  public List<Movie> findAll() {
	return entityManager.createQuery("from Movie", Movie.class).getResultList();
  }

  @Override
  public Movie findById(Long id) {
    return entityManager.find(Movie.class, id);
  }

  @Override
  public void create(Movie movie) {
    entityManager.persist(movie);
  }

  @Override
  public void update(Movie movie) {
    entityManager.merge(movie);
  }

  @Override
  public void deleteById(Long id) {
	Movie movie = findById(id);
    delete(movie);
  }

//  @Override
//  public List<Event> findAllByCategory(Long categoryId) {
//    return entityManager.createQuery("select e from Event e join e.categories ec where ec.id = :categoryId", Event.class)
//        .setParameter("categoryId", categoryId).getResultList();
//  }
  
  @Override
  public List<Movie> findAllByCategory(Long categoryId) {
    return entityManager.createQuery("SELECT m FROM Movie m JOIN m.category c WHERE c.id = :categoryId", Movie.class)
          .setParameter("categoryId", categoryId)
          .getResultList();
  }

  private void delete(Movie movie) {
    entityManager.remove(movie);
  }
}
