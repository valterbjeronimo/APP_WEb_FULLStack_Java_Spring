package com.tokioFilme.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MigrateFilmStartListener extends JobExecutionListenerSupport {

  private final JdbcTemplate jdbcTemplate;

  public MigrateFilmStartListener(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void beforeJob(JobExecution jobExecution) {
    log.info("Migrate job starting...");

    int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM films WHERE migrate = 0", Integer.class);
    log.info("{} Films to migrate", count);
  }


  /*
          Utilizei o método queryForObject() para obter diretamente o número de filmes a serem migrados.


        Impacto:

        O código está mais claro sobre o que o listener faz antes do início do job.
        A utilização do método queryForObject() simplifica o código e torna-o mais legível.
   */
}
