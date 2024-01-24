package com.PlanYourHolidays.registration;

import com.PlanYourHolidays.customer.Customer;
import com.PlanYourHolidays.customer.CustomerDto;
import com.PlanYourHolidays.customer.CustomerRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

@RestController
@RequestMapping(path="customer")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowedHeaders = "*", allowCredentials = "true")
public class RegistrationRestController {

    @Autowired
    private CustomerRepository customerRepository;

    // Rejestracja nowego użytkownika
    // Zwraca liczbę (int), która informuje o wyniku operacji
    // 0 - Użytkownik pomyślnie zarejestrowany w bazie danych
    // 1 - Użytkownik o podanym adresie email juz istnieje
    // 2 - Walidacja danych nie przeszła pomyślnie (hasła nie są takie same lub format maila jest zły)
    @PostMapping("/register")
    public int registerRestController(@ModelAttribute("customer") @Valid CustomerDto customerDto,
                                      BindingResult result) {

        // Walidacja się nie powiodła, należy obsłużyć błędy
        if (result.hasErrors()) {
            return 2;
        }

        // Użytkownik już istnieje w bazie danych
        if (customerRepository.findByEmail(customerDto.getEmail()) != null){
            return 1;
        }

        // Walidacja przeszła pomyślnie, można zapisać użytkownika
        Customer customer = customerDto.convertToCustomer();
        customerRepository.save(customer);
        return 0;
    }

    // Logowanie użytkownika
    // Zwraca liczbę (int), która informuje o wyniku operacji
    // 0 - Użytkownik zalogowany pomyślnie
    // 1 - Błąd, mail nie istnieje w bazie lub hasło nie jest poprawne
    @PostMapping("/login")
    public ResponseEntity<Object> loginRestController(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, Model model) {
        Customer customer = customerRepository.findByEmail(email);

        if (customer != null && customer.getPassword().equals(password)) {
            session.setAttribute("username", customer.getEmail());
            // Przekierowanie na wybrany adres (np. /home)
            URI location = URI.create("http://localhost:3000/user");
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(location);
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        } else {
            model.addAttribute("error", "Invalid email or password");
            URI location = URI.create("http://localhost:3000/log-in");
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(location);
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }
    }

    // Wylogowywanie użytkownika
    // 0 - Wylogowano pomyślnie

    @GetMapping("/logout")
    public ModelAndView logoutRestController(HttpSession session) {
        session.removeAttribute("username");
        return new ModelAndView("redirect:http://localhost:3000");
    }

    // Sprawdzenie aktywności sesji
    // Zwraca True jeśli sesja jest aktywna, oraz False jeśli jest nieaktywna
    @GetMapping("/session")
    public String sessionRestController(HttpSession session) {

        if(session.getAttribute("username") != null){
            return "1";
        }else {
            return "0";
        }
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
