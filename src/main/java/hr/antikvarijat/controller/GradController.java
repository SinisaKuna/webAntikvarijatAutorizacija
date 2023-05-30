package hr.antikvarijat.controller;
import hr.antikvarijat.exception.DrzavaNotFoundException;
import hr.antikvarijat.model.Drzava;
import hr.antikvarijat.servis.Kolona;
import hr.antikvarijat.service.DrzavaService;
import hr.antikvarijat.servis.Podatak;
import org.springframework.stereotype.Controller;

import hr.antikvarijat.exception.GradNotFoundException;
import hr.antikvarijat.model.Grad;
import hr.antikvarijat.service.GradService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/gradovi")
public class GradController {

    private final GradService gradService;
    private final DrzavaService drzavaService;

    @Autowired
    public GradController(GradService gradService, DrzavaService drzavaService) {
        this.gradService = gradService;
        this.drzavaService = drzavaService;
    }

    // # prikaz tablice
    @GetMapping("")
    public String showGradoviList(Model model) {

        List<Kolona> listeKolona = new ArrayList<>();
        listeKolona.add(new Kolona("ID","idGrad","idGrad"));
        listeKolona.add(new Kolona("Naziv grada","nazivGrada","idGrad"));
        listeKolona.add(new Kolona("Poštanski broj","postanskiBroj","idGrad"));
        listeKolona.add(new Kolona("Naziv države","nazivDrzave","idGrad"));

        List<Grad> listGradovi = gradService.getSortedGrad();
        model.addAttribute("listaPodataka", listGradovi);

        model.addAttribute("naslov", "Popis gradova");
        model.addAttribute("dodajLink", "/gradovi/new" );
        model.addAttribute("urediLink", "/gradovi/edit/{id}");
        model.addAttribute("obrisiLink", "/gradovi/delete/{id}");
        model.addAttribute("listeKolona", listeKolona);

        return "tablica";
    }

    // # brisanje zapisa u tablici .html #
    @GetMapping("/delete/{id}")
    public String deleteGrad(@PathVariable int id, RedirectAttributes ra) {
        try {
            if (gradService.hasIzdavac(id) || gradService.hasPartner(id) ) {
                ra.addFlashAttribute("message", "Nemoguće izbrisati grad jer postoje povezani zapisi u drugim tablicama.");
            } else {
                gradService.deleteGrad(id);
            }
        } catch (DrzavaNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/gradovi";
    }

    // # spremanje podatka
    @PostMapping("/save")
    public String addGrad(@ModelAttribute("grad") Grad grad) {
        Drzava drzava = drzavaService.getDrzavaById(grad.getDrzava().getIdDrzava());
        grad.setDrzava(drzava);
        gradService.saveGrad(grad);
        return "redirect:/gradovi";
    }



    // # forma za upis novog podatka
    @GetMapping("/new")
    public String showForm(Model model) {

        List<Podatak> sviPodaci = new ArrayList<>();
        sviPodaci.add(new Podatak("Poštanski broj:", "postanskiBroj","", "",""));;
        sviPodaci.add(new Podatak("Naziv grada:", "nazivGrada", "","",""  ));
        sviPodaci.add(new Podatak("Država:", "idDrzava", "tmpDrzava","drzava.idDrzava","nazivDrzave" ));


        Drzava drzava = new Drzava();
        List<Drzava> listaDrzava = drzavaService.getSortedDrzave();

        Grad grad = new Grad();


        // Set other necessary attributes if needed
        model.addAttribute("klasa", grad);
        model.addAttribute("tmpDrzava", listaDrzava);

        model.addAttribute("listaPodataka", sviPodaci);
        model.addAttribute("naslov", "Grad");
        model.addAttribute("idPoljePodatka", "idGrad");
        model.addAttribute("nazivGumba", "Spremi");
        model.addAttribute("stranica", "/gradovi");
        return "forma";
    }


    // # forma za promjenu podatka
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int idGrad, Model model, RedirectAttributes ra) {
        try {

            List<Podatak> sviPodaci = new ArrayList<>();
            sviPodaci.add(new Podatak("Poštanski broj:", "postanskiBroj","", "",""));;
            sviPodaci.add(new Podatak("Naziv grada:", "nazivGrada", "","",""  ));
            sviPodaci.add(new Podatak("Država:", "idDrzava", "tmpDrzava","drzava.idDrzava","nazivDrzave" ));

            List<Drzava> listaDrzava = drzavaService.getAllDrzave();
            Grad grad = gradService.getGradById(idGrad);
            model.addAttribute("klasa", grad);
            model.addAttribute("tmpDrzava", listaDrzava);

            model.addAttribute("listaPodataka", sviPodaci);
            model.addAttribute("naslov", "Grad");
            model.addAttribute("idPoljePodatka", "idGrad");
            model.addAttribute("nazivGumba", "Ažuriraj");
            model.addAttribute("stranica", "/gradovi");

            return "forma";
        } catch (GradNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/gradovi";
        }
    }


}
