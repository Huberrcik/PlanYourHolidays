package com.PlanYourHolidays.registration;

import com.PlanYourHolidays.customer.Customer;
import com.PlanYourHolidays.customer.CustomerDto;
import com.PlanYourHolidays.customer.CustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(path="customer")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowedHeaders = "*", allowCredentials = "true")
public class RegistrationRestController {

    @Autowired
    private CustomerRepository customerRepository;

    // Rejestracja nowego użytkownika
    // Zwraca String, który informuje o wyniku operacji
    @PostMapping("/register")
    public String registerRestController(@ModelAttribute("customer") @Valid CustomerDto customerDto,
                                      BindingResult result) {

        // Walidacja się nie powiodła, należy obsłużyć błędy
        if (result.hasErrors()) {
            return("Walidacja danych nie powiodła się.");
        }

        // Użytkownik już istnieje w bazie danych
        if (customerRepository.findByEmail(customerDto.getEmail()) != null){
            return("Uzytkownik o podanym mailu już istnieje.");
        }

        // Walidacja przeszła pomyślnie, można zapisać użytkownika
        Customer customer = customerDto.convertToCustomer();
        customerRepository.save(customer);
        return("Użytkownik został pomyślnie zarejestrowany.");
    }

    // Logowanie użytkownika
    // Zwraca String, który informuje o wyniku operacji
    @PostMapping("/login")
    public String loginRestController(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, Model model) {
        Customer customer = customerRepository.findByEmail(email);

        if (customer != null && customer.getPassword().equals(password)) {
            session.setAttribute("username", customer.getEmail());
            return "Pomyślnie zalogowano.";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "Błędne dane do logowania.";
        }
    }

    // Wylogowywanie użytkownika
    @GetMapping("/logout")
    public String logoutRestController(HttpSession session) {
        session.removeAttribute("username");
        return "Pomyślnie wylogowano.";
    }

    // Sprawdzenie aktywności sesji
    // Zwraca True jeśli sesja jest aktywna, oraz False jeśli jest nieaktywna
    @GetMapping("/session")
    public boolean sessionRestController(HttpSession session) {
        return(session.getAttribute("username") != null);
    }

    // Pobranie nazwy zalogowanego użytkownika
    // Zwraca String zawierający nazwę użytkownika
    @GetMapping("/info/name")
    public String infoNameRestController(HttpSession session) {
        if(session.getAttribute("username") != null) {
            Customer customer = customerRepository.findByEmail((String)session.getAttribute("username"));
            return customer.getName();
        } else {
            return null;
        }
    }

    // Pobranie emaila zalogowanego użytkownika
    // Zwraca String zawierający email użytkownika
    @GetMapping("/info/email")
    public String infoEmailRestController(HttpSession session) {
        if(session.getAttribute("username") != null) {
            Customer customer = customerRepository.findByEmail((String)session.getAttribute("username"));
            return customer.getEmail();
        } else {
            return null;
        }
    }

}