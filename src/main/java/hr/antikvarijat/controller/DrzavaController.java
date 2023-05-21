package hr.antikvarijat.controller;

import hr.antikvarijat.exception.DrzavaNotFoundException;
import hr.antikvarijat.model.Drzava;
import hr.antikvarijat.repository.DrzavaRepository;
import hr.antikvarijat.service.DrzavaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        List<Drzava> listDrzave = drzavaService.getAllDrzave();
        model.addAttribute("listDrzave", listDrzave);
        return "drzave";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("drzava", new Drzava());
        return "drzava_form";
    }

    @PostMapping("/save")
    public String addDrzava(@ModelAttribute("drzava") Drzava drzava) {
        drzavaService.saveDrzava(drzava);
        return "redirect:/drzave";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int idDrzava, Model model, RedirectAttributes ra) {
        try {
            Drzava drzava = drzavaService.getDrzavaById(idDrzava);
            model.addAttribute("drzava", drzava);
            return "drzava_form";
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
