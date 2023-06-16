package hr.antikvarijat.controller;

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

import static hr.antikvarijat.servis.IMDbScraper.findMovieWithTitle;
import static hr.antikvarijat.util.PickboxScraper.UpisiProgram;

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
    public String puniPickboxTable(Model model, RedirectAttributes ra) throws IOException {
        pickboxRepository.deleteAll();
        UpisiProgram(pickboxService);
        ra.addFlashAttribute("message", "Podaci su obnovljeni...");
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
    public String imdbPickboxTable(@PathVariable("id") int idPickBox, Model model, RedirectAttributes ra) throws IOException {
        Optional<Pickbox> pickbox = pickboxService.getPickboxById(idPickBox);
        String naslov = pickbox.get().getNaziv();
        String input = naslov;
        String[] parts = input.split("\\|"); // Razdvajanje stringa na temelju znaka "|"
        String result = parts[0].trim(); // Dobivanje prvog dijela i uklanjanje nepotrebnih razmaka
        String prijevod = pickbox.get().getPrijevod();
        String imdbUrl = "https://www.imdb.com/";
        String searchUrl = imdbUrl + "find?q=" + naslov.replace(" ", "+") + "&s=tt&exact=true&ref_=fn_tt_ex";
        String stranica = "";
        String ttImdb = findMovieWithTitle(result);
        if (ttImdb == null){
            searchUrl = imdbUrl + "find?q=" + prijevod.replace(" ", "+") + "&s=tt&exact=true&ref_=fn_tt_ex";
            ttImdb = findMovieWithTitle(result);
            if (ttImdb == null) {
                stranica = "error404";
                ra.addFlashAttribute("message", "Nisam u mogućnosti pronaći Imdb stranicu za naslov " + naslov);
            } else {
                String imdbLink = "https://www.imdb.com/title/"+ ttImdb+ "/";
                stranica = "redirect:" + imdbLink;
            }
        } else {
            String imdbLink = "https://www.imdb.com/title/"+ ttImdb+ "/";
            stranica = "redirect:" + imdbLink;
        }
        return stranica;
    }
}
