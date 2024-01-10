package com.PlanYourHolidays.registration;

import com.PlanYourHolidays.customer.Customer;
import com.PlanYourHolidays.customer.CustomerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class LoginController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/login")
    public String showLoginForm(HttpSession session) {
        if (session.getAttribute("username") != null) {
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginCustomer(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, Model model) {
        Customer customer = customerRepository.findByEmail(email);

        if (customer != null && customer.getPassword().equals(password)) {
            session.setAttribute("username", customer.getName());
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }
}

