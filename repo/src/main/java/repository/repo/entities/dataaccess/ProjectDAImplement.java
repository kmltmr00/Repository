package repository.repo.entities.dataaccess;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import repository.repo.entities.Project;
@Repository
public class ProjectDAImplement implements ProjectDAInterface {
   @Autowired
   private final EntityManager entityManager;
   public ProjectDAImplement(EntityManager entityManager){
    this.entityManager = entityManager;
   }
    @Override
    public List<Project> getAllProjects() {
         Session session=entityManager.unwrap(Session.class);
         return session.createQuery("from Project", Project.class).getResultList();
    }

    @Override
    public void add(Project project) {
        Session session=entityManager.unwrap(Session.class);
        session.save(project);
    }

    @Override
    public void delete(Project project) {
        Session session=entityManager.unwrap(Session.class);
        session.remove(project);
    }

    @Override
    public void update(Project project) {
        Session session=entityManager.unwrap(Session.class);  
        session.merge(project);
    }

    @Override
    public Project getProjectById(int id) {
        Session session=entityManager.unwrap(Session.class);
        return session.get(Project.class,id);
    }

    @Override
    public byte[] downloadProjectFile(int id) throws Exception {
     Project project=getProjectById(id);
     String filepath=project.getFile_path();
     Path path =Paths.get(filepath);
     if(Files.exists(path)){
        return Files.readAllBytes(path);
     }else{
        throw new Exception("Dosya Bulunamadı.");
     }
     
    }

    @Override
    public void printProjectFile(int id) throws IOException, PrintException {
      Project project = getProjectById(id);
      String filepath = project.getFile_path();
      File file=new File(filepath);
      if(file.exists()){
        DocFlavor flavor =DocFlavor.INPUT_STREAM.AUTOSENSE;
        PrintRequestAttributeSet pras=new HashPrintRequestAttributeSet();
        DocPrintJob job=PrintServiceLookup.lookupDefaultPrintService().createPrintJob();
        Doc doc=new SimpleDoc(file.toURI().toURL().openStream(), flavor, null);
        job.print(doc, pras);
    }else{
        throw new IOException("Dosya Bulunamadı");
    }
      }

      @Override
      @Transactional
      public Boolean deleteProjectById(Integer id){
        try{
            Project project=entityManager.find(Project.class,id);
            if(project!=null){
                entityManager.remove(project);
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            return false;
            
        }
      }
}
