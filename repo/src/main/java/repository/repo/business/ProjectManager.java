package repository.repo.business;

import java.io.IOException;
import java.util.List;

import javax.print.PrintException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import repository.repo.entities.Project;
import repository.repo.entities.dataaccess.ProjectDAInterface;
@Service
public class ProjectManager implements ProjectServiceInterface {
    @Autowired
    private final ProjectDAInterface projectDAInterface;
    public ProjectManager(ProjectDAInterface projectDAInterface) {
        this.projectDAInterface =  projectDAInterface;
        }
    @Override
    @Transactional
    public List<Project> getAllProjects() {
       return projectDAInterface.getAllProjects();
    }

    @Override
    @Transactional
    public Project getProjectById(int id) {
       return projectDAInterface.getProjectById(id);
    }

    @Override
    @Transactional
    public void addProject(Project project) {
       projectDAInterface.add(project);
    }

    @Override
    @Transactional
    public void updateProject(Project project) {
       projectDAInterface.update(project);
    }

    @Override
    @Transactional
    public void deleteProject(Project project) {
        projectDAInterface.delete(project);
    }

    @Override
    @Transactional
    public byte[] downloadProjectFile(int id) throws Exception {
      return projectDAInterface.downloadProjectFile(id);
    }

    @Override
    @Transactional
    public void printProjectFile(int id) throws IOException, PrintException {
       projectDAInterface.printProjectFile(id);
    }
   @Override
   public Boolean deleteProjectById(Integer id) {
    return this.projectDAInterface.deleteProjectById(id);
   }
    
}
