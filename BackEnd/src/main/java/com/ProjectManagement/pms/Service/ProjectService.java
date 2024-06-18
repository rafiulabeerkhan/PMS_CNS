package com.ProjectManagement.pms.Service;

import com.ProjectManagement.pms.Repository.ProjectRepository;
import com.ProjectManagement.pms.Repository.UserRepository;
import com.ProjectManagement.pms.DTO.ProjectDto;
import com.ProjectManagement.pms.Entity.Project;
import com.ProjectManagement.pms.Entity.Status;
import com.ProjectManagement.pms.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    public List<ProjectDto> getAllData(){
        List<Project> projects = projectRepository.findProjects();
        List<ProjectDto> projectDtos = new ArrayList<>();
        for (Project project: projects){
           ProjectDto projectDto = new ProjectDto();
           mapToDto(projectDto,project);
           projectDtos.add(projectDto);
        }
        return projectDtos;
    }

    public List<ProjectDto> getAllData(Long id){
        List<Project> projects = projectRepository.findProjectsReport(id);
        List<ProjectDto> projectDtos = new ArrayList<>();
        for (Project project: projects){
            ProjectDto projectDto = new ProjectDto();
            mapToDto(projectDto,project);
            projectDtos.add(projectDto);
        }
        return projectDtos;
    }

    public ProjectDto saveData(ProjectDto projectDto){
        Project project = new Project();
        Set<Long> members = projectDto.getMembers();
        if (members.size()>5){
            throw new RuntimeException("Members should not be more than 5..");
        } else {
            mapToEntity(projectDto,project);
            projectRepository.save(project);
            return projectDto;
        }
    }

    public ProjectDto getById(Long id){
    ProjectDto projectDto = new ProjectDto();
    Project project = projectRepository.findById(id).get();
    mapToDto(projectDto,project);
    return projectDto;
    }

    public void deleteData(Long id){
        Project project = projectRepository.findById(id).get();
        if (project != null){
            project.setDeleted(true);
            projectRepository.save(project);
        } else {
            throw new RuntimeException("Project not found");
        }
    }

    public Project mapToEntity(ProjectDto projectDto, Project project){
        if (project != null){
            project.setId(projectDto.getId());
        }
    project.setName(projectDto.getName());
    project.setIntro(projectDto.getIntro());
    project.setOwner(userRepository.findById(projectDto.getOwner()).get());
    project.setStatus(Status.valueOf(projectDto.getStatus()));
    project.setStartDateTime(projectDto.getStartDateTime());
    project.setEndDateTime(projectDto.getEndDateTime());
    final List<User> users = userRepository.findAllById(projectDto.getMembers() == null ? Collections.emptyList() : projectDto.getMembers());
    if (users.size() != (projectDto.getMembers() == null ? 0 : projectDto.getMembers().size())) {
        throw new RuntimeException("one of members not found");
    }
    project.setMembers(new HashSet<>(users));
    return project;
    }

    public ProjectDto mapToDto(ProjectDto projectDto, Project project){
        if (projectDto != null){
            projectDto.setId(project.getId());
            projectDto.setName(project.getName());
            projectDto.setIntro(project.getIntro());
            projectDto.setOwner(project.getOwner().getUserId());
            projectDto.setOwnerName(project.getOwner().getUsername());
            projectDto.setStatus(project.getStatus().toString());
            projectDto.setStartDateTime(project.getStartDateTime());
            projectDto.setEndDateTime(project.getEndDateTime());
            projectDto .setMembers(project.getMembers() == null ? null : project.getMembers().stream()
                    .map(User::getUserId)
                    .collect(Collectors.toSet()));
            projectDto .setMemberNames(project.getMembers() == null ? null : project.getMembers().stream()
                    .map(User::getUsername)
                    .collect(Collectors.toSet()));
        }
        return projectDto;
    }

    public List<ProjectDto> getByDateStart(String startDate0, String endDate1) {
        List<Project> projects = projectRepository.getByDateStart(startDate0, endDate1);
        List<ProjectDto> projectDtos = new ArrayList<>();
        for (Project project: projects){
            ProjectDto projectDto = new ProjectDto();
            mapToDto(projectDto,project);
            projectDtos.add(projectDto);
        }
        return projectDtos;
    }

    public List<ProjectDto> getByDateEnd(String endDate0, String endDate1) {
        List<Project> projects = projectRepository.getByDateEnd(endDate0, endDate1);
        List<ProjectDto> projectDtos = new ArrayList<>();
        for (Project project: projects){
            ProjectDto projectDto = new ProjectDto();
            mapToDto(projectDto,project);
            projectDtos.add(projectDto);
        }
        return projectDtos;
    }

    public List<ProjectDto> getByDateCustom(String startDate, String endDate) {
        List<Project> projects = projectRepository.getByDateCustom(startDate, endDate);
        List<ProjectDto> projectDtos = new ArrayList<>();
        for (Project project: projects){
            ProjectDto projectDto = new ProjectDto();
            mapToDto(projectDto,project);
            projectDtos.add(projectDto);
        }
        return projectDtos;
    }

    public List<ProjectDto> getAllDataByMember(Long id) {
        List<Project> projects = projectRepository.findProjectsByMember(id);
        List<ProjectDto> projectDtos = new ArrayList<>();
        for (Project project: projects){
            ProjectDto projectDto = new ProjectDto();
            mapToDto(projectDto,project);
            projectDtos.add(projectDto);
        }
        return projectDtos;
    }
}
