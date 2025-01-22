package repository.repo.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name="Project")
public class Project{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int ID;
    private String project_name;
    private String description;
    @Column(nullable=false)
    private String file_path;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user_id;
    private LocalDateTime project_created_at;
    
    public Project(){
        this.project_created_at=LocalDateTime.now();
        //default constructor
    }
 
    public Project(String project_name, String description, String file_path, User user_id, LocalDateTime project_created_at){
        this.project_name=project_name;
        this.description=description;
        this.file_path=file_path;
        this.user_id=user_id;
        this.project_created_at=project_created_at;
    }
    public int getID(){
        return ID;
    }
    public void setID(int ID){
        this.ID = ID;
    }
    public String getProject_name(){
        return project_name;
    }
    public void setProject_name(String project_name){
        this.project_name = project_name;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getFile_path(){
        return file_path;
    }
    public void setFile_path(String file_path){
        this.file_path = file_path;
    }
    public User getUser_id(){
        return user_id;
    }
    public void setUser_id(User user_id){
        this.user_id = user_id;
    }
    public LocalDateTime getProject_created_at(){
        return project_created_at;
    }
    @PrePersist
    public void Project_created_at(){
        this.project_created_at=LocalDateTime.now();
    } 
    
}
        
