package es.udc.asi.postexamplerest.model.service.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import es.udc.asi.postexamplerest.model.domain.Category;
import es.udc.asi.postexamplerest.model.domain.Event;

public class EventDTO {
  private Long id;
  @NotEmpty
  private String title;
  public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

@NotNull
  private UserDTOPublic author;
  private CategoryDTO category;
  private String categoryName;
  private String description;
  private Boolean hasImage = false;
  private LocalDateTime date;

  
  public EventDTO() {
  }

  public EventDTO(Event event) {
	    this.id = event.getId();
	    this.title = event.getTitle();
	    this.description = event.getDescription();
	    this.author = new UserDTOPublic(event.getAuthor());

	    if (event.getCategory() != null) {
	        this.categoryName = event.getCategory().getName();
	        this.category = new CategoryDTO(event.getCategory()); 
	    }

	    if (event.getImagePath() != null) {
	      this.hasImage = true;
	    }

	    this.date = event.getDate();
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

  public CategoryDTO getCategory() {
	return category;
}

  public void setCategory(CategoryDTO category) {
	this.category = category;
  }

  public Boolean getHasImage() {
    return hasImage;
  }
	
  public LocalDateTime getDate() {
    return date;
  }
}