package com.tokioFilme.service.filmsearch.strategy.implementation;

import com.tokioFilme.domain.Film;

import java.util.Set;

public interface FilmSearchStrategy {

  Set<Film> searchFilm(String searchParam);

}
