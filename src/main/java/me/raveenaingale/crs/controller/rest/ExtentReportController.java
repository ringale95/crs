package me.raveenaingale.crs.controller.rest;

import me.raveenaingale.crs.dao.ReportDAO;
import me.raveenaingale.crs.models.Report;
import me.raveenaingale.crs.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api/reports")
public class ExtentReportController {

    private final ReportDAO reportDAO;

    @Autowired
    public ExtentReportController(ReportDAO reportDAO){
        this.reportDAO = reportDAO;
    }


    @PostMapping
    @ResponseBody
    public ResponseEntity<String> addReport(
            @RequestParam("file") MultipartFile file,
            @RequestParam("commit") String commit,
            @RequestParam("author") String author,
            @RequestParam("status") String status,
            @RequestParam("title") String title,
            @RequestParam("createdAt") String createdAt
    ) {
        try {
            // Convert createdAt String to Date
            Date createdAtDate = new SimpleDateFormat("MM/dd/yyyy").parse(createdAt);

            // Save the report and additional data
            Report report = new Report();
            report.setCommit(commit);
            report.setAuthor(author);
            report.setStatus("PASSED".equals(status.toUpperCase()));
            report.setPrTitle(title);
            report.setCreatedAt(createdAtDate);

            // You can now save the report and additional data using your service
            Report savedReport = reportDAO.create(report);

            // Handle the uploaded file as needed
            // Save the uploaded file
            String fileName = "test-report-" + savedReport.getId() + ".html";
            String uploadDir = "src/main/resources/static/reports";

            Path uploadPath = Path.of(uploadDir);
            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Set the file path in the report object
            report.setExtentReportLocation(filePath.toString());

            return ResponseEntity.ok("Report and additional data received successfully.");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to process the request.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping
    @ResponseBody
    public List<Report> getAllUsers(){
        try {
            return reportDAO.list();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/{id}")
    @ResponseBody
    public Report getReportByID(@PathVariable long id){
        try {
            return reportDAO.getReportByID(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
