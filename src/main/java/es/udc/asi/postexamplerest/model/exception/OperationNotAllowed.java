package es.udc.asi.postexamplerest.model.exception;

public class OperationNotAllowed extends ModelException {

  public OperationNotAllowed(String msg) {
    super("Operation not allowed: " + msg);
  }
}
