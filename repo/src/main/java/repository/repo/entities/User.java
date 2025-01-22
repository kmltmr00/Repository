package repository.repo.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int ID;

    @Column(nullable=false, unique=true)
    private String username;
    @Column(nullable=false)
    private String user_password;

    @Column(nullable=false, unique=true)
    private String email;

    private boolean is_admin;

    private LocalDateTime user_created_at;

    public User() {
        // Default constructor
    }

    public User(String username, String user_password, String email, boolean is_admin) {
        this.username = username;
        this.user_password = user_password;
        this.email = email;
        this.is_admin = is_admin;
        this.user_created_at = LocalDateTime.now();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getIs_admin() {
        return this.is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public LocalDateTime getUser_created_at() {
        return this.user_created_at;
    }

    @PrePersist
    public void setUser_created_at() {
        this.user_created_at = LocalDateTime.now();
    }
    @Override
    public String toString() {
    return "User{" +
            "ID=" + ID +
            ", username='" + username + '\'' +
            ", user_password='" + user_password + '\'' +
            ", email='" + email + '\'' +
            ", is_admin=" + is_admin +
            ", user_created_at=" + user_created_at +
             "}"+"Password"+user_password;
    }
}
