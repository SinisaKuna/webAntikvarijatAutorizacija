package hr.antikvarijat.controller;

import hr.antikvarijat.exception.ProdajaStavkaNotFoundException;
import hr.antikvarijat.model.ProdajaStavka;
import hr.antikvarijat.service.ProdajaStavkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/prodaja_stavke")
public class ProdajaStavkaController {
    private final ProdajaStavkaService prodajaStavkaService;

    @Autowired
    public ProdajaStavkaController(ProdajaStavkaService prodajaStavkaService) {
        this.prodajaStavkaService = prodajaStavkaService;
    }

    @GetMapping("")
    public String showProdajaStavkeList(Model model) {
        List<ProdajaStavka> listaProdajaStavki = prodajaStavkaService.getAllProdajaStavke();
        model.addAttribute("listaProdajaStavki", listaProdajaStavki);
        return "prodaja_stavke";
    }

    @GetMapping("/{id}")
    public String showProdajaStavkaDetails(@PathVariable("id") int id, Model model) {
        try {
            ProdajaStavka prodajaStavka = prodajaStavkaService.getProdajaStavkaById(id);
            model.addAttribute("prodajaStavka", prodajaStavka);
            return "prodaja_stavka_details";
        } catch (ProdajaStavkaNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "prodaja_stavka_not_found";
        }
    }

    @GetMapping("/new")
    public String showProdajaStavkaForm(Model model) {
        ProdajaStavka prodajaStavka = new ProdajaStavka();
        // Postavite potrebne atribute modela prema vašim potrebama
        model.addAttribute("prodajaStavka", prodajaStavka);
        return "prodaja_stavka_form";
    }

    @PostMapping("/new")
    public String addProdajaStavka(@ModelAttribute("prodajaStavka") ProdajaStavka prodajaStavka) {
        prodajaStavkaService.saveProdajaStavka(prodajaStavka);
        return "redirect:/prodaja_stavke";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        try {
            ProdajaStavka prodajaStavka = prodajaStavkaService.getProdajaStavkaById(id);
            // Postavite potrebne atribute modela prema vašim potrebama
            model.addAttribute("prodajaStavka", prodajaStavka);
            return "prodaja_stavka_form";
        } catch (ProdajaStavkaNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "prodaja_stavka_not_found";
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

