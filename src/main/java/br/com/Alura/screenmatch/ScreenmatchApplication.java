package br.com.Alura.screenmatch;

import br.com.Alura.screenmatch.Main.Main;
import br.com.Alura.screenmatch.models.DadosEpisodio;
import br.com.Alura.screenmatch.models.DadosSerie;
import br.com.Alura.screenmatch.models.DadosTemporada;
import br.com.Alura.screenmatch.services.ConsumoApi;
import br.com.Alura.screenmatch.services.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main();
		main.exibeMenu();
	}
}
