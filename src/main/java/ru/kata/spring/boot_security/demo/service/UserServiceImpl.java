package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private RoleDao roleDao;
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, RoleDao roleDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
    }

    @Override
    public List<User> allUsers() {
        return userDao.allUsers();
    }

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }

//    @Override
//    public User implEditUser(Long id, String firstName, String lastName, String password, String[] roles, String email) {
//        User user = userDao.getById(id);
//        user.setFirstName(firstName);
//        user.setLastName(lastName);
//        user.setEmail(email);
//        if (!password.isEmpty()) {
//            user.setPassword(password);
//        }
//        Set<Role> setRoles = new HashSet<>();
//        for (String st : roles) {
//            if (st.equals("ROLE_ADMIN")) {
//                Role roleOfAdmin = roleDao.createRoleIfNotFound("ADMIN", 1L);
//                setRoles.add(roleOfAdmin);
//            }
//            if (st.equals("ROLE_USER")) {
//                Role roleOfUser = roleDao.createRoleIfNotFound("USER", 2L);
//                setRoles.add(roleOfUser);
//            }
//        }
//        user.setRoles(setRoles);
//        userDao.save(user);
//        return user;
//    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public void save(User user) {
        user.setPassword(user.getPassword());
        userDao.save(user);
    }

    @Override
    public void updateUser(User newUser) {
        if (newUser.getPassword().equals("")){
            newUser.setPassword(userDao.getById(newUser.getId()).getPassword());
        } else {
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }
        userDao.updateUser(newUser);
    }

    @Override
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.getUserByEmail(email);
        return user;
    }

}