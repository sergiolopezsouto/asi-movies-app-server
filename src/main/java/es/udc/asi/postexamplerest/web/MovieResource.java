package es.udc.asi.postexamplerest.web;

import java.io.IOException;
import java.util.List;

import javax.management.InstanceNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.udc.asi.postexamplerest.model.domain.Movie;
import es.udc.asi.postexamplerest.model.exception.ModelException;
import es.udc.asi.postexamplerest.model.exception.NotFoundException;
import es.udc.asi.postexamplerest.model.exception.OperationNotAllowed;
import es.udc.asi.postexamplerest.model.service.MovieService;
import es.udc.asi.postexamplerest.model.service.dto.ImageDTO;
import es.udc.asi.postexamplerest.model.service.dto.MovieDTO;
import es.udc.asi.postexamplerest.web.exceptions.IdAndBodyNotMatchingOnUpdateException;
import es.udc.asi.postexamplerest.web.exceptions.RequestBodyNotValidException;

@RestController
@RequestMapping("/api/movies")
public class MovieResource {

  @Autowired
  private MovieService MovieService;

  /*
  @GetMapping
  public List<MovieDTO> findAll(@RequestParam(required = false) String query, @RequestParam(required = false) String category,
      @RequestParam(required = false) MovieSortType sort) {
    return MovieService.findAll(query, category, sort);
  }
  */
  
  @GetMapping
  public List<MovieDTO> findAll() {
    return MovieService.findAll();
  }

  @GetMapping("/{id}")
  public MovieDTO findOne(@PathVariable Long id) throws NotFoundException {
    return MovieService.findById(id);
  }

	/*
	 * @GetMapping("/{id}/image")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public void getMovieImageById(@PathVariable
	 * Long id, HttpServletResponse response) throws InstanceNotFoundException,
	 * ModelException, IOException { ImageDTO image =
	 * MovieService.getMovieImageById(id);
	 * 
	 * if (image == null) { response.sendError(404); // esto se podría hacer también
	 * con una excepción return; }
	 * 
	 * try { response.setContentType(image.getMediaType());
	 * response.setHeader("Content-disposition", "filename=" + image.getFilename());
	 * IOUtils.copy(image.getInputStream(), response.getOutputStream()); } catch
	 * (IOException e) { e.printStackTrace(); } }
	 */

	/*
	 * @PostMapping("/{id}/image")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public void saveMovieImageById(@PathVariable
	 * Long id, @RequestParam MultipartFile file, HttpServletResponse response)
	 * throws InstanceNotFoundException, ModelException {
	 * 
	 * MovieService.saveMovieImageById(id, file); }
	 */

  @PostMapping
  public MovieDTO create(@RequestBody @Valid MovieDTO Movie, Errors errors)
      throws RequestBodyNotValidException, OperationNotAllowed {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }

    return MovieService.create(Movie);
  }
  
  // lo tuve que comentar caundo cambié category por categoryDTO en MovieDTO
  @PutMapping("/{id}")
  public MovieDTO update(@PathVariable Long id, @RequestBody @Valid MovieDTO Movie, Errors errors)
      throws IdAndBodyNotMatchingOnUpdateException, RequestBodyNotValidException, NotFoundException, OperationNotAllowed {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }

    if (id != Movie.getId()) {
      throw new IdAndBodyNotMatchingOnUpdateException(Movie.class);
    }
    return MovieService.update(Movie);
  }

  
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) throws NotFoundException, OperationNotAllowed {
    MovieService.deleteById(id);
  }

}