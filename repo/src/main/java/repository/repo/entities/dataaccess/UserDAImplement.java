package repository.repo.entities.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import repository.repo.entities.User;
@Repository
public class UserDAImplement implements UserDAInterface{
    @Autowired
    private  final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;
    public UserDAImplement(EntityManager entityManager,JdbcTemplate jdbcTemplate,DataSource dataSource) {
        this.entityManager = entityManager;
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
        }

    @Override
    @Transactional
    public List<User> getAll() {
       Session session=entityManager.unwrap(Session.class);
       return session.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void add(User user) {
        Session session=entityManager.unwrap(Session.class);
        session.save(user);

    }

    @Override
    public void update(User user) {
        Session session=entityManager.unwrap(Session.class);
        session.update(user);
    }

    @Override
    public void delete(User user) {
        Session session=entityManager.unwrap(Session.class);
        session.delete(user);
    }

    @Override
    public User getUserById(int id) {
        Session session=entityManager.unwrap(Session.class);
        return session.get(User.class,id);
    }
    @Override
    public User findByUsername(String username) {
    try {
       
        List<User> result = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();
        if (result.isEmpty()) {
            return null; 
        } else {
            return result.get(0); 
        }
    } catch (Exception e) {
        return null;
    }
    }

    @Override
    public Boolean isUsernameExist(String username) {
      String jpql="SELECT u FROM User u WHERE u.username=:username";
      TypedQuery<User> query=entityManager.createQuery(jpql,User.class);
      query.setParameter("username", username);
      try {
          query.getSingleResult();
          return true;
      } catch (Exception e) {
        return false;
      }
    }

    @Override
    public Boolean isEmailExist(String email) {
        String jpql = "SELECT u FROM User u WHERE u.email = :email";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("email", email);
        
        try {
            query.getSingleResult(); // Veritabanında e-posta var mı kontrolü
            return true; // Eğer varsa true döndür
        } catch (Exception e) {
            return false; // Eğer yoksa false döndür
        }
    }
    @Transactional
    @Override
    public Boolean deleteUserById(Integer id) {
        try{
            User user=entityManager.find(User.class, id);
            if(user!=null){
                entityManager.remove(user);
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        try {
            User user = query.getSingleResult();
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

   @Override
    public void updateUser(User user) {
        String query = "UPDATE users SET user_password = ? WHERE email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUser_password());  // Şifreyi güncelle
            statement.setString(2, user.getEmail());  // E-posta adresine göre güncelle
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Kullanıcı güncellenirken hata oluştu: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteTokenByEmail(String email) {
        String sql = "DELETE FROM password_reset_tokens WHERE email = ?";
        jdbcTemplate.update(sql, email);
    }
    
    

    
}
