package es.udc.asi.postexamplerest.model.exception;

public class UserLoginExistsException extends ModelException {
  public UserLoginExistsException(String login) {
    super("User login " + login + " already exists");
  }
}
