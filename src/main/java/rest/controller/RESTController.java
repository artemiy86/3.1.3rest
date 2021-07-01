package rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rest.model.User;
import rest.service.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RESTController {

    private UserServiceImpl service;

    @Autowired
    public RESTController(UserServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return service.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable long id){
        return service.getUserById(id);
    }

    @PostMapping("/users")
    public User addNewUser(@RequestBody User user){
        return service.createUser(user.getName(),user.getSurname(),user.getAge(),
                user.getEmail(),user.getUsername(),user.getPassword(),user.getRoles());
    }

    @PutMapping("/users")
    public User editUser(@RequestBody User user){
        return service.editUser(user.getId(),user.getName(),user.getSurname(),user.getAge(),
                user.getEmail(),user.getUsername(),user.getPassword(),user.getRoles());
    }

    @DeleteMapping("/users/{id}")
    public long deleteUser(@PathVariable long id){
        service.deleteUser(id);
        return id;
    }

}
