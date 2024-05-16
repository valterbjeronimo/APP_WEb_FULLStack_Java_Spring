package com.tokioFilme.batch;

import com.tokioFilme.domain.Film;
import com.tokioFilme.domain.Person;
import com.tokioFilme.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmMapper implements RowMapper<Film> {
  @Override
  public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
    Film film = new Film();
    film.setId(rs.getLong("id"));
    film.setTitle(rs.getString("title"));
    film.setYear(rs.getInt("year"));
    film.setDuration(rs.getInt("duration"));
    film.setSynopsis(rs.getString("synopsis"));
    film.setPoster(rs.getString("poster"));

    User user = new User();
    user.setId(rs.getLong("user_id"));
    film.setUser(user);

    Person director = new Person();
    director.setId(rs.getLong("director_id"));
    film.setFilmDirector(director);

    film.setAvgScore((int) rs.getDouble("avg_score"));

    return film;
  }

  /*
            Explicação:


          Simplifiquei a criação de objetos User e Person usando setters diretos.
          Impacto:

          O código está mais claro sobre como os objetos Film, User e Person são mapeados a partir do ResultSet.
          A simplificação no código torna-o mais legível e fácil de dar manutenção.
   */

}
