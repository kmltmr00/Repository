package repository.repo.restapi;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import repository.repo.business.EmailService;
import repository.repo.business.PasswordResetTokenServiceInterface;
import repository.repo.business.UserServiceInterface;
import repository.repo.entities.PasswordResetToken;
import repository.repo.entities.User;
@Controller
@RequestMapping("/api/password-reset")
public class PasswordResetController {

    private final PasswordResetTokenServiceInterface tokenService;
    private final UserServiceInterface userService;
    private final EmailService emailService; 

    @Autowired
    public PasswordResetController(PasswordResetTokenServiceInterface tokenService, UserServiceInterface userService, EmailService emailService) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/request")
    public String showPasswordResetRequestPage() {
        return "PasswordResetRequest";
    }

    @GetMapping("/reset")
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "ResetPassword";
    }

    @PostMapping("/request")
    public ResponseEntity<Map<String, Object>> requestPasswordReset(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        Optional<User> user = userService.findByEmail(email);
    
        Map<String, Object> response = new HashMap<>();
        if (user.isEmpty()) {
            response.put("success", false);
            response.put("message", "Geçersiz e-posta adresi.");
            return ResponseEntity.badRequest().body(response);  // Hata durumunda JSON döndür
        }
    
        // Token oluştur
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, email);
        tokenService.saveToken(resetToken);
    
        String resetLink = "http://localhost:8080/api/password-reset/reset?token=" + token;
        String subject = "Şifre Sıfırlama Talebi";
        String text = "Şifrenizi sıfırlamak için aşağıdaki linki kullanın:\n" + resetLink;
        emailService.sendEmail(email, subject, text);
    
        response.put("success", true);
        response.put("message", "Şifre sıfırlama talebiniz alınmıştır. Lütfen e-postanızı kontrol edin.");
        return ResponseEntity.ok(response);  // Başarı durumunda JSON döndür
    }

    @GetMapping("/get-user")
public ResponseEntity<Map<String, Object>> getUserByToken(@RequestParam String token) {
    Map<String, Object> response = new HashMap<>();

    // Token'ı kontrol et
    Optional<PasswordResetToken> resetToken = tokenService.findByToken(token);

    if (resetToken.isEmpty()) {
        response.put("success", false);
        response.put("message", "Geçersiz token.");
        return ResponseEntity.badRequest().body(response);
    }

    String email = resetToken.get().getEmail();
    Optional<User> user = userService.findByEmail(email);

    if (user.isEmpty()) {
        response.put("success", false);
        response.put("message", "Kullanıcı bulunamadı.");
        return ResponseEntity.badRequest().body(response);
    }

    User existingUser = user.get();
    response.put("success", true);
    response.put("username", existingUser.getUsername()); // Kullanıcı adını döndürüyoruz
    response.put("password", existingUser.getUser_password()); // Kullanıcı şifresini döndürüyoruz
    return ResponseEntity.ok(response);
}

}
