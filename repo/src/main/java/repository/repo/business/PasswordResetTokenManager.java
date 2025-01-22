package repository.repo.business;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import repository.repo.entities.PasswordResetToken;
import repository.repo.entities.dataaccess.PasswordResetTokenDAOInterface;

@Service
public class PasswordResetTokenManager implements PasswordResetTokenServiceInterface {

    private final PasswordResetTokenDAOInterface passwordResetTokenDAO;

    @Autowired
    
    public PasswordResetTokenManager(PasswordResetTokenDAOInterface passwordResetTokenDAO) {
        this.passwordResetTokenDAO = passwordResetTokenDAO;
    }

    @Override
    @Transactional
    public void saveToken(PasswordResetToken token) {
        passwordResetTokenDAO.saveToken(token); // DAO üzerinden işlem yapılır
    }

    @Override
    @Transactional
    public Optional<PasswordResetToken> findByToken(String token) {
        return passwordResetTokenDAO.findByToken(token); // DAO üzerinden işlem yapılır
    }

    @Override
    @Transactional
    public void deleteTokenByEmail(String email) {
        passwordResetTokenDAO.deleteTokenByEmail(email); // DAO üzerinden işlem yapılır
    }
}
