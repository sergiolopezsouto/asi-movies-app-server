package es.udc.asi.postexamplerest.model.service.dto;

import java.util.ArrayList;
import java.util.List;

import es.udc.asi.postexamplerest.model.domain.User;

public class UserDTOWithMovies {
  private Long id;
  private String login;
  private boolean active = true;
  private List<MovieDTO> favoriteMovies = new ArrayList<>();

  public UserDTOWithMovies() {
  }

  public UserDTOWithMovies(User user) {
    this.id = user.getId();
    this.login = user.getLogin();
    this.setActive(user.isActive());
    user.getFavoriteMovies().forEach(e -> {
      this.favoriteMovies.add(new MovieDTO(e));
    });
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public List<MovieDTO> getFavoriteMovies() {
	return favoriteMovies;
  }

  public void setFavoriteMovies(List<MovieDTO> favoriteMovies) {
	this.favoriteMovies = favoriteMovies;
  }
}
