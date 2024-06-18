package com.ProjectManagement.pms.Repository;

import com.ProjectManagement.pms.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    @Query(value = " SELECT * FROM project WHERE deleted = FALSE ", nativeQuery = true)
    List<Project> findProjects();

    @Query(value = " SELECT * FROM project WHERE deleted = FALSE and id = :id", nativeQuery = true)
    List<Project> findProjectsReport(@Param("id") Long id);

    @Query(value = " SELECT * FROM project WHERE start_date_time >= :startDate AND start_date_time <= :endDate ", nativeQuery = true)
    List<Project> getByDateStart(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value = " SELECT * FROM project WHERE end_date_time >= :startDate AND end_date_time <= :endDate ", nativeQuery = true)
    List<Project> getByDateEnd(@Param("startDate") String endDate0, @Param("endDate") String endDate1);

    @Query(value = " SELECT * FROM project WHERE start_date_time >= :startDate AND end_date_time <= :endDate ", nativeQuery = true)
    List<Project> getByDateCustom(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value = " SELECT p.id, p.created_at, p.created_by, p.deleted, p.updated_at, p.updated_by, p.end_date_time, p.intro, p.name, p.start_date_time, p.status, p.owner FROM project p JOIN member_project mp ON p.id = mp.id WHERE mp.user_id = :userId ", nativeQuery = true)
    List<Project> findProjectsByMember(@Param("userId") Long id);
}
