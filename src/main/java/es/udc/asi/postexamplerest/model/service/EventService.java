package es.udc.asi.postexamplerest.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.management.InstanceNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import es.udc.asi.postexamplerest.model.domain.Category;
import es.udc.asi.postexamplerest.model.domain.Event;
import es.udc.asi.postexamplerest.model.exception.ModelException;
import es.udc.asi.postexamplerest.model.exception.NotFoundException;
import es.udc.asi.postexamplerest.model.exception.OperationNotAllowed;
import es.udc.asi.postexamplerest.model.repository.EventDao;
import es.udc.asi.postexamplerest.model.repository.CategoryDao;
import es.udc.asi.postexamplerest.model.repository.UserDao;
import es.udc.asi.postexamplerest.model.service.dto.ImageDTO;
import es.udc.asi.postexamplerest.model.service.dto.EventDTO;
import es.udc.asi.postexamplerest.model.service.dto.EventSortType;
import es.udc.asi.postexamplerest.model.service.dto.UserDTOPrivate;
import es.udc.asi.postexamplerest.model.service.util.ImageService;
import es.udc.asi.postexamplerest.security.SecurityUtils;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EventService {
  @Autowired
  private EventDao eventDAO;
  
  @Autowired
  private ImageService imageService;

  @Autowired
  private UserDao userDAO;

  @Autowired
  private CategoryDao categoryDAO;

  @Autowired
  private UserService userService;

  /*
  public List<EventDTO> findAll(String filter, String category, EventSortType sort) {
    Stream<Event> events = eventDAO.findAll(filter, category, sort).stream();
    if (!SecurityUtils.getCurrentUserIsAdmin()) {
      events = events.filter(e -> e.getAuthor().isActive());
    }
    return events.map(event -> new EventDTO(event)).collect(Collectors.toList());
  }
  */
  
  public List<EventDTO> findAll() {
    Stream<Event> events = eventDAO.findAll().stream();
    if (!SecurityUtils.getCurrentUserIsAdmin()) {
      events = events.filter(e -> e.getAuthor().isActive());
    }
    return events.map(event -> new EventDTO(event)).collect(Collectors.toList());
  }


  public EventDTO findById(Long id) throws NotFoundException {
    Event event = eventDAO.findById(id);
    if (event == null || !SecurityUtils.getCurrentUserIsAdmin() && !event.getAuthor().isActive()) {
      throw new NotFoundException(id.toString(), Event.class);
    }
    return new EventDTO(event);
  }

  // Con estas anotaciones evitamos que usuarios no autorizados accedan a ciertas funcionalidades
//  @PreAuthorize("isAuthenticated()")
//  @Transactional(readOnly = false)
//  public EventDTO create(EventDTO event) throws OperationNotAllowed {
//    Event bdEvent = new Event(event.getDescription());
//    UserDTOPrivate currentUser = userService.getCurrentUserWithAuthority();
//
//    // Ahora no se requiere ser ADMIN para establecer el autor de un evento 
//    if (event.getAuthor() != null) {
//      bdEvent.setAuthor(userDAO.findById(event.getAuthor().getId()));
//    } else {
//      bdEvent.setAuthor(userDAO.findById(currentUser.getId()));
//    }
//
//    eventDAO.create(bdEvent);
//    return new EventDTO(bdEvent);
//  }
  
  @PreAuthorize("isAuthenticated()")
  @Transactional(readOnly = false)
  public EventDTO create(EventDTO event) throws OperationNotAllowed {
      Event bdEvent = new Event();
      UserDTOPrivate currentUser = userService.getCurrentUserWithAuthority();

      // Ahora no se requiere ser ADMIN para establecer el autor de un evento
      if (event.getAuthor() != null) {
          bdEvent.setAuthor(userDAO.findById(event.getAuthor().getId()));
      } else {
          bdEvent.setAuthor(userDAO.findById(currentUser.getId()));
      }

      // Configurar los demás campos de Event
      bdEvent.setTitle(event.getTitle());
      bdEvent.setDate(event.getDate());
      bdEvent.setPlace(event.getPlace());
      bdEvent.setDescription(event.getDescription());
//      bdEvent.setImagePath(event.getImage());

      // Configurar la categoría si está presente en el DTO
      if (event.getCategory() != null) {
          Category category = categoryDAO.findById(event.getCategory().getId());
          if (category != null) {
              bdEvent.setCategory(category);
          }
      }

      eventDAO.create(bdEvent);
      return new EventDTO(bdEvent);
  }


//  @PreAuthorize("hasAuthority('ADMIN')")
//  @Transactional(readOnly = false, rollbackFor = Exception.class)
//  public EventDTO update(EventDTO event) throws NotFoundException {
//    Event bdEvent = eventDAO.findById(event.getId());
//    if (bdEvent == null) {
//      throw new NotFoundException(event.getId().toString(), Event.class);
//    }
//    bdEvent.setTitle(event.getTitle());
//    bdEvent.setDescription(event.getDescription());
//    bdEvent.setPlace(event.getPlace());
//    bdEvent.setDate(event.getDate());
//
//    // Configurar la categoría si está presente en el DTO
//    if (event.getCategory() != null) {
//      Category category = categoryDAO.findById(event.getCategory().getId());
//      if (category != null) {
//        bdEvent.setCategory(category);
//      }
//    } else {
//      bdEvent.setCategory(null);
//    }
//
//    eventDAO.update(bdEvent);
//    return new EventDTO(bdEvent);
//  }
  
  
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public EventDTO update(EventDTO event) throws NotFoundException, OperationNotAllowed {
    Event bdEvent = eventDAO.findById(event.getId());
    if (bdEvent == null) {
      throw new NotFoundException(event.getId().toString(), Event.class);
    }

    UserDTOPrivate currentUser = userService.getCurrentUserWithAuthority();

    // Verificar si el usuario actual es el propietario del evento o tiene el rol de administrador
    if (!currentUser.getAuthority().equals("ADMIN") && bdEvent.getAuthor().getId() != currentUser.getId()) {
      throw new OperationNotAllowed("Only the owner or an administrator can update the event");
    }

    bdEvent.setTitle(event.getTitle());
    bdEvent.setDescription(event.getDescription());
    bdEvent.setPlace(event.getPlace());
    bdEvent.setDate(event.getDate());

    // Configurar la categoría si está presente en el DTO
    if (event.getCategory() != null) {
      Category category = categoryDAO.findById(event.getCategory().getId());
      if (category != null) {
        bdEvent.setCategory(category);
      }
    } else {
      bdEvent.setCategory(null);
    }

    eventDAO.update(bdEvent);
    return new EventDTO(bdEvent);
  }





  
  @PreAuthorize("isAuthenticated()")
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void deleteById(Long id) throws NotFoundException, OperationNotAllowed {
    Event event = eventDAO.findById(id);
    if (event == null) {
      throw new NotFoundException(id.toString(), Event.class);
    }

    UserDTOPrivate currentUser = userService.getCurrentUserWithAuthority();
    if (!currentUser.getId().equals(event.getAuthor().getId()) && !currentUser.getAuthority().equals("ADMIN")) {
      throw new OperationNotAllowed("Current user is not the event creator or an admin");
    }

    LocalDateTime halfAnHourAgo = LocalDateTime.now().minusMinutes(30);
    if (event.getTimestamp().isBefore(halfAnHourAgo) && !currentUser.getAuthority().equals("ADMIN")) {
      throw new OperationNotAllowed("More than half an hour has passed since the event creation");
    }

    eventDAO.deleteById(id);
  }



//  @Transactional(readOnly = false, rollbackFor = Exception.class)
//  public void saveEventImageById(Long id, MultipartFile file) throws InstanceNotFoundException, ModelException {
//
//    Event event = eventDAO.findById(id);
//    if (event == null)
//      throw new NotFoundException(id.toString(), Event.class);
//
//    String filePath = imageService.saveImage(file, event.getId());
//    event.setImagePath(filePath);
//    eventDAO.update(event);
//  }

	/*
	 * public ImageDTO getEventImageById(Long id) throws InstanceNotFoundException,
	 * ModelException { Event event = eventDAO.findById(id); if (event == null ||
	 * !SecurityUtils.getCurrentUserIsAdmin() && !event.getAuthor().isActive()) {
	 * throw new NotFoundException(id.toString(), Event.class); } if
	 * (event.getImagePath() == null) { return null; } return
	 * imageService.getImage(event.getImagePath(), event.getId()); }
	 */
}