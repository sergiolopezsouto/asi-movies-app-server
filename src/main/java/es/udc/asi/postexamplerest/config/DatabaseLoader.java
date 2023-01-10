package es.udc.asi.postexamplerest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import es.udc.asi.postexamplerest.model.domain.Event;
import es.udc.asi.postexamplerest.model.domain.Category;
import es.udc.asi.postexamplerest.model.domain.User;
import es.udc.asi.postexamplerest.model.exception.UserLoginExistsException;
import es.udc.asi.postexamplerest.model.repository.EventDao;
import es.udc.asi.postexamplerest.model.repository.CategoryDao;
import es.udc.asi.postexamplerest.model.repository.UserDao;
import es.udc.asi.postexamplerest.model.service.UserService;

@Configuration
public class DatabaseLoader {
	/*
  @Autowired
  private UserDao userDAO;

  @Autowired
  private UserService userService;

  @Autowired
  private EventDao eventDAO;

  @Autowired
  private TagDao tagDAO;

  /*
   * Para hacer que la carga de datos sea transacional, hay que cargar el propio
   * objeto como un bean y lanzar el método una vez cargado, ya que en el
   * PostConstruct (ni similares) se tienen en cuenta las anotaciones de
   * transaciones.
   */
	/*
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

    Tag news = new Tag("news");
    Tag podcast = new Tag("podcast");
    Tag tech = new Tag("tech");

    tagDAO.create(news);
    tagDAO.create(podcast);
    tagDAO.create(tech);

    Post post = new Post("Texto del primer post", userDAO.findByLogin("pepe"));
    post.getTags().add(news);
    post.getTags().add(podcast);
    postDAO.create(post);
    Thread.sleep(2000);
    post = new Post("Texto del segundo post", userDAO.findByLogin("maria"));
    post.getTags().add(news);
    post.getTags().add(tech);
    postDAO.create(post);
    Thread.sleep(2000);
    post = new Post("Texto del tercero post", userDAO.findByLogin("maria"));
    postDAO.create(post);
    Thread.sleep(2000);
    post = new Post("Texto del cuarto post", userDAO.findByLogin("maria"));
    postDAO.create(post);
    Thread.sleep(2000);
    post = new Post("Texto del quinto post", userDAO.findByLogin("pepe"));
    postDAO.create(post);
    Thread.sleep(2000);
    post = new Post("Texto del sexto post", userDAO.findByLogin("pepe"));
    postDAO.create(post);
  }*/
}
