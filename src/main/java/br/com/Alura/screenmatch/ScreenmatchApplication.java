package br.com.Alura.screenmatch;

import br.com.Alura.screenmatch.Main.Principal;
import br.com.Alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {
	@Autowired
	private SerieRepository repositorio;

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		boolean interactive = Boolean.parseBoolean(System.getProperty("app.interactive", "true"));

		if (interactive) {
			Principal principal = new Principal(repositorio);
			principal.exibeMenu();
		}
	}
}
