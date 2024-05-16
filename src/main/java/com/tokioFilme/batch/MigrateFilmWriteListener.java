package com.tokioFilme.batch;

import com.tokioFilme.domain.Film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
public class MigrateFilmWriteListener implements ItemWriteListener<Film> {

    private final JdbcTemplate jdbcTemplate;

    public MigrateFilmWriteListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void beforeWrite(List<? extends Film> list) {
        // Não é necessário implementar neste caso
    }

    @Override
    public void afterWrite(List<? extends Film> migratedFilms) {
        log.info("Updating migrated films in db");

        String updateQuery = "UPDATE films SET migrate = ?, date_migrate = ? WHERE id = ?";
        jdbcTemplate.batchUpdate(updateQuery, migratedFilms, migratedFilms.size(),
                (ps, film) -> {
                    ps.setBoolean(1, true);
                    ps.setDate(2, Date.valueOf(LocalDate.now()));
                    ps.setLong(3, film.getId());
                });
    }

    @Override
    public void onWriteError(Exception e, List<? extends Film> list) {
        log.error("Error when writing Films: {}", e.getMessage(), e);
    }


    /*
                Utilizei o método batchUpdate() do JdbcTemplate para atualizar os filmes migrados.

            Impacto:

            O código está mais claro sobre como os filmes são atualizados no banco de dados após a migração.
            A utilização do método batchUpdate() simplifica o código e melhora a eficiência da atualização.

     */
}
