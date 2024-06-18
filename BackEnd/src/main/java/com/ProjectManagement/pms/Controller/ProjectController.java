package com.ProjectManagement.pms.Controller;

import com.ProjectManagement.pms.DTO.ProjectDto;
import com.ProjectManagement.pms.DTO.SearchDateStart;
import com.ProjectManagement.pms.Repository.ProjectRepository;
import com.ProjectManagement.pms.Service.JasperExportService;
import com.ProjectManagement.pms.Service.ProjectService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "project")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @Autowired
    private JasperExportService exportService;
    @Autowired
    private ProjectRepository repository;

    @GetMapping
    public List<ProjectDto> getAll(){
        return service.getAllData();
    }

    @PostMapping
    public ProjectDto addData(@RequestBody ProjectDto projectDto){
        return service.saveData(projectDto);
    }

    @GetMapping("/{id}")
    public ProjectDto getByID(@PathVariable("id") Long id){
        return service.getById(id);
    }

    @PostMapping("/member/{UserId}")
    public List<ProjectDto> getAllByMember(@PathVariable("UserId") Long id){
        return service.getAllDataByMember(id);
    }

    @PostMapping("/startDate")
    public List<ProjectDto> getByDateStart(@RequestBody SearchDateStart searchDateStart){
        return service.getByDateStart(searchDateStart.getStartDate(), searchDateStart.getEndDate());
    }

    @PostMapping("/endDate")
    public List<ProjectDto> getByDateEnd(@RequestBody SearchDateStart searchDateStart){
        return service.getByDateEnd(searchDateStart.getStartDate(), searchDateStart.getEndDate());
    }

    @PostMapping("/customDate")
    public List<ProjectDto> getByDateCustom(@RequestBody SearchDateStart searchDateStart){
        return service.getByDateCustom(searchDateStart.getStartDate(), searchDateStart.getEndDate());
    }

    @PutMapping()
    public ProjectDto putProject(@RequestBody ProjectDto projectDto){
        return service.saveData(projectDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable("id") Long id){
        service.deleteData(id);
    }

    @GetMapping("/report/{id}")
    public String reportExport(@PathVariable("id") Long id) throws JRException, FileNotFoundException {
        String format = "pdf";
        return exportService.ExportReportProject(format, id);
    }
//    @GetMapping(value = "/report/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
//    public ResponseEntity<byte[]> downloadInvoice( @PathVariable("id") Long id) throws JRException, FileNotFoundException {
//
//        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
//                List.of(repository.findById(id).orElse(new Project()))
//                , false);
//
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("total", "7000");
//
//        JasperReport compileReport = JasperCompileManager
//                .compileReport(new FileInputStream("src/main/resources/ProjectOwner.jrxml"));
//
//        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameters, beanCollectionDataSource);
//
//        // JasperExportManager.exportReportToPdfFile(jasperPrint,
//        // System.currentTimeMillis() + ".pdf");
//
//        byte data[] = JasperExportManager.exportReportToPdf(jasperPrint);
//
//        System.err.println(data);
//
//        HttpHeaders headers = new HttpHeaders();
//
//        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");
//
//        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
//    }

}
