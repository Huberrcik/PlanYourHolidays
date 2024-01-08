package com.PlanYourHolidays.registration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
@SessionAttributes("username")
public class HomeController {

    @GetMapping("/home")
    public String showHomePage( Model model) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        if (session.getAttribute("username") != null) {
            String username = (String) session.getAttribute("username");
            model.addAttribute("username", username);
            return "home_logged_in";
        }
        return "home";
    }
}


