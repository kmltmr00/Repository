package repository.repo.entities.dataaccess;

import java.io.IOException;
import java.util.List;

import javax.print.PrintException;

import repository.repo.entities.Project;

public interface ProjectDAInterface {
    List<Project> getAllProjects();
    void add(Project project);
    void delete(Project project);
    void update(Project project);
    Project getProjectById(int id);
    Boolean deleteProjectById(Integer id);
    byte[] downloadProjectFile(int id) throws Exception;
    void printProjectFile(int id) throws IOException,PrintException;
}
