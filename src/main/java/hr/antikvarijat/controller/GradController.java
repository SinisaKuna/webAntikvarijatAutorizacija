package hr.antikvarijat.controller;
import hr.antikvarijat.exception.DrzavaNotFoundException;
import hr.antikvarijat.model.Drzava;
import hr.antikvarijat.service.DrzavaService;
import org.springframework.stereotype.Controller;

import hr.antikvarijat.exception.GradNotFoundException;
import hr.antikvarijat.model.Grad;
import hr.antikvarijat.service.GradService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // # prikaz tablice .html #
    @GetMapping("")
    public String showGradoviList(Model model) {
        List<Grad> listGradovi = gradService.getAllGradovi();
        model.addAttribute("listGradovi", listGradovi);
        return "gradovi";
    }

    // # dodavanje novog zapisa u tablicu .html #
    @GetMapping("/new")
    public String showForm(Model model) {

        Drzava drzava = new Drzava();
        List<Drzava> listaDrzava = drzavaService.getAllDrzave();

        Grad grad = new Grad(); // Create a new Grad object

        // Set other necessary attributes if needed
        model.addAttribute("grad", grad);
        model.addAttribute("drzave", listaDrzava);
        return "grad_form";
    }

    // # spremanje novog zapisa u tablicu POSTMAN #
    @PostMapping("/body/save")
    public String dodajGrad(@RequestBody Grad grad) {
        Drzava drzava = drzavaService.getDrzavaById(grad.getDrzava().getIdDrzava());
        grad.setDrzava(drzava);
        gradService.saveGrad(grad);
        return "redirect:/gradovi";
    }

    // # spremanje novog zapisa u tablicu .html #
    @PostMapping("/save")
    public String addGrad(@ModelAttribute("grad") Grad grad) {
        Drzava drzava = drzavaService.getDrzavaById(grad.getDrzava().getIdDrzava());
        grad.setDrzava(drzava);
        gradService.saveGrad(grad);
        return "redirect:/gradovi";
    }

    // # editiranje zapisa u tablici .html #
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int idGrad, Model model, RedirectAttributes ra) {
        try {
            List<Drzava> listaDrzava = drzavaService.getAllDrzave();
            Grad grad = gradService.getGradById(idGrad);
            model.addAttribute("grad", grad);
            model.addAttribute("drzave", listaDrzava);
            return "grad_form";
        } catch (GradNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/gradovi";
        }
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
}
