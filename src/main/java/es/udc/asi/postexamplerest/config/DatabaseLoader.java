package es.udc.asi.postexamplerest.config;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import es.udc.asi.postexamplerest.model.domain.Movie;
import es.udc.asi.postexamplerest.model.domain.Category;
import es.udc.asi.postexamplerest.model.domain.Director;
import es.udc.asi.postexamplerest.model.domain.User;
import es.udc.asi.postexamplerest.model.exception.UserLoginExistsException;
import es.udc.asi.postexamplerest.model.repository.MovieDao;
import es.udc.asi.postexamplerest.model.repository.CategoryDao;
import es.udc.asi.postexamplerest.model.repository.DirectorDao;
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
  
  @Autowired
  private DirectorDao directorDAO;

  /*
   * Para hacer que la carga de datos sea transacional, hay que cargar el propio
   * objeto como un bean y lanzar el método una vez cargado, ya que en el
   * PostConstruct (ni similares) se tienen en cuenta las anotaciones de
   * transaciones.
   */

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void loadData() throws UserLoginExistsException, InterruptedException {
	  
	// crear usuarios
    userService.registerUser("pepe", "pepe", true);
    userService.registerUser("maria", "maria", true);
    userService.registerUser("laura", "laura");
    userService.registerUser("pedro", "pedro");
    User pedro = userDAO.findByLogin("pedro");
    pedro.setActive(false);
    userDAO.update(pedro);
    userService.registerUser("ramón", "ramón");

    // crear categorías
    Category science = new Category("Science");
    Category action = new Category("Action");
    categoryDAO.create(science);
    categoryDAO.create(action);
    
    // Crear directores
    Director nolan = new Director("Christopher Nolan");
    Director burton = new Director("Tim Burton");
    directorDAO.create(nolan);
    directorDAO.create(burton);
    
    
//    Movie movie = new Movie("football match", userDAO.findByLogin("pepe"));
//    movie.setCategory(action);
//    movieDAO.create(movie);
//    Thread.sleep(2000);
//    
//    movie = new Movie("movie 2", userDAO.findByLogin("maria"));
//    movie.setCategory(science);
//    movieDAO.create(movie);
//    Thread.sleep(2000);
    
    // Crear primera película
    Movie movie1 = new Movie("Interstellar", userDAO.findByLogin("pepe"));
    movie1.setSynopsis("A group of explorers make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage.");
    movie1.setImagePath("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.primevideo.com%2Fdetail%2FInterestelar%2F0GURDFFNEFFK5UUUGQ3JT3HN1T%3F_encoding%3DUTF8%26language%3Des_ES&psig=AOvVaw0l0JW63Nmi3LwlbrdROQPA&ust=1687715250392000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCLDbx7G73P8CFQAAAAAdAAAAABAE");
    movie1.setDuration(169); 
    movie1.setTrailerUrl("https://www.youtube.com/watch?v=zSWdZVtXT7E");
    movie1.setReleaseDate(LocalDateTime.of(2014, 11, 5, 0, 0));
    movie1.setDirector(nolan); 
    movie1.setCategory(science);
    movieDAO.create(movie1);
    Thread.sleep(2000);

    // Crear segunda película
    Movie movie2 = new Movie("The Dark Knight", userDAO.findByLogin("maria"));
    movie2.setSynopsis("When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.");
    movie2.setImagePath("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.amazon.es%2FDark-Knight-Edizione-Regno-Unito%2Fdp%2FB003IHU5DO&psig=AOvVaw2oN0-Obta8SOlj-VI5mBiJ&ust=1687715279165000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCKjCwr-73P8CFQAAAAAdAAAAABAE");
    movie2.setDuration(152); 
    movie2.setTrailerUrl("https://www.youtube.com/watch?v=EXeTwQWrcwY");
    movie2.setReleaseDate(LocalDateTime.of(2008, 7, 18, 0, 0));
    movie2.setDirector(nolan);
    movie2.setCategory(action);
    movieDAO.create(movie2);
    Thread.sleep(2000);

    
//    movie = new Movie("paintings expo", userDAO.findByLogin("maria"));
//    movie.setCategory(art);
//    movieDAO.create(movie);
//    Thread.sleep(2000);
//    
//    movie = new Movie("movie 4", userDAO.findByLogin("maria"));
//    movie.setCategory(art);
//    movieDAO.create(movie);
//    Thread.sleep(2000);
//    
//    movie = new Movie("movie 5", userDAO.findByLogin("pepe"));
//    movie.setCategory(art);
//    movieDAO.create(movie);
//    Thread.sleep(2000);
//    
//    movie = new Movie("movie 6", userDAO.findByLogin("pepe"));
//    movie.setCategory(art);
//    movieDAO.create(movie);
  }
}
