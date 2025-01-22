package repository.repo.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import repository.repo.business.UserServiceInterface;
import repository.repo.entities.User;

@Controller
@RequestMapping("/api/users")

public class UserController  {
    @Autowired
    private final UserServiceInterface userService;
    public UserController(UserServiceInterface userService) {
        this.userService=userService;
    }
    @GetMapping("/llogin")
    public String Login(){
        return "Login";
    }
    @GetMapping("/HomePage")
    public String HomePage(Model model,HttpSession session){
        String username=(String) session.getAttribute("username");
        if (username != null) {
            model.addAttribute("username", username);
        } else {
            model.addAttribute("username", "Misafir");
        }
        return "homepage";
    }
    @GetMapping("/Project")
    public String Project(){
        return "Project";
    }
    @GetMapping("/About")
    public String About(){
        return "About";
    }
    @GetMapping("/register")
        public String register(){
            return "Register"; 
    }
    @GetMapping("/view")
    @ResponseBody
    public List<User> getAll() {
        return userService.getAll();
    }
    @GetMapping("/admin")
    public String admin(){
        return "AdminControl";
        }
    @GetMapping("/adminuser")
    public String adminuser(){
        return "UserControl";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/api/users/llogin";
    }
    @ResponseBody
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id){
        User user =userService.getUserById(id);
        if(user!=null){
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @ResponseBody
    @GetMapping("/username/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username){
        User user =userService.findByUsername(username);
        if(user!=null){
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/llogin")
    public String userLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {
        boolean isValid = userService.validateLogin(username, password);
       
        if (isValid) {
            // Başarılı giriş yapıldığında username'i modele ekle
            if("admin".equals(username)&&"admin".equals(password)){
                session.setAttribute("admin", "admin");
                return "redirect:/api/users/admin";
            }

            session.setAttribute("username", username);
            return "redirect:/api/users/HomePage"; // homepage.html'e yönlendirme
        }
        else {
            // Hatalı girişte hataf mesajı ekle ve login sayasına geri dön
          model.addAttribute("error","Kullanıcı Adı veya Şifre Hatalı ");
            return "login"; // login.html'e yönlendirme
    }           
    }
     
    
    @PostMapping("/register")
    public String Register(
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam("email") String email,
        RedirectAttributes redirectAttributes) {

        if(userService.isUsernameExist(username)){
            redirectAttributes.addFlashAttribute("error", "Kullanıcı adı zaten var");
            return "redirect:/api/users/register";
        }
        if(userService.isEmailExist(email)){
            redirectAttributes.addFlashAttribute("error", "E-mail zaten var");
            return "redirect:/api/users/register";
        }
        User user = new User();
        user.setUsername(username);
        user.setUser_password(password);
        user.setEmail(email);
        userService.add(user);

        redirectAttributes.addFlashAttribute("message", "Kayıt Başarıyla Oluşturuldu");
        return "redirect:/api/users/llogin";
    }
    @PostMapping("/addAdmin")
    public String addAdmin(
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam("email") String email,
        RedirectAttributes redirectAttributes) {

        if(userService.isUsernameExist(username)){
            redirectAttributes.addFlashAttribute("error", "Kullanıcı adı zaten var");
            return "redirect:/api/users/adminuser";
        }
        if(userService.isEmailExist(email)){
            redirectAttributes.addFlashAttribute("error", "E-mail zaten var");
            return "redirect:/api/users/adminuser";
        }
        User user = new User();
        user.setUsername(username);
        user.setUser_password(password);
        user.setEmail(email);
        userService.add(user);

        redirectAttributes.addFlashAttribute("message", "Kayıt Başarıyla Oluşturuldu");
        return "redirect:/api/users/adminuser";
    }
   
    @ResponseBody
    @PostMapping("/add")
    public void add(@ModelAttribute User user) {
        userService.add(user);
    }
    @ResponseBody
    @PostMapping("/upload")
    public void update(@RequestBody User user){
        userService.update(user);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id){
       User user=userService.getUserById(id);
       if(user==null){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kullanıcı bulunamadı"+id);
       }else{
        userService.deleteUserById(id);
        return ResponseEntity.ok("Kullanıcı başarıyla silindi");
       }
        }

    
  

}