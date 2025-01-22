package repository.repo.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import repository.repo.entities.User;
import repository.repo.entities.dataaccess.UserDAInterface;
@Service
public class UserManager implements UserServiceInterface {

    @Autowired 
    private final UserDAInterface userDA;
    public UserManager(UserDAInterface userDA) {
        this.userDA = userDA;
    }

    @Override
    @Transactional
    public List<User> getAll() {
       return userDA.getAll();
    }

    @Override
    public void add(User user) {
       this.userDA.add(user);
    }

    @Override
    public void update(User user) {
       this.userDA.update(user);
    }

    @Override
    public void delete(User user) {
      this.userDA.delete(user);
    }

    @Override
    public User getUserById(int id) {
      return this.userDA.getUserById(id);
    }
    @Override
    public User findByUsername(String username){
      return this.userDA.findByUsername(username);
    }

    @Override
    public Boolean validateLogin(String username, String password) {
      User user =this.userDA.findByUsername(username);
      if(user==null){
        return false;
      }

      return user.getUser_password().equals(password);
    }
    @Override
    public Boolean isUsernameExist(String username) {
      return this.userDA.isUsernameExist(username);
    }

    @Override
    public Boolean isEmailExist(String email) {
      return this.userDA.isEmailExist(email);
    }
    @Transactional
    @Override
    public Boolean deleteUserById(Integer id) {
      User user=userDA.getUserById(id);
      if(user!=null){
        userDA.delete(user);
        return true;
        }
        return false;
    }

    @Override
    public Optional<User> findByEmail(String email) {
      return this.userDA.findByEmail(email);
    }

    @Override
    public void updateUser(User user) {
      this.userDA.updateUser(user);
    }

    @Override
    public void deleteTokenByEmail(String email) {
      this.userDA.deleteTokenByEmail(email);
    }

   
  
   
   
  

}
