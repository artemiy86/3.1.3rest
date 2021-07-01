package rest.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rest.dao.UserRepository;
import rest.model.Role;
import rest.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{


    private UserRepository userRepository;

    public UserServiceImpl(){}

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {
        User user = null;
        Optional<User> optional = userRepository.findById(id);
        if(optional.isPresent()){
            user = optional.get();
        }
        return user;
    }

    @Override
    public User createUser(String name, String surname, int age, String email, String username, String password, Set<Role> roles) {
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setAge(age);
        user.setEmail(email);
        user.setUsername(username);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    @Override
    public User editUser(long id, String name, String surname, int age, String email,String username, String password, Set<Role> roles) {
        User user = getUserById(id);
        user.setName(name);
        user.setSurname(surname);
        user.setAge(age);
        user.setEmail(email);
        user.setUsername(username);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}