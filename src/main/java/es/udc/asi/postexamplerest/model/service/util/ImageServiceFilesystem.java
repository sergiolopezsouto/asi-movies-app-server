package es.udc.asi.postexamplerest.model.service.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import es.udc.asi.postexamplerest.config.Properties;
import es.udc.asi.postexamplerest.model.exception.ModelException;
import es.udc.asi.postexamplerest.model.service.dto.ImageDTO;

@Service
public class ImageServiceFilesystem implements ImageService {

  @Autowired
  Properties properties;

  private Path rootLoc;

  @Override
  public String saveImage(MultipartFile file, Long id) throws ModelException {
    if (file.isEmpty()) {
      throw new ModelException("No se ha enviado ningún fichero");
    }
    String filename = StringUtils.cleanPath(file.getOriginalFilename());

    try (InputStream inputStream = file.getInputStream()) {
      Files.copy(inputStream, getRootLoc().resolve(id + getExtension(filename)), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
      throw new ModelException("Problema procesando el fichero");
    }

    return filename;
  }

  @Override
  public ImageDTO getImage(String imagePath, Long id) throws ModelException {
    try {
      InputStream is = new FileInputStream(properties.getImagesPath() + id + getExtension(imagePath));
      byte[] buffer = new byte[1024];
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      int len;
      while ((len = is.read(buffer)) > -1) {
        os.write(buffer, 0, len);
      }
      InputStream imageIs = new ByteArrayInputStream(os.toByteArray());
      os.flush();
      is.close();
      return new ImageDTO(imageIs, getImageMediaType(imagePath), imagePath);
    } catch (IOException e) {
      e.printStackTrace();
      throw new ModelException("Problem while getting the image");
    }
  }

  private Path getRootLoc() {
    if (rootLoc == null)
      this.rootLoc = Paths.get(properties.getImagesPath());
    return rootLoc;
  }

  private String getExtension(String filename) {
    return filename.substring(filename.lastIndexOf("."));
  }

  private String getImageMediaType(String filename) {
    String extension = getExtension(filename);
    switch (extension) {
    case ".jpg":
    case ".jpeg":
      return MediaType.IMAGE_JPEG_VALUE;
    case ".png":
      return MediaType.IMAGE_PNG_VALUE;
    case ".gif":
      return MediaType.IMAGE_GIF_VALUE;
    default:
      return MediaType.IMAGE_JPEG_VALUE;
    }
  }
}