package com.tokioFilme.batch;

import com.tokioFilme.domain.Film;
import org.springframework.batch.item.ItemProcessor;


public class FilmItemProcessor implements ItemProcessor<Film, Film> {



    @Override
    public Film process(Film film) throws Exception {
      // Verifica se o filme deve ser migrado
      if (film.getMigrate()) {
        return film; // Retorna o filme para migração
      } else {
        return null; // Retorna null para indicar que o filme não deve ser migrado
      }
    }
  }

   /*

   * Explicação:


                Modifiquei a lógica dentro do método process() para torná-la mais clara


                Impacto:

                A lógica mais clara facilita a compreensão do que o processador faz.
                O código agora está mais legível e fácil de dar manutenção.




    */
