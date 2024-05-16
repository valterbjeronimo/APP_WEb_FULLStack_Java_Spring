package com.tokioFilme.batch;

import com.tokioFilme.domain.Film;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class MigrateJobConfig {

  @Bean
  public ItemReader<Film> reader(DataSource dataSource) {
    return new JdbcCursorItemReaderBuilder<Film>()
            .name("filmReader")
            .dataSource(dataSource)
            .sql("SELECT * FROM films")
            .rowMapper(new FilmMapper())
            .build();
  }

  @Bean
  public ItemProcessor<Film, Film> processor() {
    return new FilmItemProcessor();
  }

  @Bean
  public ItemWriter<Film> writer() {
    return new FlatFileItemWriterBuilder<Film>()
            .name("filmWriter")
            .resource(new ClassPathResource("films.csv"))
            .lineAggregator(new FilmLineAggregator())
            .build();
  }

  @Bean
  public Step step(StepBuilderFactory stepBuilderFactory, ItemReader<Film> reader,
                   ItemProcessor<Film, Film> processor, ItemWriter<Film> writer,
                   MigrateFilmWriteListener writeListener) {
    return stepBuilderFactory.get("step")
            .<Film, Film>chunk(10)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .listener(writeListener)
            .build();
  }

  @Bean
  public Job migrateFilmJob(JobBuilderFactory jobBuilderFactory, Step step,
                            MigrateFilmStartListener startListener) {
    return jobBuilderFactory.get("migrateFilmJob")
            .incrementer(new RunIdIncrementer())
            .listener(startListener)
            .flow(step)
            .end()
            .build();
  }

       /*
                     Simplifiquei a configuração dos beans utilizando métodos fluentes fornecidos pelo Spring Batch.


              Impacto:

              O código está mais claro sobre a configuração do job de migração de filmes.
              A utilização de métodos fluentes torna a configuração mais legível e fácil de entender.
        */

}
