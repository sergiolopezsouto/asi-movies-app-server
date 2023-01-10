package es.udc.asi.postexamplerest.security;

public class JWTToken {
  private String token;

  public JWTToken(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
