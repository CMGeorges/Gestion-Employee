package com.cmgeorges.examen.mvc.employees.service;

import com.cmgeorges.examen.mvc.employees.controller.dto.UserRegistrationDto;
import com.cmgeorges.examen.mvc.employees.model.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;


public interface UserService extends UserDetailsService {

    User Update(User user);

    void deleteUser(long id);

    User register(UserRegistrationDto registrationDto);

    User GetUserByEmail(UserRegistrationDto registrationDto);

    Page<User> findPages(int pageNo, int pageSize, String sortField, String sortDirection);

    Optional<User> getUsersById(long id);
}
