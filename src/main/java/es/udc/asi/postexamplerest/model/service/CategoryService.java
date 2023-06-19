package es.udc.asi.postexamplerest.model.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.asi.postexamplerest.model.domain.Event;
import es.udc.asi.postexamplerest.model.domain.Category;
import es.udc.asi.postexamplerest.model.exception.NotFoundException;
import es.udc.asi.postexamplerest.model.repository.EventDao;
import es.udc.asi.postexamplerest.model.repository.CategoryDao;
import es.udc.asi.postexamplerest.model.service.dto.CategoryDTO;

@Service
@Transactional(readOnly = true)
public class CategoryService {

  @Autowired
  private CategoryDao categoryDAO;

  @Autowired
  private EventDao eventDAO;

  public List<CategoryDTO> findAll() {
    return categoryDAO.findAll().stream().sorted(Comparator.comparing(Category::getName)).map(CategoryDTO::new)
        .collect(Collectors.toList());
  }
  
  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public void deleteById(Long id) throws NotFoundException {
    List<Event> events = eventDAO.findAllByCategory(id);
    Category theCategory = categoryDAO.findById(id);
    if (theCategory == null) {
      throw new NotFoundException(id.toString(), Category.class);
    }
    events.forEach(event -> {
      event.setCategory(null);
      eventDAO.update(event);
    });
    categoryDAO.delete(theCategory);
  }


  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public CategoryDTO create(CategoryDTO category) {
	Category bdCategory = new Category(category.getName());
	categoryDAO.create(bdCategory);
    return new CategoryDTO(bdCategory);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public CategoryDTO update(CategoryDTO category) throws NotFoundException {
	Category bdCategory = categoryDAO.findById(category.getId());
    if (bdCategory == null) {
      throw new NotFoundException(category.getId().toString(), Category.class);
    }
    bdCategory.setName(category.getName());
    categoryDAO.update(bdCategory);
    return new CategoryDTO(bdCategory);
  }
}