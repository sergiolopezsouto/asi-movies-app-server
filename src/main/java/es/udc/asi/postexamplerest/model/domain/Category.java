package es.udc.asi.postexamplerest.model.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_generator")
  @SequenceGenerator(name = "category_generator", sequenceName = "category_seq")
  private Long id;

  @Column(unique = true)
  private String name;

  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
  private Set<Event> events = new HashSet<>();

  
  
  public Category() {
  }

  public Category(String name) {
    this.name = name;
  }

  
  
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Category category = (Category) o;
    return id.equals(category.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  
  
  public Category(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Event> getEvents() {
    return events;
  }

  public void setEvents(Set<Event> events) {
    this.events = events;
  }
}
