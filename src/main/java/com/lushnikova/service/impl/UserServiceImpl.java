package com.lushnikova.service.impl;

import com.lushnikova.dto.request.UserRequest;
import com.lushnikova.dto.response.UserResponse;
import com.lushnikova.mapper.UserMapper;
import com.lushnikova.model.User;
import com.lushnikova.repository.UserRepository;
import com.lushnikova.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Класс Service по управлению пользователями и их привычек
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserResponse> save(UserRequest userRequest){
        try {
            User user = userMapper.mapToEntity(userRequest);
            return Optional.of(userMapper.mapToResponse(userRepository.save(user)));
        } catch (SQLException e) {
            System.err.println("Error with creating user");
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserResponse> findById(Long id) {
        try {
            UserResponse userResponse = userMapper.mapToResponse(userRepository.findById(id));
            return Optional.of(userResponse);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateName(Long id, String name){
        try {
            userRepository.updateName(id, name);
        } catch (SQLException e) {
            System.err.println("Error with updating name user");
        }
    }

    @Override
    public void updateEmail(Long id, String email){
        try {
            userRepository.updateEmail(id, email);
        } catch (SQLException e) {
            System.err.println("Error with updating email user");
        }
    }

    @Override
    public void updatePassword(Long id, String password) {
        try {
            userRepository.updatePassword(id, password);
        } catch (SQLException e) {
            System.err.println("Error with updating password user");
        }
    }

    @Override
    public void delete(Long id){
        try {
            userRepository.delete(id);
        } catch (SQLException e) {
            System.err.println("Error with deleting user");
        }
    }

    @Override
    public void blockByIdUser(Long idUser, boolean isActive) {
        try {
            userRepository.setIsActive(idUser, isActive);
        } catch (SQLException e) {
            System.err.println("Error with block user");
        }
    }

    @Override
    public List<UserResponse> findAll(){
        try {
            return userRepository.findAll().stream().map(userMapper::mapToResponse).toList();
        } catch (SQLException e) {
            System.err.println("Error with getting list users");
            return null;
        }
    }
}
