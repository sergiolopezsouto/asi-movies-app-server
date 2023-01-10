package es.udc.asi.postexamplerest.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.udc.asi.postexamplerest.model.domain.Category;
import es.udc.asi.postexamplerest.model.exception.NotFoundException;
import es.udc.asi.postexamplerest.model.service.CategoryService;
import es.udc.asi.postexamplerest.model.service.dto.CategoryDTO;
import es.udc.asi.postexamplerest.web.exceptions.IdAndBodyNotMatchingOnUpdateException;
import es.udc.asi.postexamplerest.web.exceptions.RequestBodyNotValidException;

@RestController
@RequestMapping("/api/categories")
public class CategoryResource {

  @Autowired
  private CategoryService categoryService;

  @GetMapping
  public List<CategoryDTO> findAll() {
    return categoryService.findAll();
  }

  @PostMapping
  public CategoryDTO create(@RequestBody @Valid CategoryDTO category, Errors errors) throws RequestBodyNotValidException {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }

    return categoryService.create(category);
  }

  @PutMapping("/{id}")
  public CategoryDTO update(@PathVariable Long id, @RequestBody @Valid CategoryDTO category, Errors errors)
      throws RequestBodyNotValidException, IdAndBodyNotMatchingOnUpdateException, NotFoundException {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }

    if (id != category.getId()) {
      throw new IdAndBodyNotMatchingOnUpdateException(Category.class);
    }

    return categoryService.update(category);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) throws NotFoundException {
    categoryService.deleteById(id);
  }
}
