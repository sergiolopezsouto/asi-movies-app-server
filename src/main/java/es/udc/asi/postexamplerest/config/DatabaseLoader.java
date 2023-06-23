package es.udc.asi.postexamplerest.config;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import es.udc.asi.postexamplerest.model.domain.Movie;
import es.udc.asi.postexamplerest.model.domain.Category;
import es.udc.asi.postexamplerest.model.domain.User;
import es.udc.asi.postexamplerest.model.exception.UserLoginExistsException;
import es.udc.asi.postexamplerest.model.repository.MovieDao;
import es.udc.asi.postexamplerest.model.repository.CategoryDao;
import es.udc.asi.postexamplerest.model.repository.UserDao;
import es.udc.asi.postexamplerest.model.service.UserService;

@Configuration
public class DatabaseLoader {

  @Autowired
  private UserDao userDAO;

  @Autowired
  private UserService userService;

  @Autowired
  private MovieDao movieDAO;

  @Autowired
  private CategoryDao categoryDAO;

  /*
   * Para hacer que la carga de datos sea transacional, hay que cargar el propio
   * objeto como un bean y lanzar el método una vez cargado, ya que en el
   * PostConstruct (ni similares) se tienen en cuenta las anotaciones de
   * transaciones.
   */

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void loadData() throws UserLoginExistsException, InterruptedException {
    userService.registerUser("pepe", "pepe", true);
    userService.registerUser("maria", "maria", true);
    userService.registerUser("laura", "laura");
    userService.registerUser("pedro", "pedro");
    User pedro = userDAO.findByLogin("pedro");
    pedro.setActive(false);
    userDAO.update(pedro);
    userService.registerUser("ramón", "ramón");

    Category sports = new Category("sports");
    Category cultural = new Category("cultural");
    Category art = new Category("art");

    categoryDAO.create(sports);
    categoryDAO.create(cultural);
    categoryDAO.create(art);
    
    Movie movie = new Movie("football match", userDAO.findByLogin("pepe"));
    movie.setCategory(sports);
    movieDAO.create(movie);
    Thread.sleep(2000);
    
    movie = new Movie("movie 2", userDAO.findByLogin("maria"));
    movie.setCategory(cultural);
//    movie.setDate(LocalDateTime.now());
    movieDAO.create(movie);
    Thread.sleep(2000);
    
    movie = new Movie("paintings expo", userDAO.findByLogin("maria"));
    movie.setCategory(art);
//    movie.setDate(LocalDateTime.now().plusDays(1)); // fijar a mañana
    movieDAO.create(movie);
    Thread.sleep(2000);
    
    movie = new Movie("movie 4", userDAO.findByLogin("maria"));
    movie.setCategory(art);
    movieDAO.create(movie);
    Thread.sleep(2000);
    
    movie = new Movie("movie 5", userDAO.findByLogin("pepe"));
    movie.setCategory(art);
    movieDAO.create(movie);
    Thread.sleep(2000);
    
    movie = new Movie("movie 6", userDAO.findByLogin("pepe"));
    movie.setCategory(art);
    movieDAO.create(movie);
  }
}
