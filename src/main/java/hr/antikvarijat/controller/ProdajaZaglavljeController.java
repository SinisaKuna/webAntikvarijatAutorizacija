package hr.antikvarijat.controller;

import hr.antikvarijat.dto.UserDto;
import hr.antikvarijat.model.User;
import hr.antikvarijat.exception.ProdajaZaglavljeNotFoundException;
import hr.antikvarijat.model.Partner;
import hr.antikvarijat.model.NacinPlacanja;
import hr.antikvarijat.model.ProdajaZaglavlje;
import hr.antikvarijat.service.PartnerService;
import hr.antikvarijat.service.NacinPlacanjaService;
import hr.antikvarijat.service.ProdajaZaglavljeService;
import hr.antikvarijat.service.impl.UserServiceImpl;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/prodaja_zaglavlja")
public class ProdajaZaglavljeController {

    private final ProdajaZaglavljeService prodajaZaglavljeService;
    private final PartnerService partnerService;
    private final NacinPlacanjaService nacinPlacanjaService;
    private final UserServiceImpl userService;

    @Autowired
    public ProdajaZaglavljeController(ProdajaZaglavljeService prodajaZaglavljeService, PartnerService partnerService,
                                      NacinPlacanjaService nacinPlacanjaService, UserServiceImpl userService) {
        this.prodajaZaglavljeService = prodajaZaglavljeService;
        this.partnerService = partnerService;
        this.nacinPlacanjaService = nacinPlacanjaService;
        this.userService = userService;
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

        Calendar calendar = Calendar.getInstance();
        java.util.Date danasnjiDatum = calendar.getTime();

        // Pretvori java.util.Date u java.sql.Date
        Date danas = new Date(danasnjiDatum.getTime());

        prodajaZaglavlje.setDatumProdaje(danas);

        List<Partner> listaPartnera = partnerService.getAllPartners();
        List<NacinPlacanja> listaNacinaPlacanja = nacinPlacanjaService.getAllNacinPlacanja();

//        List<UserDto> listaUserDTO = userService.findAllUsers();

        User user = userService.findByEmail("");

        model.addAttribute("prodajaZaglavlje", prodajaZaglavlje);
        model.addAttribute("partner", listaPartnera);
        model.addAttribute("nacinPlacanja", listaNacinaPlacanja);
//        model.addAttribute("user", listaUserDTO);

        return "prodaja_zaglavlje_form";
    }

    @PostMapping("/save")
    public String addProdajaZaglavlje(@ModelAttribute("prodajaZaglavlje") ProdajaZaglavlje prodajaZaglavlje) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            User user = userService.findByEmail(userDetails.getUsername());
            if (user != null) {
                prodajaZaglavlje.setUser(user);
                prodajaZaglavljeService.saveProdajaZaglavlje(prodajaZaglavlje);
            } else {
                System.out.println("Nije moguće pristupiti objektu klase User.");
            }
        } else {
            System.out.println("Nema prijavljenog korisnika.");
        }
        return "redirect:/prodaja_zaglavlja";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int idProdajaZaglavlje, Model model) {
        try {
            ProdajaZaglavlje prodajaZaglavlje = prodajaZaglavljeService.getProdajaZaglavljeById(idProdajaZaglavlje);

            List<Partner> listaPartnera = partnerService.getAllPartners();
            List<NacinPlacanja> listaNacinaPlacanja = nacinPlacanjaService.getAllNacinPlacanja();
            List<UserDto> listaUserDTO = userService.findAllUsers();

            model.addAttribute("prodajaZaglavlje", prodajaZaglavlje);
            model.addAttribute("partner", listaPartnera);
            model.addAttribute("nacinPlacanja", listaNacinaPlacanja);
            model.addAttribute("user", listaUserDTO);

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
