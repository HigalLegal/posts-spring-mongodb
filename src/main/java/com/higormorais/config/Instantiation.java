package com.higormorais.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.higormorais.domain.Post;
import com.higormorais.domain.User;
import com.higormorais.dto.AuthorDTO;
import com.higormorais.dto.CommentDTO;
import com.higormorais.repository.PostRepository;
import com.higormorais.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userReposiroty;

	@Autowired
	private PostRepository postReposiroty;

	@Override
	public void run(String... arg0) throws Exception {
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		userReposiroty.deleteAll();
		postReposiroty.deleteAll();
		
		User fabio = new User("Fábio", "fabio@gmail.com");
		User guilherme = new User("Guilherme", "guilherme@gmail.com");
		User anderson = new User("Anderson", "anderson@gmail.com");
		
		userReposiroty.saveAll(Arrays.asList(fabio, guilherme, anderson));

		Post post1 = new Post(LocalDate.parse("21/03/2022", format), "Partiu viagem", "Vou viajar para São Paulo. Abraços!", 
				new AuthorDTO(fabio));
		Post post2 = new Post(LocalDate.parse("23/03/2022", format), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(fabio));

		CommentDTO c1 = new CommentDTO("Boa viagem mano!", LocalDate.parse("21/03/2022", format), new AuthorDTO(guilherme));
		CommentDTO c2 = new CommentDTO("Aproveite", LocalDate.parse("22/03/2022", format), new AuthorDTO(anderson));
		CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", LocalDate.parse("23/03/2022", format), new AuthorDTO(guilherme));
		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().addAll(Arrays.asList(c3));
		
		postReposiroty.saveAll(Arrays.asList(post1, post2));
		
		fabio.getPosts().addAll(Arrays.asList(post1, post2));
		userReposiroty.save(fabio);
	}

}
