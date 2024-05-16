package com.tokioFilme.config;

import groovy.util.logging.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
@EnableWebMvc
@Slf4j
@Profile("jar")
public class JarApplicationConfig implements WebMvcConfigurer {
  public static String uploadDirectory= System.getProperty("user.home") + File.separator + "/film-fanatic-data";

  public JarApplicationConfig() {
    File upload = new File(uploadDirectory);
    if (!upload.exists()) {
      upload.mkdirs();
    }
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //registry.addResourceHandler("/images/**").addResourceLocations("file:" + uploadDirectory + File.separator);
    registry.addResourceHandler("/images/**", "styles/css/**", "/js/**", "/webjars/**")
      .addResourceLocations("file:" + uploadDirectory + File.separator + "/static/images/", "classpath:/static/images/", "classpath:/static/styles/css/",
      "classpath:/static/js/", "/webjars/");

  }

}
