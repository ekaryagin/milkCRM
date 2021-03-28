package com.ekaryagin.milkcrm.security;

import com.ekaryagin.milkcrm.entity.employee.User;
import com.ekaryagin.milkcrm.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService{

    private final UserService userService;

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userService.getUser(userName);

        if (user == null){
            throw new UsernameNotFoundException("User with username: " + userName + "not found");
        }

        return user;
    }
}
