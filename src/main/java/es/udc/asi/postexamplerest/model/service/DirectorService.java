package es.udc.asi.postexamplerest.model.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.asi.postexamplerest.model.domain.Event;
import es.udc.asi.postexamplerest.model.domain.Director;
import es.udc.asi.postexamplerest.model.exception.NotFoundException;
import es.udc.asi.postexamplerest.model.repository.EventDao;
import es.udc.asi.postexamplerest.model.repository.DirectorDao;
import es.udc.asi.postexamplerest.model.service.dto.DirectorDTO;

@Service
@Transactional(readOnly = true)
public class DirectorService {

  @Autowired
  private DirectorDao directorDAO;

  @Autowired
  private EventDao eventDAO;

  public List<DirectorDTO> findAll() {
    return directorDAO.findAll().stream().sorted(Comparator.comparing(Director::getName)).map(DirectorDTO::new)
        .collect(Collectors.toList());
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public void deleteById(Long id) throws NotFoundException {
    Director theDirector = directorDAO.findById(id);
    if (theDirector == null) {
      throw new NotFoundException(id.toString(), Director.class);
    }
    directorDAO.delete(theDirector);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public DirectorDTO create(DirectorDTO director) {
	Director bdDirector = new Director(director.getName());
    bdDirector.setBirthDate(director.getBirthDate());
    bdDirector.setImageUrl(director.getImageUrl());
	directorDAO.create(bdDirector);
    return new DirectorDTO(bdDirector);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public DirectorDTO update(DirectorDTO director) throws NotFoundException {
	Director bdDirector = directorDAO.findById(director.getId());
    if (bdDirector == null) {
      throw new NotFoundException(director.getId().toString(), Director.class);
    }
    bdDirector.setName(director.getName());
    bdDirector.setBirthDate(director.getBirthDate());
    bdDirector.setImageUrl(director.getImageUrl());
    directorDAO.update(bdDirector);
    return new DirectorDTO(bdDirector);
  }
}
