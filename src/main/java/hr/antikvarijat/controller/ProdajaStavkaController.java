package hr.antikvarijat.controller;

import hr.antikvarijat.exception.ProdajaStavkaNotFoundException;
import hr.antikvarijat.model.Knjiga;
import hr.antikvarijat.model.Partner;
import hr.antikvarijat.model.ProdajaStavka;
import hr.antikvarijat.repository.KnjigaRepository;
import hr.antikvarijat.service.KnjigaService;
import hr.antikvarijat.service.ProdajaStavkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/prodaja_stavke")
public class ProdajaStavkaController {
    private final ProdajaStavkaService prodajaStavkaService;

    private final KnjigaService knjigaService;
    private final KnjigaRepository knjigaRepository;

    @Autowired
    public ProdajaStavkaController(ProdajaStavkaService prodajaStavkaService, KnjigaService knjigaService,
                                   KnjigaRepository knjigaRepository) {
        this.prodajaStavkaService = prodajaStavkaService;
        this.knjigaService = knjigaService;
        this.knjigaRepository = knjigaRepository;
    }

    @GetMapping("")
    public String showProdajaStavkeList(Model model) {
        List<Knjiga> listaKnjiga = new ArrayList<>();
        Knjiga praznaKnjiga = new Knjiga(); // Kreiranje praznog objekta klase Knjiga
        listaKnjiga.add(praznaKnjiga); // Dodavanje prazne knjige u listu

        List<Knjiga> popunjenaListaKnjiga = knjigaService.getSortedKnjiga();
        listaKnjiga.addAll(popunjenaListaKnjiga);

        ProdajaStavka prodajaStavka = new ProdajaStavka();
        prodajaStavka.setKolicina(1);
        List<ProdajaStavka> listaProdajaStavki = prodajaStavkaService.getAllProdajaStavke();
        model.addAttribute("listaProdajaStavki", listaProdajaStavki);
        model.addAttribute("prodajaStavka", prodajaStavka);
        model.addAttribute("knjiga", listaKnjiga);
        return "prodaja_stavke";
    }

    @GetMapping("/new")
    public String showProdajaStavkaForm(Model model) {

        List<Knjiga> listaKnjiga = knjigaService.getSortedKnjiga();
        List<ProdajaStavka> listaProdajaStavki = prodajaStavkaService.getAllProdajaStavke();
        ProdajaStavka prodajaStavka = new ProdajaStavka();
        prodajaStavka.setKolicina(1);
        // Postavite potrebne atribute modela prema vašim potrebama
        model.addAttribute("listaProdajaStavki", listaProdajaStavki);
        model.addAttribute("prodajaStavka", prodajaStavka);
        model.addAttribute("knjiga", listaKnjiga);
        return "prodaja_forma";
    }

    @GetMapping("/{id}")
    public String showProdajaStavkaDetails(@PathVariable("id") int id, Model model) {
        try {
            ProdajaStavka prodajaStavka = prodajaStavkaService.getProdajaStavkaById(id);
            model.addAttribute("prodajaStavka", prodajaStavka);
            return "prodaja_forma";
        } catch (ProdajaStavkaNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "prodaja_stavke";
        }
    }



    @PostMapping("/save")
    public String addProdajaStavka(@ModelAttribute("prodajaStavka") ProdajaStavka prodajaStavka) {
        prodajaStavkaService.saveProdajaStavka(prodajaStavka);
        return "redirect:/prodaja_stavke";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        try {
            List<Knjiga> listaKnjiga = knjigaService.getSortedKnjiga();
            ProdajaStavka prodajaStavka = prodajaStavkaService.getProdajaStavkaById(id);
            // Postavite potrebne atribute modela prema vašim potrebama
            model.addAttribute("prodajaStavka", prodajaStavka);
            model.addAttribute("knjiga", listaKnjiga);
            return "prodaja_forma";
        } catch (ProdajaStavkaNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "prodaja_stavke";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateProdajaStavka(@PathVariable("id") int id, @ModelAttribute("prodajaStavka") ProdajaStavka prodajaStavka) {
        prodajaStavka.setIdProdajaStavka(id);
        prodajaStavkaService.saveProdajaStavka(prodajaStavka);
        return "redirect:/prodaja_stavke";
    }

    @GetMapping("/delete/{id}")
    public String deleteProdajaStavka(@PathVariable("id") int id) {
        prodajaStavkaService.deleteProdajaStavka(id);
        return "redirect:/prodaja_stavke";
    }
}

