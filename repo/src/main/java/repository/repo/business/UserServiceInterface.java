package repository.repo.business;

import java.util.List;
import java.util.Optional;

import repository.repo.entities.User;


public interface UserServiceInterface {
    List<User> getAll();
    void add(User user);
    void update(User user);
    void delete(User user);
    User getUserById(int id);
    User findByUsername(String username);
    Boolean isUsernameExist(String username);
    Boolean isEmailExist(String email);
    Boolean validateLogin(String username,String password);
    Boolean deleteUserById(Integer id);
    Optional<User> findByEmail(String email);
    void updateUser(User user);
    void deleteTokenByEmail(String email);
}
