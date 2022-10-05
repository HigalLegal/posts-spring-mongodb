package com.higormorais.resources;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.higormorais.domain.Post;
import com.higormorais.resources.util.URL;
import com.higormorais.services.PostService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/posts")
@Api("API dos posts")
public class PostResource {

	@Autowired
	private PostService postService;
	
	// --------------------------------------------------------------------------------------------------------------------

	@GetMapping("/{id}")
	@ApiOperation("Busca por id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Post encontrado"),
		@ApiResponse(code = 404, message = "Post não encontrado")
	})
 	public ResponseEntity<Post> findById(@PathVariable @ApiParam("Id do post") String id) {
		Post post = postService.findById(id);
		return ResponseEntity.ok().body(post);
	}
	
	@GetMapping("/title-search")
	@ApiOperation("Busca de acordo com título")
	@ApiResponse(code = 200, message = "Requisição feita com sucesso.")
 	public ResponseEntity<Page<Post>> findByTitle(@RequestParam(value="text", defaultValue="") @ApiParam("Título") String text,
 			@PageableDefault Pageable pageable) {
		text = URL.decodeParam(text);
		Page<Post> list = postService.findByTitle(text, pageable);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/full-search")
	@ApiOperation("Busca completa")
	@ApiResponse(code = 200, message = "Requisição feita com sucesso.")
 	public ResponseEntity<List<Post>> fullSearch(
 			@RequestParam(value="text", defaultValue="") @ApiParam("Título ou comentários") String text,
 			@RequestParam(value="minDate", defaultValue="") @ApiParam("Data mínima") String minDate,
 			@RequestParam(value="maxDate", defaultValue="") @ApiParam("Data máxima") String maxDate) {
		text = URL.decodeParam(text);
		LocalDate min = URL.convertDate(minDate, LocalDate.ofEpochDay(0L));
		LocalDate max = URL.convertDate(maxDate, LocalDate.now());
		List<Post> list = postService.fullSearch(text, min, max);
		return ResponseEntity.ok().body(list);
	}
}
