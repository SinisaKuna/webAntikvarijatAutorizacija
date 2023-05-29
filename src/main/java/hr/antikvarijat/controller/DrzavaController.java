package hr.antikvarijat.controller;

import hr.antikvarijat.exception.DrzavaNotFoundException;
import hr.antikvarijat.model.Drzava;
import hr.antikvarijat.servis.Kolona;
import hr.antikvarijat.repository.DrzavaRepository;
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
@RequestMapping("/drzave")
public class DrzavaController {

    private final DrzavaRepository drzavaRepository;

    @Autowired
    public DrzavaController(DrzavaRepository drzavaRepository) {
        this.drzavaRepository = drzavaRepository;
    }
    @Autowired
    private DrzavaService drzavaService;


    @GetMapping("")
    public String showDrzaveList(Model model) {

        List<Kolona> listeKolona = new ArrayList<>();

        listeKolona.add(new Kolona("ID","idDrzava","idDrzava"));
        listeKolona.add(new Kolona("Naziv države","nazivDrzave","idDrzava"));


        List<Drzava> listDrzave = drzavaService.getAllDrzave();
        model.addAttribute("listaPodataka", listDrzave);

        model.addAttribute("naslov", "Popis država");
        model.addAttribute("dodajLink", "/drzave/new" );
        model.addAttribute("urediLink", "/drzave/edit/{id}");
        model.addAttribute("obrisiLink", "/drzave/delete/{id}");
        model.addAttribute("listeKolona", listeKolona);

        return "tablica";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        List<Podatak> sviPodaci = new ArrayList<>();
        sviPodaci.add(new Podatak("Naziv države:", "nazivDrzave","", "",""));;

        model.addAttribute("klasa", new Drzava());
        model.addAttribute("listaPodataka", sviPodaci);
        model.addAttribute("naslov", "Država");
        model.addAttribute("idPoljePodatka", "idDrzava");
        model.addAttribute("nazivGumba", "Spremi");
        model.addAttribute("stranica", "/drzave");

        return "forma";
    }

    @PostMapping("/save")
    public String addDrzava(@ModelAttribute("drzava") Drzava drzava) {
        drzavaService.saveDrzava(drzava);
        return "redirect:/drzave";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int idDrzava, Model model, RedirectAttributes ra) {
        try {

            List<Podatak> sviPodaci = new ArrayList<>();
            sviPodaci.add(new Podatak("Naziv države:", "nazivDrzave","", "",""));;
            Drzava drzava = drzavaService.getDrzavaById(idDrzava);
            model.addAttribute("klasa", drzava);
            model.addAttribute("listaPodataka", sviPodaci);
            model.addAttribute("naslov", "Država");
            model.addAttribute("idPoljePodatka", "idDrzava");
            model.addAttribute("nazivGumba", "Spremi");
            model.addAttribute("stranica", "/drzave");
            return "forma";
        } catch (DrzavaNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/drzave";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteDrzava(@PathVariable int id, RedirectAttributes ra) {
        try {
            if (drzavaService.hasGrad(id) || drzavaService.hasAutor(id) ) {
                ra.addFlashAttribute("message", "Nemoguće izbrisati državu jer postoje povezani zapisi u drugim tablicama.");
            } else {
                drzavaService.deleteDrzava(id);
            }
        } catch (DrzavaNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/drzave";
    }


}
