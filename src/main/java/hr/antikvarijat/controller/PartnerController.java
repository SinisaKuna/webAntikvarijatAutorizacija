package hr.antikvarijat.controller;

import hr.antikvarijat.exception.DrzavaNotFoundException;
import hr.antikvarijat.exception.PartnerNotFoundException;
import hr.antikvarijat.service.ProdajaZaglavljeService;
import hr.antikvarijat.model.Grad;
import hr.antikvarijat.servis.Kolona;
import hr.antikvarijat.model.Partner;
import hr.antikvarijat.service.GradService;
import hr.antikvarijat.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;




@Controller
@RequestMapping("/partneri")
public class PartnerController {
    private final PartnerService partnerService;
    private final GradService gradService;

    private final ProdajaZaglavljeService prodajaZaglavljeService;

    @Autowired
    public PartnerController(PartnerService partnerService, GradService gradService, ProdajaZaglavljeService prodajaZaglavljeService) {
        this.partnerService = partnerService;
        this.gradService = gradService;
        this.prodajaZaglavljeService = prodajaZaglavljeService;
    }

    @GetMapping("")
    public String showPartneriList(Model model) {


        List<Kolona> listeKolona = new ArrayList<>();

        listeKolona.add(new Kolona("ID","idPartner","idPartner"));
        listeKolona.add(new Kolona("Naziv partnera","nazivPartnera","idPartner"));
        listeKolona.add(new Kolona("Ulica i broj","ulicaBroj","idPartner"));
        listeKolona.add(new Kolona("Naziv grada","nazivGrada","idPartner"));
        listeKolona.add(new Kolona("Mail","email","idPartner"));
        /*
             <td th:text="${partner.idPartner}"></td>
              <td th:text="${partner.nazivPartnera}"></td>
              <td th:text="${partner.ulicaBroj}"></td>
              <td th:text="${partner.grad.nazivGrada}"></td>
         */

        List<Partner> listPartneri = partnerService.getAllPartners();
        model.addAttribute("listaPodataka", listPartneri);

        model.addAttribute("naslov", "Popis partnera");
        model.addAttribute("dodajLink", "/partneri/new" );
        model.addAttribute("urediLink", "/partneri/edit/{id}");
        model.addAttribute("obrisiLink", "/partneri/delete/{id}");
        model.addAttribute("listeKolona", listeKolona);

        return "tablica";
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
            if (prodajaZaglavljeService.hasProdajaZaglavlje(id)) {
                ra.addFlashAttribute("message", "Nemoguće izbrisati grad jer postoje povezani zapisi u drugim tablicama.");
            } else {
                partnerService.deletePartner(id);
            }
        } catch (DrzavaNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/partneri";
    }
}
