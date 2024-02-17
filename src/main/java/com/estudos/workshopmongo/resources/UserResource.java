package com.estudos.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.estudos.workshopmongo.domain.User;
import com.estudos.workshopmongo.dto.UserDTO;
import com.estudos.workshopmongo.services.UserService;

@RestController
@RequestMapping(value="/users")
public class UserResource {

	@Autowired
	private UserService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> findAll() {
		// 1 - buscando na base
		List<User> list = service.findAll();  
		// 2 - transformar a list (entidade) em uma lista de DTO. Para cada elemento(x) vai converter em DTO e 
		//     depois reuni-los em uma nova lista(listDto)
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList()); 
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET) //200
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		User obj = service.findById(id);           // 1 - pegar do BD
		UserDTO objDto = new UserDTO(obj);         // 2 - converter para DTO
		return ResponseEntity.ok().body(objDto); 
	}
	
	@RequestMapping(method=RequestMethod.POST) 
	public ResponseEntity<Void> insert(@RequestBody UserDTO objDto) {
		// converter DTO para User
		User obj = service.fromDTO(objDto);      
		obj = service.insert(obj);
		// pega o endereço do novo objeto
		// retorna 201, com corpo de requisição vazio e endereço (id) no Header
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();	 
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE) 
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);           	        
		return ResponseEntity.noContent().build(); 
	}
}
