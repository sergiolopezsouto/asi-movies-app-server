package es.udc.asi.postexamplerest.model.service.dto;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

import es.udc.asi.postexamplerest.model.domain.Director;

public class DirectorDTO {
  private Long id;
  @NotEmpty
  private String name;
  private LocalDate birthDate;
  private String imageUrl;

  public DirectorDTO() {
  }

  public DirectorDTO(Director director) {
    this.id = director.getId();
    this.name = director.getName();
    this.birthDate = director.getBirthDate();
    this.imageUrl = director.getImageUrl();
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

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
