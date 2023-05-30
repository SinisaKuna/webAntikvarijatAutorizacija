package hr.antikvarijat.controller;

import hr.antikvarijat.exception.AutorNotFoundException;
import hr.antikvarijat.exception.IzdavacNotFoundException;
import hr.antikvarijat.model.Grad;
import hr.antikvarijat.model.Izdavac;
import hr.antikvarijat.servis.Kolona;
import hr.antikvarijat.service.GradService;
import hr.antikvarijat.service.IzdavacService;
import hr.antikvarijat.servis.Podatak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/izdavaci")
public class IzdavacController {
    private final IzdavacService izdavacService;
    private final GradService gradService;

    @Autowired
    public IzdavacController(IzdavacService izdavacService, GradService gradService) {
        this.izdavacService = izdavacService;
        this.gradService = gradService;
    }

    @GetMapping("")
    public String showIzdavaciList(Model model) {

        List<Kolona> listeKolona = new ArrayList<>();

        listeKolona.add(new Kolona("ID","idIzdavac","idIzdavac"));
        listeKolona.add(new Kolona("Naziv izdavača","nazivIzdavaca","idIzdavac"));
        listeKolona.add(new Kolona("Naziv grada","nazivGrada","idIzdavac"));


        List<Izdavac> listIzdavaci = izdavacService.getSortedIzdavac();
        model.addAttribute("listaPodataka", listIzdavaci);


        model.addAttribute("naslov", "Popis izdavača");
        model.addAttribute("dodajLink", "/izdavaci/new" );
        model.addAttribute("urediLink", "/izdavaci/edit/{id}");
        model.addAttribute("obrisiLink", "/izdavaci/delete/{id}");
        model.addAttribute("listeKolona", listeKolona);



        return "tablica";
    }

    @GetMapping("/new")
    public String showForm(Model model) {

        List<Podatak> sviPodaci = new ArrayList<>();
        sviPodaci.add(new Podatak("Naziv izdavača:", "nazivIzdavaca","", "",""));;
        sviPodaci.add(new Podatak("Grad:", "idGrad", "tmpGrad","grad.idGrad","nazivGrada" ));

        Grad grad = new Grad();
        List<Grad> listaGradova = gradService.getSortedGrad();
        Izdavac izdavac = new Izdavac();
        model.addAttribute("klasa", izdavac);
        model.addAttribute("tmpGrad", listaGradova);

        model.addAttribute("listaPodataka", sviPodaci);
        model.addAttribute("naslov", "Partner");
        model.addAttribute("idPoljePodatka", "idIzdavac");
        model.addAttribute("nazivGumba", "Spremi");
        model.addAttribute("stranica", "/izdavaci");


        return "forma";
    }

    @PostMapping("/save")
    public String addIzdavac(@ModelAttribute("izdavac") Izdavac izdavac) {
        Grad grad = gradService.getGradById(izdavac.getGrad().getIdGrad());
        izdavac.setGrad(grad);
        izdavacService.saveIzdavac(izdavac);
        return "redirect:/izdavaci";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int idIzdavac, Model model, RedirectAttributes ra) {
        try {

            List<Podatak> sviPodaci = new ArrayList<>();
            sviPodaci.add(new Podatak("Naziv izdavača:", "nazivIzdavaca","", "",""));;
            sviPodaci.add(new Podatak("Grad:", "idGrad", "tmpGrad","grad.idGrad","nazivGrada" ));

            List<Grad> listaGradova = gradService.getSortedGrad();
            Izdavac izdavac = izdavacService.getIzdavacById(idIzdavac);
            model.addAttribute("klasa", izdavac);
            model.addAttribute("tmpGrad", listaGradova);

            model.addAttribute("listaPodataka", sviPodaci);
            model.addAttribute("naslov", "Partner");
            model.addAttribute("idPoljePodatka", "idIzdavac");
            model.addAttribute("nazivGumba", "Ažuriraj");
            model.addAttribute("stranica", "/izdavaci");

            return "forma";

        } catch (IzdavacNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/izdavaci";
        }
    }

//    @GetMapping("/delete/{id}")
//    public String deleteIzdavac(@PathVariable int id) {
//        izdavacService.deleteIzdavac(id);
//        return "redirect:/izdavaci";
//    }

    @GetMapping("/delete/{id}")
    public String deleteIzdavac(@PathVariable int id, RedirectAttributes ra) {
        try {
            if ( izdavacService.hasKnjiga(id) ) {
                ra.addFlashAttribute("message", "Nemoguće izbrisati izdavača jer postoje povezani zapisi u drugim tablicama.");
            } else {
                izdavacService.deleteIzdavac(id);
            }
        } catch (AutorNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/izdavaci";
    }
}
