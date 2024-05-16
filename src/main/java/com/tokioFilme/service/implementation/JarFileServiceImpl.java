package com.tokioFilme.service.implementation;

import com.tokioFilme.exception.ImageUploadException;
import com.tokioFilme.service.FileService;
import com.tokioFilme.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

@Slf4j
public class JarFileServiceImpl implements FileService {

  private String uploadDirectory;

  public JarFileServiceImpl(String uploadDirectory) {
    this.uploadDirectory = uploadDirectory;
  }

  @Override
  public void saveFile(MultipartFile file, String fileName) throws ImageUploadException {
    log.info("Saving file {} as {}", file, fileName);
    try (InputStream fileStream = file.getInputStream()) {
      log.info("Getting file path");
      Path imagePath = FileUtils.getResourcePathJar(uploadDirectory, fileName);
      log.info("Checking file exists");
      if (Files.exists(imagePath)) {
        log.info("Copying file over old file");
        Files.copy(fileStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
      }
      else {
        log.info("Creating new file");
        Files.write(imagePath, fileStream.readAllBytes(), StandardOpenOption.CREATE);
      }
    } catch (IOException e) {
      log.error("Error saving file {}", fileName, e);
      throw new ImageUploadException("Image Upload Failed", e);
    }
  }

  @Override
  public void deleteFile(String fileName) {
    log.info("Deleting file {}", fileName);
    try {
      log.info("Getting file path");
      Path toDelete = FileUtils.getResourcePathJar(uploadDirectory, fileName);
      log.info("deleting file");
      Files.deleteIfExists(toDelete);
    } catch (IOException e) {
      log.error("Error when deleting image file {}", fileName);
      throw new ImageUploadException("Image Upload Failed", e);
    }
  }


}
