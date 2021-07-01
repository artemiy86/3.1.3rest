package rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import rest.model.Role;
import rest.model.User;
import rest.service.RoleServiceImpl;
import rest.service.UserServiceImpl;

import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserServiceImpl service;
    private RoleServiceImpl roleService;

    public AdminController(){}

    @Autowired
    public AdminController(UserServiceImpl service, RoleServiceImpl roleService) {
        this.service = service;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAdminPage(@AuthenticationPrincipal User user, ModelMap model){
        model.addAttribute("principal",user);
        model.addAttribute("users",service.getAllUsers());
        model.addAttribute("newUser",new User());
        model.addAttribute("roles",roleService.getAllRoles());
        return "view/adminPanel";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam(value = "id") long id){
        service.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/add")
    public String addUserForm(ModelMap model){
        User user = new User();
        model.addAttribute("action","add");
        model.addAttribute("user", user);
        model.addAttribute("roles",new Role());
        return "view/form";
    }

    @GetMapping("/edit")
    public String editUserForm(@RequestParam(value = "id") long id,
                               ModelMap model){
        User user = service.getUserById(id);
        model.addAttribute("user",user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "view/editUserModal";
    }

    @GetMapping("/delete")
    public String deleteUserForm(@RequestParam(value = "id") long id,
                               ModelMap model){
        User user = service.getUserById(id);
        model.addAttribute("user",user);
        model.addAttribute("roles", user.getRoles());
        return "view/deleteUserModal";
    }

    @PostMapping("/edit")
    public String editUser(@RequestParam(value = "id") long id,
                           @RequestParam(value = "name") String name,
                           @RequestParam(value = "surname") String surname,
                           @RequestParam(value = "age") int age,
                           @RequestParam(value = "email") String email,
                           @RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "roles") Set<Role> roles){
        service.editUser(id, name, surname, age, email,username,password, roles);
        return "redirect:/admin";
    }

    @PostMapping("/add")
    public String createUser(@RequestParam(value = "name") String name,
                             @RequestParam(value = "surname") String surname,
                             @RequestParam(value = "age") int age,
                             @RequestParam(value = "email") String email,
                             @RequestParam(value = "username") String username,
                             @RequestParam(value = "password") String password,
                             @RequestParam(value = "roles") Set<Role> roles){
        service.createUser(name, surname, age, email,username,password,roles);
        return "redirect:/admin";
    }

}