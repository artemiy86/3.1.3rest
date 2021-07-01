package rest.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import rest.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
 User findByUsername(String username);
}
