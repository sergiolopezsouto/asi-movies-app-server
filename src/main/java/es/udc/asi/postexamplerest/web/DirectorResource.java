package es.udc.asi.postexamplerest.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import es.udc.asi.postexamplerest.model.domain.Director;
import es.udc.asi.postexamplerest.model.exception.NotFoundException;
import es.udc.asi.postexamplerest.model.service.DirectorService;
import es.udc.asi.postexamplerest.model.service.dto.DirectorDTO;
import es.udc.asi.postexamplerest.web.exceptions.IdAndBodyNotMatchingOnUpdateException;
import es.udc.asi.postexamplerest.web.exceptions.RequestBodyNotValidException;

@RestController
@RequestMapping("/api/directors")
public class DirectorResource {

  @Autowired
  private DirectorService directorService;

  @GetMapping
  public List<DirectorDTO> findAll() {
    return directorService.findAll();
  }

  @PostMapping
  public DirectorDTO create(@RequestBody @Valid DirectorDTO director, Errors errors) throws RequestBodyNotValidException {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }

    return directorService.create(director);
  }

  @PutMapping("/{id}")
  public DirectorDTO update(@PathVariable Long id, @RequestBody @Valid DirectorDTO director, Errors errors)
      throws RequestBodyNotValidException, IdAndBodyNotMatchingOnUpdateException, NotFoundException {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }

    if (id != director.getId()) {
      throw new IdAndBodyNotMatchingOnUpdateException(Director.class);
    }

    return directorService.update(director);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) throws NotFoundException {
    directorService.deleteById(id);
  }
}
