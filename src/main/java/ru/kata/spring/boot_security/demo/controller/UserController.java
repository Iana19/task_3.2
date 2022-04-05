package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController implements WebMvcConfigurer {

    private final UserService userService;
    private final RoleDao roleDao;
    private final UserDetailsService userDetailsService;

    @Autowired
    UserController(UserService userService, RoleDao roleDao
            , @Qualifier("userServiceImpl") UserDetailsService userDetailsService) {
        this.userService = userService;
        this.roleDao = roleDao;
        this.userDetailsService = userDetailsService;
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @GetMapping("/admin")
    public ModelAndView allUsers(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        modelAndView.addObject("users", userService.allUsers());
        modelAndView.addObject("authUser", userService.getUserByEmail(principal.getName()));
//        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @GetMapping("/admin/new")
    public String addUser() {
        return "users";
    }

    @PostMapping("/admin/new")
    public String addUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping ("/admin/edit")
    public String editUser(@ModelAttribute("user") User user, @RequestParam("newRoles[]") String[] roles) {
        Set<Role> roleSet = new HashSet<>();
        for (String r: roles){
            roleSet.add(roleDao.getRoleByName(new Role(r)));
        }
        user.setRoles(roleSet);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @ModelAttribute("auth")
        public UserDetails authUser(Principal principal) {
        return userDetailsService.loadUserByUsername(principal.getName());
    }

    @GetMapping("/user")
    public ModelAndView showUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
