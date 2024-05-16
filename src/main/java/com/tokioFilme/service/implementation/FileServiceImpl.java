package com.tokioFilme.service.implementation;

import com.tokioFilme.exception.ImageUploadException;
import com.tokioFilme.service.FileService;
import com.tokioFilme.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;

@Slf4j
public class FileServiceImpl implements FileService {

  private String uploadDirectory;

  public FileServiceImpl(String uploadDirectory) {
    this.uploadDirectory = uploadDirectory;
  }

  @Override
  public void saveFile(MultipartFile file, String fileName) throws ImageUploadException {
    log.info("Saving file {} as {}", file, fileName);
    try (InputStream fileStream = file.getInputStream()) {
      log.info("Getting file path");
      Path imagePath = FileUtils.getResourcePath(uploadDirectory, fileName);
      log.info("Checking file exists");
      if (Files.exists(imagePath)) {
        log.info("Copying File");
        Files.copy(fileStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
      }
      else {
        log.info("Creating new file");
        imagePath.toFile()
          .createNewFile();
        Files.write(imagePath, fileStream.readAllBytes());
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
      Path toDelete = FileUtils.getResourcePath(uploadDirectory, fileName);
      Files.deleteIfExists(toDelete);
    } catch (IOException e) {
      log.error("Error when deleting image file {}", fileName);
      throw new ImageUploadException("Image Upload Failed", e);
    }
  }


}
