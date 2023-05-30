package hr.antikvarijat.controller;

import hr.antikvarijat.exception.AutorNotFoundException;
import hr.antikvarijat.model.Autor;
import hr.antikvarijat.model.Drzava;
import hr.antikvarijat.servis.Kolona;
import hr.antikvarijat.service.AutorService;
import hr.antikvarijat.service.DrzavaService;
import hr.antikvarijat.servis.Podatak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/autori")
public class AutorController {
    private final AutorService autorService;
    private final DrzavaService drzavaService;

    @Autowired
    public AutorController(AutorService autorService, DrzavaService drzavaService) {
        this.autorService = autorService;
        this.drzavaService = drzavaService;
    }

    @GetMapping("")
    public String showAutoriList(Model model) {


        List<Kolona> listeKolona = new ArrayList<>();

        listeKolona.add(new Kolona("ID", "idAutor", "idAutor"));
        listeKolona.add(new Kolona("Naziv autora", "nazivAutora", "idAutor"));
        listeKolona.add(new Kolona("Naziv države", "nazivDrzave", "idAutor"));

        List<Autor> listAutori = autorService.getSortedAutor();
        model.addAttribute("listaPodataka", listAutori);

        model.addAttribute("naslov", "Popis autora");
        model.addAttribute("dodajLink", "/autori/new");
        model.addAttribute("urediLink", "/autori/edit/{id}");
        model.addAttribute("obrisiLink", "/autori/delete/{id}");
        model.addAttribute("listeKolona", listeKolona);

        return "tablica";
    }

    @GetMapping("/new")
    public String showForm(Model model) {

        List<Podatak> sviPodaci = new ArrayList<>();
        sviPodaci.add(new Podatak("Naziv autora:", "nazivAutora", "", "", ""));
        sviPodaci.add(new Podatak("Država:", "idDrzava", "tmpDrzava", "drzava.idDrzava", "nazivDrzave"));

        Drzava drzava = new Drzava();
        List<Drzava> listaDrzava = drzavaService.getSortedDrzave();
        Autor autor = new Autor();
        model.addAttribute("klasa", autor);
        model.addAttribute("tmpDrzava", listaDrzava);


        model.addAttribute("listaPodataka", sviPodaci);
        model.addAttribute("naslov", "Autor");
        model.addAttribute("idPoljePodatka", "idAutor");
        model.addAttribute("nazivGumba", "Spremi");
        model.addAttribute("stranica", "/autori");

        return "forma";
    }

    @PostMapping("/save")
    public String addAutor(@ModelAttribute("autor") Autor autor) {
        Drzava drzava = drzavaService.getDrzavaById(autor.getDrzava().getIdDrzava());
        autor.setDrzava(drzava);
        autorService.saveAutor(autor);
        return "redirect:/autori";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int idAutor, Model model, RedirectAttributes ra) {
        try {

            List<Podatak> sviPodaci = new ArrayList<>();
            sviPodaci.add(new Podatak("Naziv autora:", "nazivAutora", "", "", ""));
            sviPodaci.add(new Podatak("Država:", "idDrzava", "tmpDrzava", "drzava.idDrzava", "nazivDrzave"));

            List<Drzava> listaDrzava = drzavaService.getAllDrzave();
            Autor autor = autorService.getAutorById(idAutor);
            model.addAttribute("klasa", autor);
            model.addAttribute("tmpDrzava", listaDrzava);


            model.addAttribute("listaPodataka", sviPodaci);
            model.addAttribute("naslov", "Autor");
            model.addAttribute("idPoljePodatka", "idAutor");
            model.addAttribute("nazivGumba", "Ažuriraj");
            model.addAttribute("stranica", "/autori");

            return "forma";

        } catch (AutorNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/autori";
        }
    }


    @GetMapping("/delete/{id}")
    public String deleteAutor(@PathVariable int id, RedirectAttributes ra) {
        try {
            if (autorService.hasKnjiga(id)) {
                ra.addFlashAttribute("message", "Nemoguće izbrisati autora jer postoje povezani zapisi u drugim tablicama.");
            } else {
                autorService.deleteAutor(id);
            }
        } catch (AutorNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/autori";
    }

}
