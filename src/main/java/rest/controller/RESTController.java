package rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.model.Role;
import rest.model.User;
import rest.service.RoleServiceImpl;
import rest.service.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RESTController {

    private UserServiceImpl service;
    private RoleServiceImpl roleService;

    @Autowired
    public RESTController(UserServiceImpl service, RoleServiceImpl roleService) {
        this.service = service;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(service.getAllUsers(), responseHeaders, HttpStatus.OK);
    }
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles(){
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(roleService.getAllRoles(), responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id){
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(service.getUserById(id),responseHeaders,HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addNewUser(@RequestBody User user){
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(service.createUser(user.getName(),user.getSurname(),user.getAge(),
                user.getEmail(),user.getUsername(),user.getPassword(),user.getRoles()), responseHeaders, HttpStatus.OK);
    }

    @PutMapping("/users")
    public ResponseEntity<User> editUser(@RequestBody User user){
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(service.editUser(user.getId(),user.getName(),user.getSurname(),user.getAge(),
                user.getEmail(),user.getUsername(),user.getPassword(),user.getRoles()), responseHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable long id){
        service.deleteUser(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(id, responseHeaders, HttpStatus.OK);
    }

}
