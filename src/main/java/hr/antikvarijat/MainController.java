package hr.antikvarijat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    @GetMapping("/")
    public String showHomePage() {
        return "antikvarijat";
    }

    @GetMapping("/otkup")
    public String showNoOtkup() { return "redirect:antikvarijat"; }

    @GetMapping("/rezervacija")
    public String showNoRezervacija() { return "redirect:antikvarijat"; }

    @GetMapping("/operateri")
    public String showUsers() {
        return "redirect:/users";
    }

    @GetMapping("/odjava")
    public String showOdjava() {
        return "redirect:/logout";
    }

    @GetMapping("/antikvarijat")
    public String antikvarijat(){ return "antikvarijat"; }

}