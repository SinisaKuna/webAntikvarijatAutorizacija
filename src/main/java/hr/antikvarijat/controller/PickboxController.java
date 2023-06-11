package hr.antikvarijat.controller;

import hr.antikvarijat.model.Grad;
import hr.antikvarijat.model.Pickbox;
import hr.antikvarijat.repository.PickboxRepository;
import hr.antikvarijat.service.PickboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static hr.antikvarijat.servis.PickboxScraper.UpisiProgram;

@Controller
public class PickboxController {

    private final PickboxService pickboxService;
    @Autowired
    private PickboxRepository pickboxRepository; // Pretpostavljamo da imate implementiranu PickboxRepository klasu za pristup bazi podataka

    public PickboxController(PickboxService pickboxService, PickboxRepository pickboxRepository) {
        this.pickboxService = pickboxService;
        this.pickboxRepository = pickboxRepository;
    }

    @RequestMapping("/pickbox/puni")
    public String puniPickboxTable(Model model) throws IOException {
        pickboxRepository.deleteAll();
        UpisiProgram(pickboxService);
        return "redirect:/pickbox";
    }

    @RequestMapping("/pickbox")
    public String showPickboxTable(Model model) throws IOException {
        List<Pickbox> pickboxList = pickboxRepository.findAll();
        model.addAttribute("pickboxList", pickboxList);
        model.addAttribute("puniLink", "/pickbox/puni" );
        return "pickbox";
    }

    @RequestMapping("/pickbox/imdb/{id}")
    public String imdbPickboxTable(@PathVariable("id") int idPickBox, Model model, RedirectAttributes ra) {
        Optional<Pickbox> pickbox = pickboxService.getPickboxById(idPickBox);
        ra.addFlashAttribute("message", "Izrada prikaza IMDB stranice u tijeku...");
        return "redirect:/pickbox";
    }
}
