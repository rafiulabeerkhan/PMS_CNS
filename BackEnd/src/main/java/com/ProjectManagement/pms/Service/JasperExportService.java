package com.ProjectManagement.pms.Service;

import com.ProjectManagement.pms.DTO.ProjectDto;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JasperExportService {

    @Autowired
    private ProjectService service;



    public String ExportReport(String format) throws FileNotFoundException, JRException {
        List<ProjectDto> projectDtoList = service.getAllData();
        File file = ResourceUtils.getFile("classpath:ProjectOwner.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(projectDtoList);
        Map<String, Object> map = new HashMap<>();
        map.put("id", "id");
        JasperPrint print = JasperFillManager.fillReport(jasperReport,map,dataSource);
        if (format.equalsIgnoreCase("pdf")){
//            JasperExportManager.exportReportToPdfFile(print,"C:\\Users\\ZAHID\\Desktop\\jasper\\project-report.pdf");
            JasperExportManager.exportReportToPdfFile(print,"G:\\project\\PMS_CNS\\Jasper\\project-report.pdf");
        }
        return "Report Generated..";
    }

    public String ExportReportProject(String format, Long id) throws FileNotFoundException, JRException {
        List<ProjectDto> projectDtoList = service.getAllData(id);
        File file = ResourceUtils.getFile("classpath:ProjectOwner.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(projectDtoList);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        JasperPrint print = JasperFillManager.fillReport(jasperReport,map,dataSource);
        if (format.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(print,"G:\\project\\PMS_CNS\\Jasper\\project-report.pdf");
//            JasperExportManager.exportReportToPdfFile(print,"C:\\Users\\User\\Desktop\\report\\project-report.pdf");
        }
        return "Report Generated..";
    }
}