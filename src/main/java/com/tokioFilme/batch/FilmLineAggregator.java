package com.tokioFilme.batch;

import com.tokioFilme.domain.Film;
import org.springframework.batch.item.file.transform.LineAggregator;

public class FilmLineAggregator implements LineAggregator<Film> {

  private static final String CSV_DELIMITER = ",";

  @Override
  public String aggregate(Film film) {

      // Agrega os campos do filme separados por vírgula
      return String.join(CSV_DELIMITER,
              String.valueOf(film.getId()),
              film.getTitle(),
              String.valueOf(film.getYear()),
              String.valueOf(film.getDuration()),
              film.getSynopsis(),
              film.getPoster(),
              String.valueOf(film.getFilmDirector().getId()),
              String.valueOf(film.getAvgScore()),
              String.valueOf(film.getUser().getId()));
      }
  }

  /*
          Explicação:


        Utilizei o método String.join() para agregar os campos do filme em uma linha formatada em CSV.


        Impacto:

        O código está mais claro sobre como os campos do filme são agregados em uma linha de texto.
        A utilização do método String.join() simplifica o código e torna-o mais legível.
   */
