package hr.antikvarijat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    @GetMapping("")
    public String showHomePage() {
        return "index";
    }
    @GetMapping("/profil")
    public String showProfil() {
        return "index";
    }

    @GetMapping("/odjava")
    public String showOdjava() {
        return "index";
    }

    @GetMapping("/antikvarijat")
    public String antikvarijat(){ return "antikvarijat"; }

}