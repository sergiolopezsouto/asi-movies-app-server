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

import es.udc.asi.postexamplerest.model.domain.Event;
import es.udc.asi.postexamplerest.model.exception.ModelException;
import es.udc.asi.postexamplerest.model.exception.NotFoundException;
import es.udc.asi.postexamplerest.model.exception.OperationNotAllowed;
import es.udc.asi.postexamplerest.model.service.EventService;
import es.udc.asi.postexamplerest.model.service.dto.ImageDTO;
import es.udc.asi.postexamplerest.model.service.dto.EventDTO;
import es.udc.asi.postexamplerest.model.service.dto.EventSortType;
import es.udc.asi.postexamplerest.web.exceptions.IdAndBodyNotMatchingOnUpdateException;
import es.udc.asi.postexamplerest.web.exceptions.RequestBodyNotValidException;

@RestController
@RequestMapping("/api/events")
public class EventResource {

  @Autowired
  private EventService eventService;

  /*
  @GetMapping
  public List<EventDTO> findAll(@RequestParam(required = false) String query, @RequestParam(required = false) String category,
      @RequestParam(required = false) EventSortType sort) {
    return eventService.findAll(query, category, sort);
  }
  */
  
  @GetMapping
  public List<EventDTO> findAll() {
    return eventService.findAll();
  }

  @GetMapping("/{id}")
  public EventDTO findOne(@PathVariable Long id) throws NotFoundException {
    return eventService.findById(id);
  }

	/*
	 * @GetMapping("/{id}/image")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public void getMovieImageById(@PathVariable
	 * Long id, HttpServletResponse response) throws InstanceNotFoundException,
	 * ModelException, IOException { ImageDTO image =
	 * eventService.getEventImageById(id);
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
	 * eventService.saveEventImageById(id, file); }
	 */

  @PostMapping
  public EventDTO create(@RequestBody @Valid EventDTO event, Errors errors)
      throws RequestBodyNotValidException, OperationNotAllowed {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }

    return eventService.create(event);
  }
  
  // lo tuve que comentar caundo cambié category por categoryDTO en eventDTO
  @PutMapping("/{id}")
  public EventDTO update(@PathVariable Long id, @RequestBody @Valid EventDTO event, Errors errors)
      throws IdAndBodyNotMatchingOnUpdateException, RequestBodyNotValidException, NotFoundException, OperationNotAllowed {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }

    if (id != event.getId()) {
      throw new IdAndBodyNotMatchingOnUpdateException(Event.class);
    }
    return eventService.update(event);
  }

  
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) throws NotFoundException, OperationNotAllowed {
    eventService.deleteById(id);
  }

}