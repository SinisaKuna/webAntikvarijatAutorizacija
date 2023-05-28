package hr.antikvarijat.controller;

import hr.antikvarijat.exception.NacinPlacanjaNotFoundException;
import hr.antikvarijat.servis.Kolona;
import hr.antikvarijat.model.NacinPlacanja;
import hr.antikvarijat.service.NacinPlacanjaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/nacini_placanja")
public class NacinPlacanjaController {

    private final NacinPlacanjaService nacinPlacanjaService;

    @Autowired
    public NacinPlacanjaController(NacinPlacanjaService nacinPlacanjaService) {
        this.nacinPlacanjaService = nacinPlacanjaService;
    }

    @GetMapping("")
    public String showNacinPlacanjaList(Model model) {

        /*
            <td th:text="${nacinPlacanja.idNacinPlacanja}"></td>
            <td th:text="${nacinPlacanja.nazivNacinaPlacanja}"></td>
            <td th:text="${nacinPlacanja.oznakaNacinaPlacanja}"></td>
         */
        List<Kolona> listeKolona = new ArrayList<>();

        listeKolona.add(new Kolona("ID","idNacinPlacanja","idNacinPlacanja"));
        listeKolona.add(new Kolona("Naziv načina plaćanja","nazivNacinaPlacanja","idNacinPlacanja"));
        listeKolona.add(new Kolona("Oznaka načina plaćanja","oznakaNacinaPlacanja","idNacinPlacanja"));


        List<NacinPlacanja> listNacinPlacanja = nacinPlacanjaService.getAllNacinPlacanja();
        model.addAttribute("listaPodataka", listNacinPlacanja);

        model.addAttribute("naslov", "Popis načina plaćanja");
        model.addAttribute("dodajLink", "/nacini_placanja/new" );
        model.addAttribute("urediLink", "/nacini_placanja/edit/{id}");
        model.addAttribute("obrisiLink", "/nacini_placanja/delete/{id}");
        model.addAttribute("listeKolona", listeKolona);

        return "tablica";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("nacinPlacanja", new NacinPlacanja());
        return "nacin_placanja_form";
    }

    @PostMapping("/save")
    public String addNacinPlacanja(@ModelAttribute("nacinPlacanja") NacinPlacanja nacinPlacanja) {
        nacinPlacanjaService.createNacinPlacanja(nacinPlacanja);
        return "redirect:/nacini_placanja";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int idNacinPlacanja, Model model, RedirectAttributes ra) {
        try {
            NacinPlacanja nacinPlacanja = nacinPlacanjaService.getNacinPlacanjaById(idNacinPlacanja);
            model.addAttribute("nacinPlacanja", nacinPlacanja);
            return "nacin_placanja_form";
        } catch (NacinPlacanjaNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/nacini_placanja";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteNacinPlacanja(@PathVariable int id, RedirectAttributes ra) {
        try {
            // Add your custom logic here for checking related records in other tables
            nacinPlacanjaService.deleteNacinPlacanja(id);
        } catch (NacinPlacanjaNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/nacini_placanja";
    }
}
