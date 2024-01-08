package com.PlanYourHolidays.registration;

import com.PlanYourHolidays.customer.Customer;
import com.PlanYourHolidays.customer.CustomerDto;
import com.PlanYourHolidays.customer.CustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/register")
    public String showRegistrationForm(Model model, HttpSession session) {
        if (session.getAttribute("username") != null) {
            return "redirect:/home";
        }
        model.addAttribute("customer", new CustomerDto());
        return "registration";
    }

    @PostMapping("/register")
    public ModelAndView registerUserAccount(@ModelAttribute("customer") @Valid CustomerDto customerDto,
                                            BindingResult result, HttpServletRequest request) {

        // Walidacja się nie powiodła, należy obsłużyć błędy
        if (result.hasErrors()) {
            return new ModelAndView("registration");
        }

        // Użytkownik już istnieje w bazie danych
        Customer customer = convertToCustomer(customerDto);
        if (customerRepository.findByEmail(customer.getEmail()) != null){
            return new ModelAndView("registration");
        }

        // Walidacja przeszła pomyślnie, można zapisać użytkownika
        customerRepository.save(customer);
        return new ModelAndView("redirect:/login");
    }

    // Konwersja z CustomerDto do Customer
    private Customer convertToCustomer(CustomerDto customerDto) {
        return new Customer(customerDto.getName(), customerDto.getEmail(), customerDto.getPassword());
    }
}

