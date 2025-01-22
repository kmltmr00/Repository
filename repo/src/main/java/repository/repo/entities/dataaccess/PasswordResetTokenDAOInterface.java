package repository.repo.entities.dataaccess;

import java.util.Optional;

import repository.repo.entities.PasswordResetToken;

public interface PasswordResetTokenDAOInterface {

    void saveToken(PasswordResetToken token);

    Optional<PasswordResetToken> findByToken(String token);

    void deleteTokenByEmail(String email);
}

