package es.udc.asi.postexamplerest.model.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Movie {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_generator")
  @SequenceGenerator(name = "event_generator", sequenceName = "event_seq")
  private Long id;
  
  @Column(length = 1000)
  private String imagePath;
  
  private String title;
  
  private Number duration;
  
  @Column(length = 10485760)
  private String synopsis;
  
  private String trailerUrl;
  
//  private String director;
  
  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  private Director director;

  private LocalDateTime releaseDate;

  private LocalDateTime timestamp = LocalDateTime.now();
  
  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  private Category category;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private User author;
  
  @ManyToMany(mappedBy = "favoriteMovies")
  private Set<User> likedByUsers = new HashSet<>();


  
  
  
  public Movie() {
  }

  public Movie(String title) {
    this.title = title;
  }

  public Movie(String title, User author) {
    this(title);
    this.author = author;
  }
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
  
  public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public String getSynopsis() {
	return synopsis;
}

public void setSynopsis(String synopsis) {
	this.synopsis = synopsis;
}

public String getTrailerUrl() {
	return trailerUrl;
}

public void setTrailerUrl(String trailerUrl) {
	this.trailerUrl = trailerUrl;
}

//public String getDirector() {
//	return director;
//}
//
//public void setDirector(String director) {
//	this.director = director;
//}

public Director getDirector() {
    return director;
  }

  public void setDirector(Director director) {
    this.director = director;
  }

public LocalDateTime getReleaseDate() {
	return releaseDate;
}

public void setReleaseDate(LocalDateTime releaseDate) {
	this.releaseDate = releaseDate;
}

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public Number getDuration() {
	return duration;
  }

  public void setDuration(Number duration) {
	this.duration = duration;
  }
  
  public Set<User> getLikedByUsers() {
	return likedByUsers;
  }

  public void setLikedByUsers(Set<User> likedByUsers) {
	this.likedByUsers = likedByUsers;
  }
}