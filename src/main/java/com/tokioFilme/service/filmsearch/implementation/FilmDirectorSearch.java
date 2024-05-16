package com.tokioFilme.service.filmsearch.implementation;

import com.tokioFilme.domain.Film;
import com.tokioFilme.repository.FilmRepository;
import com.tokioFilme.service.filmsearch.strategy.implementation.FilmSearchStrategy;

import java.util.Set;

public class FilmDirectorSearch implements FilmSearchStrategy {

  private final FilmRepository repository;

  public FilmDirectorSearch(FilmRepository repository) {
    this.repository = repository;
  }

  @Override
  public Set<Film> searchFilm(String searchParam) {
    return repository.findByFilmDirectorNameContainsOrFilmDirectorSurnameContainsAllIgnoreCase(searchParam, searchParam);
  }
}
