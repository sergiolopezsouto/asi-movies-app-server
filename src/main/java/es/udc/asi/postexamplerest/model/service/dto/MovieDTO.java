package es.udc.asi.postexamplerest.model.service.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import es.udc.asi.postexamplerest.model.domain.Category;
import es.udc.asi.postexamplerest.model.domain.Movie;

public class MovieDTO {
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
  private Number duration;
  private String categoryName;
  private String director;
  private String synopsis;
  private String trailerUrl;
  private Boolean hasImage = false;
  private LocalDateTime releaseDate;

  
  public MovieDTO() {
  }

  public MovieDTO(Movie movie) {
	    this.id = movie.getId();
	    this.title = movie.getTitle();
	    this.synopsis = movie.getSynopsis();
	    this.author = new UserDTOPublic(movie.getAuthor());

	    if (movie.getCategory() != null) {
	        this.categoryName = movie.getCategory().getName();
	        this.category = new CategoryDTO(movie.getCategory()); 
	    }

	    if (movie.getImagePath() != null) {
	      this.hasImage = true;
	    }

	    this.releaseDate = movie.getReleaseDate();
	    this.director = movie.getDirector(); 
	    this.trailerUrl = movie.getTrailerUrl(); 	    
	    this.duration = movie.getDuration(); 
	}


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSynopsis() {
    return synopsis;
  }

  public void setSynopsis(String synopsis) {
    this.synopsis = synopsis;
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
	
  public LocalDateTime getReleaseDate() {
    return releaseDate;
  }

  public String getDirector() {
	return director;
  }
  
  public String getTrailerUrl() {
	return trailerUrl;
  }
  
  public Number getDuration() {
	return duration;
  }
}