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
import es.udc.asi.postexamplerest.model.domain.Movie;
import es.udc.asi.postexamplerest.model.domain.User;
import es.udc.asi.postexamplerest.model.exception.ModelException;
import es.udc.asi.postexamplerest.model.exception.NotFoundException;
import es.udc.asi.postexamplerest.model.exception.OperationNotAllowed;
import es.udc.asi.postexamplerest.model.repository.MovieDao;
import es.udc.asi.postexamplerest.model.repository.CategoryDao;
import es.udc.asi.postexamplerest.model.repository.UserDao;
import es.udc.asi.postexamplerest.model.service.dto.ImageDTO;
import es.udc.asi.postexamplerest.model.service.dto.MovieDTO;
import es.udc.asi.postexamplerest.model.service.dto.UserDTOPrivate;
import es.udc.asi.postexamplerest.model.service.util.ImageService;
import es.udc.asi.postexamplerest.security.SecurityUtils;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MovieService {
  @Autowired
  private MovieDao MovieDAO;
  
  @Autowired
  private ImageService imageService;

  @Autowired
  private UserDao userDAO;

  @Autowired
  private CategoryDao categoryDAO;

  @Autowired
  private UserService userService;

  /*
  public List<MovieDTO> findAll(String filter, String category, MovieSortType sort) {
    Stream<Movie> Movies = MovieDAO.findAll(filter, category, sort).stream();
    if (!SecurityUtils.getCurrentUserIsAdmin()) {
      Movies = Movies.filter(e -> e.getAuthor().isActive());
    }
    return Movies.map(Movie -> new MovieDTO(Movie)).collect(Collectors.toList());
  }
  */
  
  public List<MovieDTO> findAll() {
    Stream<Movie> Movies = MovieDAO.findAll().stream();
	//    if (!SecurityUtils.getCurrentUserIsAdmin()) {
	//      Movies = Movies.filter(e -> e.getAuthor().isActive());
	//    }
    return Movies.map(Movie -> new MovieDTO(Movie)).collect(Collectors.toList());
  }


  public MovieDTO findById(Long id) throws NotFoundException {
    Movie Movie = MovieDAO.findById(id);
    if (Movie == null || !SecurityUtils.getCurrentUserIsAdmin() && !Movie.getAuthor().isActive()) {
      throw new NotFoundException(id.toString(), Movie.class);
    }
    return new MovieDTO(Movie);
  }

  // Con estas anotaciones evitamos que usuarios no autorizados accedan a ciertas funcionalidades
//  @PreAuthorize("isAuthenticated()")
//  @Transactional(readOnly = false)
//  public MovieDTO create(MovieDTO Movie) throws OperationNotAllowed {
//    Movie bdMovie = new Movie(Movie.getDescription());
//    UserDTOPrivate currentUser = userService.getCurrentUserWithAuthority();
//
//    // Ahora no se requiere ser ADMIN para establecer el autor de un Movieo 
//    if (Movie.getAuthor() != null) {
//      bdMovie.setAuthor(userDAO.findById(Movie.getAuthor().getId()));
//    } else {
//      bdMovie.setAuthor(userDAO.findById(currentUser.getId()));
//    }
//
//    MovieDAO.create(bdMovie);
//    return new MovieDTO(bdMovie);
//  }
  
  @PreAuthorize("isAuthenticated()")
  @Transactional(readOnly = false)
  public MovieDTO create(MovieDTO Movie) throws OperationNotAllowed {
      Movie bdMovie = new Movie();
      UserDTOPrivate currentUser = userService.getCurrentUserWithAuthority();

      // Ahora no se requiere ser ADMIN para establecer el autor de un Movieo
      if (Movie.getAuthor() != null) {
          bdMovie.setAuthor(userDAO.findById(Movie.getAuthor().getId()));
      } else {
          bdMovie.setAuthor(userDAO.findById(currentUser.getId()));
      }

      // Configurar los demás campos de Movie
      bdMovie.setTitle(Movie.getTitle());
      bdMovie.setReleaseDate(Movie.getReleaseDate());
      bdMovie.setDirector(Movie.getDirector());
      bdMovie.setSynopsis(Movie.getSynopsis());
      bdMovie.setTrailerUrl(Movie.getTrailerUrl());
      bdMovie.setDuration(Movie.getDuration());
      // bdMovie.setImagePath(Movie.getImage());

      // Configurar la categoría si está presente en el DTO
      if (Movie.getCategory() != null) {
          Category category = categoryDAO.findById(Movie.getCategory().getId());
          if (category != null) {
              bdMovie.setCategory(category);
          }
      }

      MovieDAO.create(bdMovie);
      return new MovieDTO(bdMovie);
  }


//  @PreAuthorize("hasAuthority('ADMIN')")
//  @Transactional(readOnly = false, rollbackFor = Exception.class)
//  public MovieDTO update(MovieDTO Movie) throws NotFoundException {
//    Movie bdMovie = MovieDAO.findById(Movie.getId());
//    if (bdMovie == null) {
//      throw new NotFoundException(Movie.getId().toString(), Movie.class);
//    }
//    bdMovie.setTitle(Movie.getTitle());
//    bdMovie.setDescription(Movie.getDescription());
//    bdMovie.setPlace(Movie.getPlace());
//    bdMovie.setDate(Movie.getDate());
//
//    // Configurar la categoría si está presente en el DTO
//    if (Movie.getCategory() != null) {
//      Category category = categoryDAO.findById(Movie.getCategory().getId());
//      if (category != null) {
//        bdMovie.setCategory(category);
//      }
//    } else {
//      bdMovie.setCategory(null);
//    }
//
//    MovieDAO.update(bdMovie);
//    return new MovieDTO(bdMovie);
//  }
  
  
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public MovieDTO update(MovieDTO Movie) throws NotFoundException, OperationNotAllowed {
    Movie bdMovie = MovieDAO.findById(Movie.getId());
    if (bdMovie == null) {
      throw new NotFoundException(Movie.getId().toString(), Movie.class);
    }

    UserDTOPrivate currentUser = userService.getCurrentUserWithAuthority();

    // Verificar si el usuario actual es el propietario del Movieo o tiene el rol de administrador
    if (!currentUser.getAuthority().equals("ADMIN") && bdMovie.getAuthor().getId() != currentUser.getId()) {
      throw new OperationNotAllowed("Only the owner or an administrator can update the Movie");
    }

    bdMovie.setTitle(Movie.getTitle());
    bdMovie.setReleaseDate(Movie.getReleaseDate());
    bdMovie.setDirector(Movie.getDirector());
    bdMovie.setSynopsis(Movie.getSynopsis());
    bdMovie.setTrailerUrl(Movie.getTrailerUrl());
    bdMovie.setDuration(Movie.getDuration());

    // Configurar la categoría si está presente en el DTO
    if (Movie.getCategory() != null) {
      Category category = categoryDAO.findById(Movie.getCategory().getId());
      if (category != null) {
        bdMovie.setCategory(category);
      }
    } else {
      bdMovie.setCategory(null);
    }

    MovieDAO.update(bdMovie);
    return new MovieDTO(bdMovie);
  }





  
  @PreAuthorize("isAuthenticated()")
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void deleteById(Long id) throws NotFoundException, OperationNotAllowed {
    Movie Movie = MovieDAO.findById(id);
    if (Movie == null) {
      throw new NotFoundException(id.toString(), Movie.class);
    }

    UserDTOPrivate currentUser = userService.getCurrentUserWithAuthority();
    if (!currentUser.getId().equals(Movie.getAuthor().getId()) && !currentUser.getAuthority().equals("ADMIN")) {
      throw new OperationNotAllowed("Current user is not the Movie creator or an admin");
    }

    LocalDateTime halfAnHourAgo = LocalDateTime.now().minusMinutes(30);
    if (Movie.getTimestamp().isBefore(halfAnHourAgo) && !currentUser.getAuthority().equals("ADMIN")) {
      throw new OperationNotAllowed("More than half an hour has passed since the Movie creation");
    }

    MovieDAO.deleteById(id);
  }
  
  @PreAuthorize("isAuthenticated()")
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void addFavoriteMovie(Long userId, Long movieId) throws NotFoundException, OperationNotAllowed {
    Long currentUserId = userService.getCurrentUserWithAuthority().getId();
    if (!currentUserId.equals(userId)) {
      throw new OperationNotAllowed("Current user is not the same as the provided userId");
    }

    User currentUser = userDAO.findById(userId);
    Movie movie = MovieDAO.findById(movieId);
    if (movie == null) {
      throw new NotFoundException(movieId.toString(), Movie.class);
    }
    currentUser.addFavoriteMovie(movie);
    userDAO.update(currentUser);
  }

  @PreAuthorize("isAuthenticated()")
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void removeFavoriteMovie(Long userId, Long movieId) throws NotFoundException, OperationNotAllowed {
    Long currentUserId = userService.getCurrentUserWithAuthority().getId();
    if (!currentUserId.equals(userId)) {
      throw new OperationNotAllowed("Current user is not the same as the provided userId");
    }

    User currentUser = userDAO.findById(userId);
    Movie movie = MovieDAO.findById(movieId);
    if (movie == null) {
      throw new NotFoundException(movieId.toString(), Movie.class);
    }
    currentUser.removeFavoriteMovie(movie);
    userDAO.update(currentUser);
  }
  
  
  public boolean isMovieFavorite(Long userId, Long movieId) throws NotFoundException {
	  User user = userDAO.findById(userId);
	  if (user == null) {
	    throw new NotFoundException(userId.toString(), User.class);
	  }
	  Movie movie = MovieDAO.findById(movieId);
	  if (movie == null) {
	    throw new NotFoundException(movieId.toString(), Movie.class);
	  }
	  return user.getFavoriteMovies().contains(movie);
	}
  
  
  public List<MovieDTO> findFavoriteMovies(Long userId) throws NotFoundException {
	    User user = userDAO.findById(userId);
	    if (user == null) {
	        throw new NotFoundException(userId.toString(), User.class);
	    }
	    return user.getFavoriteMovies().stream()
	        .map(MovieDTO::new)
	        .collect(Collectors.toList());
	}






//  @Transactional(readOnly = false, rollbackFor = Exception.class)
//  public void saveMovieImageById(Long id, MultipartFile file) throws InstanceNotFoundException, ModelException {
//
//    Movie Movie = MovieDAO.findById(id);
//    if (Movie == null)
//      throw new NotFoundException(id.toString(), Movie.class);
//
//    String filePath = imageService.saveImage(file, Movie.getId());
//    Movie.setImagePath(filePath);
//    MovieDAO.update(Movie);
//  }

	/*
	 * public ImageDTO getMovieImageById(Long id) throws InstanceNotFoundException,
	 * ModelException { Movie Movie = MovieDAO.findById(id); if (Movie == null ||
	 * !SecurityUtils.getCurrentUserIsAdmin() && !Movie.getAuthor().isActive()) {
	 * throw new NotFoundException(id.toString(), Movie.class); } if
	 * (Movie.getImagePath() == null) { return null; } return
	 * imageService.getImage(Movie.getImagePath(), Movie.getId()); }
	 */
}