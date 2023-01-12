package es.udc.asi.postexamplerest;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

import es.udc.asi.postexamplerest.config.DatabaseLoader;
import es.udc.asi.postexamplerest.model.exception.UserLoginExistsException;

@SpringBootApplication
public class RestExampleApplication {
  private final Logger logger = LoggerFactory.getLogger(RestExampleApplication.class);

  @Autowired
  @Lazy
  private DatabaseLoader databaseLoader;

  public static void main(String[] args) {
    SpringApplication.run(RestExampleApplication.class, args);
  }

  @PostConstruct
  public void init() throws InterruptedException {
    try {
      databaseLoader.loadData();
    } catch (UserLoginExistsException e) {
      logger.error(e.getMessage(), e);
    }
  }
}
