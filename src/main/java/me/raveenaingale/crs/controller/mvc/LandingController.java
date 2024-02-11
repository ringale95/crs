package me.raveenaingale.crs.controller.mvc;

import jakarta.servlet.http.HttpSession;
import me.raveenaingale.crs.dao.ReportDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/crs")
public class LandingController {

    @Autowired
    private ReportDAO reportDAO;

    @GetMapping
    public ModelAndView homepage(HttpSession session) throws Exception {
        Object userLoggedIn = session.getAttribute("loggedInUser");
        if(userLoggedIn != null) {
            Map<String, Object> model = new HashMap<>();
            model.put("results", reportDAO.list());
            return new ModelAndView("homepage",model);
        }
        else
            return new ModelAndView("redirect:/crs/login");
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> showReport(@PathVariable int id) {
        String reportFileName = "test-report-"+id+".html";//"test-report-" + id + ".html";
        String reportsDirectory = "static/reports"; // Relative to the classpath

        try {
            ClassPathResource resource = new ClassPathResource(reportsDirectory + "/" + reportFileName);

            if (resource.exists()) {
                byte[] data = new byte[(int) resource.contentLength()];
                try {
                    if (resource.getInputStream().read(data) > 0) {
                        String htmlContent = new String(data, StandardCharsets.UTF_8);
                        return ResponseEntity.ok(htmlContent);
                    }
                } finally {
                    resource.getInputStream().close();
                }
            }

            // Report not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Report not found for id: " + id);
        } catch (IOException e) {
            // Handle exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading report");
        }
    }
}
