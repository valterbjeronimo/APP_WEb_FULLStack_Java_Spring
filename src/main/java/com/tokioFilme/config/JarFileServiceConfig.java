package com.tokioFilme.config;

import com.tokioFilme.service.FileService;
import com.tokioFilme.service.implementation.JarFileServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("jar")
public class JarFileServiceConfig {

  @Value("${images.upload.directory.user}")
  private String userImageUploadDirectory;
  @Value("${images.upload.directory.film}")
  private String filmPosterUploadDirectory;

  @Bean("user-image-file-service")
  public FileService userImageFileService() {
    return new JarFileServiceImpl(userImageUploadDirectory);
  }

  @Bean("film-poster-file-service")
  public FileService filmPosterFileService() {
    return new JarFileServiceImpl(filmPosterUploadDirectory);
  }

}
