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

  @Autowired
  private UserDao userDAO;

  @Autowired
  private UserService userService;

  @Autowired
  private EventDao eventDAO;

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

    Event event = new Event("football match", userDAO.findByLogin("pepe"));
    event.setCategory(sports);
    eventDAO.create(event);
    Thread.sleep(2000);
    
    event = new Event("event 2", userDAO.findByLogin("maria"));
    event.setCategory(cultural);
    eventDAO.create(event);
    Thread.sleep(2000);
    
    event = new Event("paintings expo", userDAO.findByLogin("maria"));
    event.setCategory(art);
    eventDAO.create(event);
    Thread.sleep(2000);
    
    event = new Event("event 4", userDAO.findByLogin("maria"));
    event.setCategory(art);
    eventDAO.create(event);
    Thread.sleep(2000);
    
    event = new Event("event 5", userDAO.findByLogin("pepe"));
    eventDAO.create(event);
    Thread.sleep(2000);
    
    event = new Event("event 6", userDAO.findByLogin("pepe"));
    eventDAO.create(event);
  }
}
