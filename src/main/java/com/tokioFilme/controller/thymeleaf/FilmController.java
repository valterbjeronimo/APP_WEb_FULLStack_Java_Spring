package com.tokioFilme.controller.thymeleaf;

import com.tokioFilme.domain.Film;
import com.tokioFilme.domain.Score;
import com.tokioFilme.domain.User;
import com.tokioFilme.domain.FilmSearchCriteriaEnum;
import com.tokioFilme.service.FilmService;
import com.tokioFilme.service.PersonService;
import com.tokioFilme.service.UserService;
import com.tokioFilme.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.tokioFilme.domain.PersonTypeEnum.*;

@Controller
@Slf4j
public class FilmController {

  private final FilmService filmService;
  private final UserService userService;
  private final PersonService personService;

  public FilmController(FilmService filmService, UserService userService, PersonService personService) {
    this.filmService = filmService;
    this.userService = userService;
    this.personService = personService;
  }

  @RequestMapping(path = "films/search", method=RequestMethod.GET)
  public String searchFilm(@RequestParam(name = "query") String query,
                           @RequestParam(name = "criteria") String criteria,
                           Model model) {
    Set<Film> results = filmService.searchFilms(query, criteria);
    model.addAttribute("films", results);
    return "searched-film";
  }

  @RequestMapping(path = "search", method = RequestMethod.GET)
  public String search(Model model) {
    model.addAttribute("criteria", FilmSearchCriteriaEnum.values());
    return "search-film";
  }

  @RequestMapping(path = "films/{filmUri}", method = RequestMethod.GET)
  public String filmInfo(@PathVariable("filmUri") String filmUri,
                         Model model,
                         Authentication authentication) {
    Film film = filmService.getFilmByUri(filmUri);
    model.addAttribute("film", film);

    if (authentication != null) {
      User authenticatedUser = (User) authentication.getPrincipal();
      Optional<Score> userScore = film.getScores()
        .stream()
        .filter(scr -> Objects.equals(scr.getUser().getId(), authenticatedUser.getId()))
        .findFirst();
      if (userScore.isPresent()) {
        model.addAttribute("score", userScore.get());
      } else {
        model.addAttribute("newScore", new Score());
      }
    }
    return "film";
  }

  @RequestMapping(path = "films/{filmUri}/score", method = RequestMethod.POST)
  public String filmInfo(@PathVariable("filmUri") String filmUri,
                         @ModelAttribute("newScore") @Valid Score score,
                         BindingResult result,
                         Authentication auth) {
    if (result.hasErrors()) {
      return "film";
    }
    score.setUser((User) auth.getPrincipal());
    filmService.addScore(filmUri, score);

    return "redirect:/films/" + filmUri;
  }

  @RequestMapping(path = "films/add", method = RequestMethod.GET)
  public String createFilm(Model model) {
    Film newFilm = new Film();

    model.addAttribute("film", newFilm);
    model.addAttribute("directors", personService.getPeopleByType(DIRECTOR));
    model.addAttribute("actors", personService.getPeopleByType(ACTOR));
    model.addAttribute("screenwriters", personService.getPeopleByType(SCREEN_WRITER));
    model.addAttribute("cinematographers", personService.getPeopleByType(CINEMATOGRAPHER));
    model.addAttribute("composers", personService.getPeopleByType(COMPOSER));
    return "new-film";
  }

  @RequestMapping(path = "films/add", method = RequestMethod.POST)
  public String createFilm(@RequestParam("posterImage") MultipartFile posterImage,
                           @ModelAttribute("film") @Valid Film film,
                           BindingResult result,
                           Model model,
                           Principal principal)
  {
    if (result.hasErrors()) {
      return "new-film";
    }
    else {
      film.setUser(userService.getUser(principal.getName()));
      Film createdFilm = filmService.addFilm(film);
      if (!posterImage.isEmpty()) {
        createdFilm = filmService.savePoster(film, posterImage);
      }
      model.addAttribute("film", createdFilm);
      return "redirect:/films/" + StringUtil.getFilmUri(createdFilm.getTitle(), createdFilm.getYear());
    }
  }
}
