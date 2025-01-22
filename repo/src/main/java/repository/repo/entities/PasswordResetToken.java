package repository.repo.entities;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false, unique = true)
    private String token;
    
    @Column(nullable = false)
    private LocalDateTime expiryDate;

    // Constructor with expiryDate
    public PasswordResetToken(String token, String email, LocalDateTime expiryDate) {
        this.token = token;
        this.email = email;
        // Ensure expiryDate is never null
        this.expiryDate = (expiryDate != null) ? expiryDate : LocalDateTime.now().plusHours(1); // Default 1 hour if null
    }

    // Constructor without expiryDate
    public PasswordResetToken(String token, String email) {
        this.token = token;
        this.email = email;
        // Set expiryDate to 1 hour from now if it's not passed
        this.expiryDate = LocalDateTime.now().plusHours(1);
    }

    public PasswordResetToken() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        // Ensure expiryDate is never null
        this.expiryDate = (expiryDate != null) ? expiryDate : LocalDateTime.now().plusHours(1); // Default 1 hour if null
    }
    public boolean isExpired() {
        if (expiryDate == null) {
            return true;  // Eğer createdDate null ise, token geçersiz sayılır
        }
        // Token'ın oluşturulma zamanından itibaren geçen süreyi hesapla
        long minutesSinceCreation = ChronoUnit.MINUTES.between(expiryDate, LocalDateTime.now());
        return minutesSinceCreation > 0;  // Süresi dolmuşsa true döner
    }
}
