package br.com.Alura.screenmatch;

import br.com.Alura.screenmatch.models.DadosSerie;
import br.com.Alura.screenmatch.services.ConsumoApi;
import br.com.Alura.screenmatch.services.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		System.out.println("Primeiro projeto Spring sem Web");
//		https://www.omdbapi.com/?t=gilmore+girls&apikey=8ce78692

		var comsumoApi = new ConsumoApi();
		var json = comsumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=8ce78692");
		System.out.println(json);

		ConverteDados dados = new ConverteDados();
		var serie = dados.obterDados(json, DadosSerie.class);
		System.out.println(serie);
	}
}
