package repository.repo.entities.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import repository.repo.entities.PasswordResetToken;
@Repository
public class PasswordResetTokenDAOImpl implements PasswordResetTokenDAOInterface {

    private final DataSource dataSource; // Veritabanı bağlantısı için kullanılır

    public PasswordResetTokenDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveToken(PasswordResetToken token) {
        String query = "INSERT INTO password_reset_token (email, token, expiry_date) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, token.getEmail());
            statement.setString(2, token.getToken());
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(token.getExpiryDate()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Token kaydedilirken hata oluştu: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        String query = "SELECT * FROM password_reset_token WHERE token = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, token);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                PasswordResetToken resetToken = new PasswordResetToken();
                resetToken.setId(rs.getLong("id"));
                resetToken.setEmail(rs.getString("email"));
                resetToken.setToken(rs.getString("token"));
                resetToken.setExpiryDate(rs.getTimestamp("expiry_date").toLocalDateTime());
                return Optional.of(resetToken);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Token bulunurken hata oluştu: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public void deleteTokenByEmail(String email) {
        String query = "DELETE FROM password_reset_token WHERE email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Token silinirken hata oluştu: " + e.getMessage(), e);
        }
    }
}

