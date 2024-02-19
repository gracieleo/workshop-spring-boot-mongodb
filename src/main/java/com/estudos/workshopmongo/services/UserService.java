package com.estudos.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estudos.workshopmongo.domain.User;
import com.estudos.workshopmongo.dto.UserDTO;
import com.estudos.workshopmongo.repository.UserRepository;
import com.estudos.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;
	
	public List<User> findAll() {
		return repo.findAll();
	}
	
	public User findById(String id) {
		Optional<User> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public User insert(User obj) {
		return repo.insert(obj);
	}
	
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}
	
	public User update(User obj) {
		// buscar o objeto na base de dados
		// atualizar o objeto e salvar na base
		User newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	private void updateData(User newObj, User obj) {
		// copiar dados de obj para newObj
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}

	public User fromDTO(UserDTO objDto) {
		/* 
		 * poderia estar na classe UserDTO mas aqui fica mais fácil a manutenção visto que o Service tem acesso 
		   direto a base de dados. Passos:
		   1 - pegar um DTO
		   2 - transformar em objeto no banco a partir dos valores passados no dto
		*/
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail()); 
	}
}
