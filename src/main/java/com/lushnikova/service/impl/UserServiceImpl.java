package com.lushnikova.service.impl;

import com.lushnikova.dto.request.UserRequest;
import com.lushnikova.dto.response.UserResponse;
import com.lushnikova.mapper.UserMapper;
import com.lushnikova.model.User;
import com.lushnikova.repository.UserRepository;
import com.lushnikova.service.UserService;
import org.audit.annotation.Audit;
import org.audit.enums.EventType;
import org.audit_logging.annotations.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Класс по управлению пользователями
 */
@Loggable
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Audit(eventType = EventType.CREATE_ACCOUNT)
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
    public Optional<UserResponse> findById(Long idUser) {
        try {
            UserResponse userResponse = userMapper.mapToResponse(userRepository.findById(idUser));
            return Optional.of(userResponse);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Audit(eventType = EventType.UPDATE_PROFILE)
    @Override
    public void updateName(Long idUser, String name){
        try {
            userRepository.updateName(idUser, name);
        } catch (SQLException e) {
            System.err.println("Error with updating name user");
        }
    }

    @Audit(eventType = EventType.UPDATE_PROFILE)
    @Override
    public void updateEmail(Long idUser, String email){
        try {
            userRepository.updateEmail(idUser, email);
        } catch (SQLException e) {
            System.err.println("Error with updating email user");
        }
    }

    @Audit(eventType = EventType.UPDATE_PROFILE)
    @Override
    public void updatePassword(Long idUser, String password) {
        try {
            userRepository.updatePassword(idUser, password);
        } catch (SQLException e) {
            System.err.println("Error with updating password user");
        }
    }

    @Audit(eventType = EventType.DELETE_ACCOUNT)
    @Override
    public void delete(Long idUser){
        try {
            userRepository.delete(idUser);
        } catch (SQLException e) {
            System.err.println("Error with deleting user");
        }
    }

    @Audit(eventType = EventType.UPDATE_PROFILE)
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
