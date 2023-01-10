package es.udc.asi.postexamplerest.model.service.dto;

import java.io.InputStream;

public class ImageDTO {
  private InputStream inputStream;
  private String mediaType;
  private String filename;

  public ImageDTO(InputStream inputStream, String mediaType, String filename) {
    this.inputStream = inputStream;
    this.mediaType = mediaType;
    this.filename = filename;
  }

  public InputStream getInputStream() {
    return inputStream;
  }

  public String getMediaType() {
    return mediaType;
  }

  public String getFilename() {
    return filename;
  }
}