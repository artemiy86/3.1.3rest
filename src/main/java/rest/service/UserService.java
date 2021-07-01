package rest.service;



import rest.model.Role;
import rest.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(long id);
    User createUser(String name, String surname, int age, String email, String username, String password, Set<Role> roles);
    User editUser(long id, String name, String surname, int age, String email, String username, String password, Set<Role> roles);
    void deleteUser(long id);
}
