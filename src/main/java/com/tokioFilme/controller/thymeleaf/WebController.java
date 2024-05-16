package com.tokioFilme.controller.thymeleaf;

import com.tokioFilme.service.FilmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class WebController {

  private final Logger logger = LoggerFactory.getLogger(WebController.class);
  private final FilmService filmService;

  public WebController(FilmService filmService) {
    this.filmService = filmService;
  }

  @RequestMapping(path = "/*", method = {GET, RequestMethod.POST})
  public String index(Model model) {
    logger.info("Connection... serving index.html");
    model.addAttribute("films", filmService.getAllFilms());
    return "index";
  }

  @RequestMapping(path = "/swagger-ui", method = GET)
  public String swaggerUi() {
    return "redirect:/swagger-ui/index.html";
  }

  @GetMapping(path = "/error-test")
  public String errorTest(Model model) throws RuntimeException {
    throw new RuntimeException("This is a test Exception");
  }
}
