package me.raveenaingale.crs.controller.mvc;

import jakarta.servlet.http.HttpSession;
import me.raveenaingale.crs.dao.UserDAO;
import me.raveenaingale.crs.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/crs")
public class AuthenticationController {

    private final UserDAO userDAO;
    @Autowired
    public AuthenticationController(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Boolean> onLogin(@RequestBody User user, HttpSession session) {
        if (userDAO.authenticateUser(user.getUsername(), user.getPassword())) {
            session.setAttribute("loggedInUser", user);
            return ResponseEntity.ok(true);
        }
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }

    @GetMapping("/login")
    public ModelAndView showLoginPage(@RequestParam(required = false) String status){
        Map<String, Object> model = new HashMap<>();
        model.put("status", status);
        return new ModelAndView("login",model );
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session){
        session.invalidate();
        return "logout";
    }
}
