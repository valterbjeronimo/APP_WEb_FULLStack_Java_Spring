package com.tokioFilme.service;

import com.tokioFilme.domain.Film;
import com.tokioFilme.domain.FilmSearchCriteriaEnum;

import java.util.Set;

public interface FilmSearch {

  Set<Film> searchFilm(String searchParam, FilmSearchCriteriaEnum searchCriteria);

}
