package com.tokioFilme.service.implementation;

import com.tokioFilme.domain.Film;
import com.tokioFilme.domain.Score;
import com.tokioFilme.domain.FilmSearchCriteriaEnum;
import com.tokioFilme.exception.FilmNotFoundException;
import com.tokioFilme.repository.FilmRepository;
import com.tokioFilme.service.FileService;
import com.tokioFilme.service.FilmSearch;
import com.tokioFilme.service.FilmService;
import com.tokioFilme.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Set;

@Slf4j
@Service
public class FilmServiceImpl implements FilmService {

  private final FilmRepository filmRepo;
  private final FilmSearch filmSearch;
  private final FileService fileService;

  public FilmServiceImpl(FilmRepository filmRepo,
                        FilmSearch filmSearch, @Qualifier("film-poster-file-service")
                        FileService fileService) {
    this.filmRepo = filmRepo;
    this.filmSearch = filmSearch;
    this.fileService = fileService;
  }

  @Override
  @Transactional
  public Film addFilm(Film film) {
    film.setUri(StringUtil.getFilmUri(film.getTitle(), film.getYear()));
    log.info("Saving Film {}", film);
    return filmRepo.save(film);
  }

  @Override
  @Transactional
  public Film savePoster(Film film, MultipartFile posterImage) {
    Film toUpdate = filmRepo.findByTitleIgnoreCase(film.getTitle())
      .orElseThrow(() -> new FilmNotFoundException());

    String fileName = StringUtil.getFilmPosterFilename(film.getTitle(), film.getYear(), posterImage.getContentType());
    fileService.saveFile(posterImage, fileName);

    toUpdate.setPoster(fileName);
    return filmRepo.save(toUpdate);
  }

  @Override
  @Transactional()
  public Film getFilmByUri(String filmUri) {
    return filmRepo.findByUri(filmUri)
      .orElseThrow(() -> new FilmNotFoundException());
  }

  @Override
  @Transactional
  public Film addScore(String filmUri, Score score) {
    Film toUpdate = getFilmByUri(filmUri);
    toUpdate.addScore(score);
    return filmRepo.save(toUpdate);
  }

  @Override
  public Set<Film> searchByTitle(String title) {
    log.info("Searching for Film with title like {}", title);
    return filmRepo.findByTitleContainsIgnoreCase(title);
  }

  @Override
  public Set<Film> getAllFilms() {
    return filmRepo.findAll();
  }

  @Override
  public Set<Film> searchFilms(String searchParam, String searchCriteria) {
    return filmSearch.searchFilm(searchParam, FilmSearchCriteriaEnum.fromString(searchCriteria));
  }

  @Override
  public Film findByTitleExact(String title) {
    return filmRepo.findByTitleIgnoreCase(title).orElseThrow(FilmNotFoundException::new);
  }
}
