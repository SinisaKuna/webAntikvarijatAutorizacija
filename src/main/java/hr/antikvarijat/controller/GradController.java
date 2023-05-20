package hr.antikvarijat.controller;
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

    @GetMapping("")
    public String showGradoviList(Model model) {
        List<Grad> listGradovi = gradService.getAllGradovi();
        model.addAttribute("listGradovi", listGradovi);
        return "gradovi";
    }

    //    @GetMapping("/new")
//    public String showNewForm(Model model) {
//        model.addAttribute("grad", new Grad());
//        model.addAttribute("pageTitle", "Dodaj novi grad");
//        return "grad_form";
//    }
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

    @PostMapping("/body/save")
    public String dodajGrad(@RequestBody Grad grad) {
        Drzava drzava = drzavaService.getDrzavaById(grad.getDrzava().getIdDrzava());
        grad.setDrzava(drzava);
        gradService.saveGrad(grad);
        return "redirect:/gradovi";
    }
    @PostMapping("/save")
    public String addGrad(@ModelAttribute("grad") Grad grad) {
        Drzava drzava = drzavaService.getDrzavaById(grad.getDrzava().getIdDrzava());
        grad.setDrzava(drzava);
        gradService.saveGrad(grad);
        return "redirect:/gradovi";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int idGrad, Model model, RedirectAttributes ra) {
        try {
            List<Drzava> listaDrzava = drzavaService.getAllDrzave();
            Grad grad = gradService.getGradById(idGrad);
            model.addAttribute("grad", grad);
            model.addAttribute("drzave", listaDrzava);
            model.addAttribute("pageTitle", "Uređivanje grada (ID: " + idGrad + ")");
            return "grad_form";
        } catch (GradNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/gradovi";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteGrad(@PathVariable int id) {
        gradService.deleteGrad(id);
        return "redirect:/gradovi";
    }
}
