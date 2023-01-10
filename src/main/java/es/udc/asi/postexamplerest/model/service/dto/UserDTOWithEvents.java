package es.udc.asi.postexamplerest.model.service.dto;

import java.util.ArrayList;
import java.util.List;

import es.udc.asi.postexamplerest.model.domain.User;

public class UserDTOWithEvents {
  private Long id;
  private String login;
  private boolean active = true;
  private List<EventDTO> events = new ArrayList<>();

  public UserDTOWithEvents() {
  }

  public UserDTOWithEvents(User user) {
    this.id = user.getId();
    this.login = user.getLogin();
    this.setActive(user.isActive());
    user.getEvents().forEach(e -> {
      this.events.add(new EventDTO(e));
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

  public List<EventDTO> getEvents() {
    return events;
  }

  public void setPosts(List<EventDTO> events) {
    this.events = events;
  }
}
