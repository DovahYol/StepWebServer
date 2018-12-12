package com.step.webServer.service;

import com.step.webServer.dao.UserDao;
import com.step.webServer.domain.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service("default")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser appUser = userDao.selectByUsername(username);
        if (appUser == null) {
            throw new UsernameNotFoundException(username);
        }
        if (!Integer.valueOf(1).equals(appUser.getConfirmed())) {
            return new User(username, appUser.getPassword(), Arrays.asList(() -> "尚未审核通过"));
        }
        else return new User(username, appUser.getPassword(), new ArrayList<>());
    }

}
