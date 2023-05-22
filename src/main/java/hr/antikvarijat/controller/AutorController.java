package hr.antikvarijat.controller;

import hr.antikvarijat.exception.AutorNotFoundException;
import hr.antikvarijat.model.Autor;
import hr.antikvarijat.model.Drzava;
import hr.antikvarijat.service.AutorService;
import hr.antikvarijat.service.DrzavaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        List<Autor> listAutori = autorService.getAllAutori();
        model.addAttribute("listAutori", listAutori);
        return "autori";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        Drzava drzava = new Drzava();
        List<Drzava> listaDrzava = drzavaService.getAllDrzave();
        Autor autor = new Autor();
        model.addAttribute("autor", autor);
        model.addAttribute("drzave", listaDrzava);
        return "autor_form";
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
            List<Drzava> listaDrzava = drzavaService.getAllDrzave();
            Autor autor = autorService.getAutorById(idAutor);
            model.addAttribute("autor", autor);
            model.addAttribute("drzave", listaDrzava);
            return "autor_form";
        } catch (AutorNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/autori";
        }
    }

//    @GetMapping("/delete/{id}")
//    public String deleteAutor(@PathVariable int id) {
//        autorService.deleteAutor(id);
//        return "redirect:/autori";
//    }

    @GetMapping("/delete/{id}")
    public String deleteAutor(@PathVariable int id, RedirectAttributes ra) {
        try {
            if ( autorService.hasKnjiga(id) ) {
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
