package com.cmgeorges.examen.mvc.employees.security;

import com.cmgeorges.examen.mvc.employees.controller.dto.UserRegistrationDto;
import com.cmgeorges.examen.mvc.employees.model.Role;
import com.cmgeorges.examen.mvc.employees.model.User;
import com.cmgeorges.examen.mvc.employees.repository.UserRepository;
import com.cmgeorges.examen.mvc.employees.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find User");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    @Override
    public User Update(@RequestBody User user) {
        System.out.println(user.getId());
        Optional<User> userold = userRepository.findById(user.getId());

        userold.get().setRoles(user.getRoles());

        return userRepository.save(userold.get());
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User register(UserRegistrationDto registrationDto) {

        User user = new User();
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setRoles(Collections.singletonList(new Role("USER")));

        return userRepository.save(user);

    }

    @Override
    public User GetUserByEmail(UserRegistrationDto registrationDto) {
        return userRepository.findByEmail(registrationDto.getEmail());
    }

    @Override
    public Page<User> findPages(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> getUsersById(long id) {
        return userRepository.findById(id);
    }

    /**
     * on map un role dans les authorities
     *
     * @param roles
     * @return
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {


        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

    }
}
