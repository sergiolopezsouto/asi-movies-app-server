package es.udc.asi.postexamplerest.model.service.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import es.udc.asi.postexamplerest.model.domain.Category;
import es.udc.asi.postexamplerest.model.domain.Event;

public class EventDTO {
  private Long id;
  @NotEmpty
  private String description;
  @NotNull
  private UserDTOPublic author;
  private Category category;
  private Boolean hasImage = false;
  private LocalDateTime timestamp;

  
  public EventDTO() {
  }

  public EventDTO(Event event) {
    this.id = event.getId();
    this.description = event.getDescription();
    this.author = new UserDTOPublic(event.getAuthor());
    this.category= event.getCategory();
    if (event.getImagePath() != null) {
      this.hasImage = true;
    }
    this.timestamp = event.getTimestamp();
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public UserDTOPublic getAuthor() {
    return author;
  }

  public void setAuthor(UserDTOPublic author) {
    this.author = author;
  }

  public Category getCategory() {
	return category;
}

  public void setCategory(Category category) {
	this.category = category;
  }

  public Boolean getHasImage() {
    return hasImage;
  }
	
  public LocalDateTime getTimestamp() {
    return timestamp;
  }
}