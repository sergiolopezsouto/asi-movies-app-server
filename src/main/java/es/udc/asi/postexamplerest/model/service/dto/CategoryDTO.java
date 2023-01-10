package es.udc.asi.postexamplerest.model.service.dto;

import javax.validation.constraints.NotEmpty;

import es.udc.asi.postexamplerest.model.domain.Category;

public class CategoryDTO {
  private Long id;
  @NotEmpty
  private String name;

  
  public CategoryDTO() {
  }

  public CategoryDTO(Category category) {
    this.id = category.getId();
    this.name = category.getName();
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
}
