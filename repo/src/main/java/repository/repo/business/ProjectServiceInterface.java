package repository.repo.business;

import java.io.IOException;
import java.util.List;

import javax.print.PrintException;

import repository.repo.entities.Project;

public interface ProjectServiceInterface {
    List<Project> getAllProjects();
    Project getProjectById(int id);
    void addProject(Project project);
    void updateProject(Project project);
    void deleteProject(Project project);
    Boolean deleteProjectById(Integer id);
    byte[] downloadProjectFile(int id) throws Exception;
    void printProjectFile(int id) throws IOException,PrintException;
}
