package repository.repo.restapi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import repository.repo.business.ProjectServiceInterface;
import repository.repo.entities.Project;

@Controller
@RequestMapping("/apis")
public class ProjectController {
    @Autowired
    private final ProjectServiceInterface projectService;
    public ProjectController(ProjectServiceInterface projectServiceInterface){
        this.projectService = projectServiceInterface;
    }
    
    @GetMapping("/projectdetails")
    public String ProjectDetails(){
        return "projectdetails";
    }
    @GetMapping("/ProjectControl")
    public String projectControl(){
        return "ProjectControl";
    }
    @GetMapping("/projects/{id}")
    @ResponseBody
    public ResponseEntity<Project> getProjectById(@PathVariable Integer id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Project project = projectService.getProjectById(id);
        if (project != null) {
            return ResponseEntity.ok(project);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    

    @GetMapping("/projects")
    @ResponseBody
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
        }
    @PostMapping("/add")
    public void addProject(@RequestBody Project project) {
        projectService.addProject(project);
        }
    @PostMapping("/update")
    public void updateProject(@RequestBody Project project) {
        projectService.updateProject(project);
    }
    @PostMapping("/delete")
    public void deleteProject(@RequestBody Project project) {
        projectService.deleteProject(project);
    }
    @PostMapping("/upload")
    public ResponseEntity<?> uploadProject(
        @RequestParam("projectName") String projectName,
        @RequestParam("projectDescription") String description,
        @RequestParam("file") MultipartFile file,
        @RequestParam(value = "userId", required = false) Integer userId
    ) {
        try {
            
            // Dosyayı fiziksel olarak kaydet
            String filePath = saveFile(file);

            // Proje nesnesini oluştur ve kaydet
            Project project = new Project();
            project.setProject_name(projectName);
            project.setDescription(description);
            project.setFile_path(filePath);

            projectService.addProject(project);

            return ResponseEntity.ok("Proje başarıyla yüklendi.");
            
        } catch (Exception e) {
            
            return ResponseEntity.status(500).body("Hata: " + e.getMessage());
        }
    }
    private String saveFile(MultipartFile file) throws IOException {
        String uploadDir = "uploaded-files/";
        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        // Klasörü oluştur
        Files.createDirectories(filePath.getParent());

        // Dosyayı kaydet
        Files.write(filePath, file.getBytes());

        return filePath.toString();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProjectById(@PathVariable Integer id){
        Project project=projectService.getProjectById(id);
        if(project==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proje bulunamadı.");
        }else{
            projectService.deleteProjectById(id);
            return ResponseEntity.ok("Proje başarıyla silindi.");
        }
    }
    @GetMapping("/project/file/{id}")
    @ResponseBody
    public ResponseEntity<?> getFileContent(@PathVariable("id") int projectId) {
    // Projeyi getir
    Project project = projectService.getProjectById(projectId);
    if (project == null || project.getFile_path() == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dosya bulunamadı.");
    }
    // Dosyanın içeriğini oku
    Path filePath = Paths.get(project.getFile_path());
    if (!Files.exists(filePath)) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dosya mevcut değil.");
    }
    String content = null;
    try {
        content = Files.readString(filePath);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return ResponseEntity.ok(content);
}
     
@GetMapping("/download/{projectId}")
public ResponseEntity<Resource> downloadProjectCode(@PathVariable Long projectId) throws FileNotFoundException {
    try {
        String fileName = "project" + projectId + ".zip"; // Dosya adı dinamik olarak belirleniyor
        Path filePath = Paths.get("/path/to/your/uploaded-files/" + fileName); // Tam dosya yolu

        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } else {
            throw new FileNotFoundException("Dosya bulunamadı: " + fileName);
        }
    } catch (MalformedURLException ex) {
        throw new FileNotFoundException("Dosya bulunamadı: " + ex.getMessage());
    } catch (IOException e) {
        throw new RuntimeException("Dosya indirirken bir hata oluştu: " + e.getMessage());
    }
}



}
