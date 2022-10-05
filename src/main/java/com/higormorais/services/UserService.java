package com.higormorais.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.higormorais.domain.User;
import com.higormorais.dto.UserSaveDTO;
import com.higormorais.repository.UserRepository;
import com.higormorais.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	public User findById(String id) {
		Optional<User> user = userRepository.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	public User insert(User user) {
		return userRepository.insert(user);
	}

	public void delete(String id) {
		findById(id);
		userRepository.deleteById(id);
	}

	public User update(User user) {
		findById(user.getId());
		
		User newUser = findById(user.getId());
		updateData(newUser, user);
		return userRepository.save(newUser);
	}

	private void updateData(User newObj, User user) {
		newObj.setName(user.getName());
		newObj.setEmail(user.getEmail());
	}

	public User fromDTO(UserSaveDTO userSaveDTO) {
		return new User(userSaveDTO.getName(), userSaveDTO.getEmail());
	}
}
