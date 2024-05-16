package com.tokioFilme.service.filmsearch.implementation;

import com.tokioFilme.service.FileService;
import com.tokioFilme.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@Service
@Slf4j
public class FilmPosterImageFileService implements FileService {

  @Value("${images.upload.directory.film}")
  private String filmPosterUploadDirectory;

  @Override
  public void saveFile(MultipartFile file, String fileName) {
      try (InputStream inputStream = file.getInputStream()) {
        Path path = FileUtils.getResourcePath(filmPosterUploadDirectory, fileName);

      } catch (IOException e) {

      }
  }

  @Override
  public void deleteFile(String fileName) {

  }
}
