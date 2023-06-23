package es.udc.asi.postexamplerest.model.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "theUser")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
  @SequenceGenerator(name = "user_generator", sequenceName = "user_seq")
  private Long id;

  @Column(unique = true)
  private String login;

  private String password;

  @Enumerated(EnumType.STRING)
  private UserAuthority authority;

  @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
  private List<Event> events = new ArrayList<>();
  
  
  @ManyToMany(cascade = {
	CascadeType.PERSIST,
	CascadeType.MERGE
  })
  @JoinTable(name = "user_favorite_movies",
  	joinColumns = @JoinColumn(name = "user_id"),
  	inverseJoinColumns = @JoinColumn(name = "movie_id")
	)
  private Set<Movie> favoriteMovies = new HashSet<>();

  private boolean active = true;

  
  
  
  public User() {
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  
  public Set<Movie> getFavoriteMovies() {
	return favoriteMovies;
  }

  public void setFavoriteMovies(Set<Movie> favoriteMovies) {
    this.favoriteMovies = favoriteMovies;
  }

  public UserAuthority getAuthority() {
    return authority;
  }

  public void setAuthority(UserAuthority authority) {
    this.authority = authority;
  }

  public List<Event> getEvents() {
    return events;
  }

  public void setEvents(List<Event> events) {
    this.events = events;
  }
  
//  public Set<Assist> getAssist() {
//	return assist;
//}
//
//  public void setAssist(Set<Assist> assist) {
//	this.assist = assist;
//}
  public void addFavoriteMovie(Movie movie) {
	favoriteMovies.add(movie);
	movie.getLikedByUsers().add(this);
  }

  // Método helper para eliminar una película de favoritos
  public void removeFavoriteMovie(Movie movie) {
	favoriteMovies.remove(movie);
	movie.getLikedByUsers().remove(this);
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
