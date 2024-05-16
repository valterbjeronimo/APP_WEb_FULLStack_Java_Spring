package com.tokioFilme.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

  void saveFile(MultipartFile file, String fileName);


  void deleteFile(String fileName);
}
