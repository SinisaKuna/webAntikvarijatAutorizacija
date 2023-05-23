package hr.antikvarijat.controller;

import hr.antikvarijat.exception.ProdajaZaglavljeNotFoundException;
import hr.antikvarijat.model.Partner;
import hr.antikvarijat.model.NacinPlacanja;
import hr.antikvarijat.model.Operater;
import hr.antikvarijat.model.ProdajaZaglavlje;
import hr.antikvarijat.service.PartnerService;
import hr.antikvarijat.service.NacinPlacanjaService;
import hr.antikvarijat.service.OperaterService;
import hr.antikvarijat.service.ProdajaZaglavljeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/prodaja_zaglavlja")
public class ProdajaZaglavljeController {

    private final ProdajaZaglavljeService prodajaZaglavljeService;
    private final PartnerService partnerService;
    private final NacinPlacanjaService nacinPlacanjaService;
    private final OperaterService operaterService;

    @Autowired
    public ProdajaZaglavljeController(ProdajaZaglavljeService prodajaZaglavljeService, PartnerService partnerService,
                                      NacinPlacanjaService nacinPlacanjaService, OperaterService operaterService) {
        this.prodajaZaglavljeService = prodajaZaglavljeService;
        this.partnerService = partnerService;
        this.nacinPlacanjaService = nacinPlacanjaService;
        this.operaterService = operaterService;
    }

    @GetMapping("")
    public String showProdajaZaglavljeList(Model model) {
        List<ProdajaZaglavlje> listaProdajaZaglavlja = prodajaZaglavljeService.getAllProdajaZaglavlje();

        model.addAttribute("listaProdajaZaglavlja", listaProdajaZaglavlja);
        return "prodaja_zaglavlja";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        ProdajaZaglavlje prodajaZaglavlje = new ProdajaZaglavlje();
        List<Partner> listaPartnera = partnerService.getAllPartners();
//        listaPartnera.add(0, null);
        List<NacinPlacanja> listaNacinaPlacanja = nacinPlacanjaService.getAllNacinPlacanja();
        List<Operater> listaOperatera = operaterService.listAll();

        model.addAttribute("prodajaZaglavlje", prodajaZaglavlje);
        model.addAttribute("partner", listaPartnera);
        model.addAttribute("nacinPlacanja", listaNacinaPlacanja);
        model.addAttribute("operater", listaOperatera);

        return "prodaja_zaglavlje_form";
    }

    @PostMapping("/save")
    public String addProdajaZaglavlje(@ModelAttribute("prodajaZaglavlje") ProdajaZaglavlje prodajaZaglavlje) {

        prodajaZaglavljeService.saveProdajaZaglavlje(prodajaZaglavlje);
        return "redirect:/prodaja_zaglavlja";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int idProdajaZaglavlje, Model model) {
        try {
            ProdajaZaglavlje prodajaZaglavlje = prodajaZaglavljeService.getProdajaZaglavljeById(idProdajaZaglavlje);

            List<Partner> listaPartnera = partnerService.getAllPartners();
            List<NacinPlacanja> listaNacinaPlacanja = nacinPlacanjaService.getAllNacinPlacanja();
            List<Operater> listaOperatera = operaterService.listAll();

            model.addAttribute("prodajaZaglavlje", prodajaZaglavlje);
            model.addAttribute("partner", listaPartnera);
            model.addAttribute("nacinPlacanja", listaNacinaPlacanja);
            model.addAttribute("operater", listaOperatera);

            return "prodaja_zaglavlje_form";
        } catch (ProdajaZaglavljeNotFoundException e) {
            return "redirect:/prodaja_zaglavlja";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteProdajaZaglavlje(@PathVariable int id) {
        prodajaZaglavljeService.deleteProdajaZaglavlje(id);
        return "redirect:/prodaja_zaglavlja";
    }
}
