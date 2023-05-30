package hr.antikvarijat.controller;

import hr.antikvarijat.exception.NacinPlacanjaNotFoundException;
import hr.antikvarijat.servis.Kolona;
import hr.antikvarijat.model.NacinPlacanja;
import hr.antikvarijat.service.NacinPlacanjaService;
import hr.antikvarijat.servis.OznakaNacinaPlacanja;
import hr.antikvarijat.servis.Podatak;
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

        List<Podatak> sviPodaci = new ArrayList<>();
        sviPodaci.add(new Podatak("Naziv načina plaćanja:", "nazivNacinaPlacanja", "", "", ""));
        sviPodaci.add(new Podatak("Oznaka:", "oznakaNacinaPlacanja", "tmpOznakaNacinaPlacanja", "oznakaNacinaPlacanja", "opis"));

        List<OznakaNacinaPlacanja> tmpNacinPlacanja = new ArrayList<>();
        tmpNacinPlacanja.add(new OznakaNacinaPlacanja("G","Gotovina"));
        tmpNacinPlacanja.add(new OznakaNacinaPlacanja("K","Kartica"));
        tmpNacinPlacanja.add(new OznakaNacinaPlacanja("C","Ček tekućeg računa"));
        tmpNacinPlacanja.add(new OznakaNacinaPlacanja("O","Ostalo"));

        model.addAttribute("klasa", new NacinPlacanja());
        model.addAttribute("tmpOznakaNacinaPlacanja", tmpNacinPlacanja);


        model.addAttribute("listaPodataka", sviPodaci);
        model.addAttribute("naslov", "Način plaćanja");
        model.addAttribute("idPoljePodatka", "idNacinPlacanja");
        model.addAttribute("nazivGumba", "Spremi");
        model.addAttribute("stranica", "/nacini_placanja");


        return "forma";
    }

    @PostMapping("/save")
    public String addNacinPlacanja(@ModelAttribute("nacinPlacanja") NacinPlacanja nacinPlacanja) {
        nacinPlacanjaService.createNacinPlacanja(nacinPlacanja);
        return "redirect:/nacini_placanja";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int idNacinPlacanja, Model model, RedirectAttributes ra) {
        try {

            List<Podatak> sviPodaci = new ArrayList<>();
            sviPodaci.add(new Podatak("Naziv načina plaćanja:", "nazivNacinaPlacanja", "", "", ""));
            sviPodaci.add(new Podatak("Oznaka:", "oznakaNacinaPlacanja", "tmpOznakaNacinaPlacanja", "oznakaNacinaPlacanja", "opis"));

            List<OznakaNacinaPlacanja> tmpNacinPlacanja = new ArrayList<>();
            tmpNacinPlacanja.add(new OznakaNacinaPlacanja("G","Gotovina"));
            tmpNacinPlacanja.add(new OznakaNacinaPlacanja("K","Kartica"));
            tmpNacinPlacanja.add(new OznakaNacinaPlacanja("C","Ček tekućeg računa"));
            tmpNacinPlacanja.add(new OznakaNacinaPlacanja("O","Ostalo"));


            NacinPlacanja nacinPlacanja = nacinPlacanjaService.getNacinPlacanjaById(idNacinPlacanja);
            model.addAttribute("klasa", nacinPlacanja);
            model.addAttribute("tmpOznakaNacinaPlacanja", tmpNacinPlacanja);

            model.addAttribute("listaPodataka", sviPodaci);
            model.addAttribute("naslov", "Način plaćanja");
            model.addAttribute("idPoljePodatka", "idNacinPlacanja");
            model.addAttribute("nazivGumba", "Ažuriraj");
            model.addAttribute("stranica", "/nacini_placanja");

            return "forma";

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
