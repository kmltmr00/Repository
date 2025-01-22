package repository.repo.business;

import java.util.Optional;

import repository.repo.entities.PasswordResetToken;

public interface PasswordResetTokenServiceInterface {
    void saveToken(PasswordResetToken token);

    Optional<PasswordResetToken> findByToken(String token);

    void deleteTokenByEmail(String email);
}
