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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Event {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_generator")
  @SequenceGenerator(name = "event_generator", sequenceName = "event_seq")
  private Long id;
  
  private String imagePath;
  
  private String title;
  
  @Column(length = 10485760)
  private String description;
  
  private String place;

  private LocalDateTime date;

  private LocalDateTime timestamp = LocalDateTime.now();
  
  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  private Category category;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private User author;
  
  @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
  private Set<Assist> assist = new HashSet<>();


  
  
  
  public Event() {
  }

  public Event(String title) {
    this.title = title;
  }

  public Event(String title, User author) {
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

public String getPlace() {
	return place;
}

public void setPlace(String place) {
	this.place = place;
}

public LocalDateTime getDate() {
	return date;
}

public void setDate(LocalDateTime date) {
	this.date = date;
}

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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
}