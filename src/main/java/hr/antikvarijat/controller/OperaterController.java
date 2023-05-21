package hr.antikvarijat.controller;

import hr.antikvarijat.exception.OperaterNotFoundException;
import hr.antikvarijat.model.Operater;
import hr.antikvarijat.repository.OperaterRepository;
import hr.antikvarijat.service.OperaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

//@RestController
@Controller
@RequestMapping("/operateri")
public class OperaterController {

    private final OperaterRepository operaterRepository;
    @Autowired
    public OperaterController(OperaterRepository operaterRepository) {
        this.operaterRepository = operaterRepository;
    }

    @Autowired private OperaterService service;

    // DONE Endpoint za prikaz svih operatera
    @GetMapping("")
    public String showOperatoriList(Model model) {
        List<Operater> listOperateri = service.listAll();
        model.addAttribute("listOperateri", listOperateri);
        return "operateri";
    }

    // DONE Endpoint za dodavanje novog operatera
    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("operater", new Operater());
        return "operater_form";
    }
    @PostMapping("/save")
    public String addOperater(@ModelAttribute("operater") Operater operater) {
        service.save(operater);
        return "redirect:/operateri";
    }

    // DONE Endpoint za ažuriranje operatera
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer idOperater, Model model, RedirectAttributes ra) {
        try {
            Operater operater = service.get(idOperater);
            model.addAttribute("operater", operater);
            return "operater_form";
        } catch (OperaterNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/operateri";
        }
    }

    // DONE Endpoint za brisanje operatera po ID-u
    @GetMapping("/delete/{id}")
    public String deleteOperater(@PathVariable int id) {
        operaterRepository.deleteById(id);
        return ("redirect:/operateri");
    }
}
