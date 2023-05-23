package hr.antikvarijat.controller;

import hr.antikvarijat.exception.GradNotFoundException;
import hr.antikvarijat.exception.PartnerNotFoundException;
import hr.antikvarijat.model.Grad;
import hr.antikvarijat.model.Partner;
import hr.antikvarijat.service.GradService;
import hr.antikvarijat.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/partneri")
public class PartnerController {
    private final PartnerService partnerService;
    private final GradService gradService;

    @Autowired
    public PartnerController(PartnerService partnerService, GradService gradService) {
        this.partnerService = partnerService;
        this.gradService = gradService;
    }

    @GetMapping("")
    public String showPartneriList(Model model) {
        List<Partner> listPartneri = partnerService.getAllPartners();
        model.addAttribute("listPartneri", listPartneri);
        return "partneri";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        List<Grad> listaGradova = gradService.getAllGradovi();
        Partner partner = new Partner();
        model.addAttribute("partner", partner);
        model.addAttribute("gradovi", listaGradova);
        return "partner_form";
    }

    @PostMapping("/save")
    public String addPartner(@ModelAttribute("partner") Partner partner) {
        Grad grad = gradService.getGradById(partner.getGrad().getIdGrad());
        partner.setGrad(grad);
        partnerService.savePartner(partner);
        return "redirect:/partneri";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int idPartner, Model model, RedirectAttributes ra) {
        try {
            List<Grad> listaGradova = gradService.getAllGradovi();
            Partner partner = partnerService.getPartnerById(idPartner);
            model.addAttribute("partner", partner);
            model.addAttribute("gradovi", listaGradova);
            return "partner_form";
        } catch (PartnerNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/partneri";
        }
    }

    @GetMapping("/delete/{id}")
    public String deletePartner(@PathVariable int id, RedirectAttributes ra) {
        try {
            partnerService.deletePartner(id);
        } catch (PartnerNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/partneri";
    }
}
