package ru.dzheb.springdatajpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.dzheb.springdatajpa.model.User;
import ru.dzheb.springdatajpa.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public ResponseEntity<Object> getAllUsers() {
        List<User> dbUsers = userRepository.findAll();
        if (dbUsers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // TODO Auto-generated method stub
        return new ResponseEntity<>(dbUsers, HttpStatus.OK);
    }
    public User getUserByLogin (String login){
        return  userRepository.findByLogin(login).orElse(null);
    }
}
