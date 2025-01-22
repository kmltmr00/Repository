package repository.repo.entities.dataaccess;

import java.util.List;
import java.util.Optional;

import repository.repo.entities.User;

public interface UserDAInterface {
    List<User> getAll();
    void add(User user);
    void update(User user);
    void delete(User user);
    User getUserById(int id);
    User findByUsername(String username);
    Boolean isUsernameExist(String username);
    Boolean isEmailExist(String email);
    Boolean deleteUserById(Integer id);
    Optional<User> findByEmail(String email);
    void updateUser(User user);
    void deleteTokenByEmail(String email);
}