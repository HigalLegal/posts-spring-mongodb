package com.higormorais.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.higormorais.domain.Post;
import com.higormorais.domain.User;
import com.higormorais.dto.UserDTO;
import com.higormorais.dto.UserSaveDTO;
import com.higormorais.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/users")
@Api("API dos usuários")
public class UserResource {

	@Autowired
	private UserService userService;
	
	// --------------------------------------------------------------------------------------------------------------------
	
	@GetMapping
	@ApiOperation("Retorna todos os usuários")
	@ApiResponse(code = 200, message = "Todos os clientes foram retornados")
 	public ResponseEntity<Page<UserDTO>> findAll(@PageableDefault Pageable pageable) {
		
		Page<UserDTO> listDto = userService.findAll(pageable)
				.map(user -> new UserDTO(user));
		
		return ResponseEntity.ok().body(listDto);
	}

	@GetMapping("/{id}")
	@ApiOperation("Retorna um usuário específico por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Usuário encontrado"),
		@ApiResponse(code = 404, message = "Usuário não encontrado")
	})
 	public ResponseEntity<UserDTO> findById(@PathVariable @ApiParam("Id do usuário") String id) {
		User user = userService.findById(id);
		return ResponseEntity.ok().body(new UserDTO(user));
	}

	@PostMapping
	@ApiOperation("Insere um novo usuário")
	@ApiResponse(code = 201, message = "Usuário inserido com sucesso!")
 	public ResponseEntity<Void> insert(@RequestBody @Valid UserSaveDTO userSaveDTO) {
		User user = userService.fromDTO(userSaveDTO);
		user = userService.insert(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/{id}")
	@ApiOperation("Deleta um usuário de acordo com o ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Usuário excluído com sucesso!"),
		@ApiResponse(code = 404, message = "Usuário não encontrado")
	})
 	public ResponseEntity<Void> delete(@PathVariable @ApiParam("Id do usuário a ser deletado") String id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	@ApiOperation("Altera um usuário existente de acordo com o ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Usuário alterado com sucesso!"),
		@ApiResponse(code = 404, message = "Usuário não encontrado")
	})
 	public ResponseEntity<Void> update(@RequestBody @Valid UserSaveDTO userSaveDTO, @PathVariable String id) {
		User user = userService.fromDTO(userSaveDTO);
		user.setId(id);
		user = userService.update(user);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}/posts")
	@ApiOperation("Retorna todos os posts de um usuário específico, de acordo com o ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Post(s) do usuário encontrados!"),
		@ApiResponse(code = 404, message = "Usuário não encontrado")
	})
 	public ResponseEntity<List<Post>> findPosts(@PathVariable @ApiParam("Id do usuário") String id) {
		User user = userService.findById(id);
		return ResponseEntity.ok().body(user.getPosts());
	}
}
