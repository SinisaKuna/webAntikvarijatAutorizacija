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




//    @GetMapping("/operateri")
//    public String showUsers() {
//        return "redirect:/users";
//    }

    @GetMapping("/odjava")
    public String showOdjava() {
        return "redirect:/logout";
    }

    @GetMapping("/antikvarijat")
    public String antikvarijat(){ return "antikvarijat"; }

    @GetMapping("/rezervacija")
    public String showNoRezervacija(RedirectAttributes ra) {
        ra.addFlashAttribute("message", "Izrada stranice u tijeku...");
        return "redirect:antikvarijat";
    }

    @GetMapping("/otkup")
    // ZA SADA U FAZI IZRADE PRODAJE
    public String showFazaIzradeProdaje() { return "redirect:prodaja_stavke"; }

}