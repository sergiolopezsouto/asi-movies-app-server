package es.udc.asi.postexamplerest.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.udc.asi.postexamplerest.model.exception.NotFoundException;
import es.udc.asi.postexamplerest.model.exception.OperationNotAllowed;
import es.udc.asi.postexamplerest.model.service.MovieService;
import es.udc.asi.postexamplerest.model.service.UserService;
import es.udc.asi.postexamplerest.model.service.dto.MovieDTO;
import es.udc.asi.postexamplerest.model.service.dto.UserDTOPublic;
import es.udc.asi.postexamplerest.model.service.dto.UserDTOWithMovies;

@RestController
@RequestMapping("/api/users")
public class UserResource {

  @Autowired
  private UserService userService;
  
  @Autowired
  private MovieService movieService;  

  @GetMapping
  public List<UserDTOPublic> findAll() {
    return userService.findAll();
  }

  @GetMapping("/{id}")
  public UserDTOWithMovies findOne(@PathVariable Long id) throws NotFoundException {
    return userService.findById(id);
  }

  @PutMapping("/{id}/activate")
  public UserDTOPublic activate(@PathVariable Long id) throws NotFoundException, OperationNotAllowed {
    return userService.updateActive(id, true);
  }

  @PutMapping("/{id}/deactivate")
  public UserDTOPublic deactivate(@PathVariable Long id) throws NotFoundException, OperationNotAllowed {
    return userService.updateActive(id, false);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) throws NotFoundException, OperationNotAllowed {
    userService.deleteById(id);
  }
  
  @PostMapping("/{userId}/favorites/{movieId}")
  public ResponseEntity<?> addFavoriteMovie(@PathVariable Long userId, @PathVariable Long movieId) throws NotFoundException, OperationNotAllowed {
    movieService.addFavoriteMovie(userId, movieId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{userId}/favorites/{movieId}")
  public ResponseEntity<?> removeFavoriteMovie(@PathVariable Long userId, @PathVariable Long movieId) throws NotFoundException, OperationNotAllowed {
    movieService.removeFavoriteMovie(userId, movieId);
    return ResponseEntity.ok().build();
  }
  
  @GetMapping("/{userId}/favorites/{movieId}")
  public boolean isMovieFavorite(@PathVariable Long userId, @PathVariable Long movieId) throws NotFoundException {
    return movieService.isMovieFavorite(userId, movieId);
  }
  
  @GetMapping("/{userId}/favorites")
  public List<MovieDTO> getFavoriteMovies(@PathVariable Long userId) throws NotFoundException {
    return movieService.findFavoriteMovies(userId);
  }

}
