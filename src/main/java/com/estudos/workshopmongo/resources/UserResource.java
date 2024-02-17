package com.estudos.workshopmongo.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
