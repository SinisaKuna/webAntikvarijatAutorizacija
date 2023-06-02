package hr.antikvarijat.controller;

import hr.antikvarijat.exception.ProdajaStavkaNotFoundException;
import hr.antikvarijat.impl.UserServiceImpl;
import hr.antikvarijat.model.*;
import hr.antikvarijat.repository.KnjigaRepository;
import hr.antikvarijat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/prodaja_stavke")
public class ProdajaController {
    private final ProdajaStavkaService prodajaStavkaService;

    private final KnjigaService knjigaService;
    private final KnjigaRepository knjigaRepository;


    private final PartnerService partnerService;
    private final NacinPlacanjaService nacinPlacanjaService;
    private final UserServiceImpl userService;

    private final ProdajaZaglavljeService prodajaZaglavljeService;

    @Autowired
    public ProdajaController(ProdajaStavkaService prodajaStavkaService, KnjigaService knjigaService,
                             KnjigaRepository knjigaRepository, PartnerService partnerService, NacinPlacanjaService nacinPlacanjaService, UserServiceImpl userService, ProdajaZaglavljeService prodajaZaglavljeService) {
        this.prodajaStavkaService = prodajaStavkaService;
        this.knjigaService = knjigaService;
        this.knjigaRepository = knjigaRepository;
        this.partnerService = partnerService;
        this.nacinPlacanjaService = nacinPlacanjaService;
        this.userService = userService;
        this.prodajaZaglavljeService = prodajaZaglavljeService;
    }

    @GetMapping("")
    public String showPosTerminal(Model model) {

        // zaglavlje računa

        ProdajaZaglavlje prodajaZaglavlje = new ProdajaZaglavlje();

        Calendar calendar = Calendar.getInstance();
        java.util.Date danasnjiDatum = calendar.getTime();

        Date danas = new Date(danasnjiDatum.getTime());

        prodajaZaglavlje.setDatumProdaje(danas);

        List<Partner> listaPartnera = new ArrayList<>();
        Partner prazanPartner = new Partner(); // Kreiranje praznog objekta klase
        listaPartnera.add(prazanPartner); // Dodavanje praznog objekta u listu
        listaPartnera.addAll(partnerService.getSortedPartner());


        List<NacinPlacanja> listaNacinaPlacanja = nacinPlacanjaService.getAllNacinPlacanja();

        User user = userService.findByEmail("");

        model.addAttribute("prodajaZaglavlje", prodajaZaglavlje);
        model.addAttribute("partner", listaPartnera);
        model.addAttribute("nacinPlacanja", listaNacinaPlacanja);

        // stavke računa

        List<Knjiga> listaKnjiga = new ArrayList<>();
        Knjiga praznaKnjiga = new Knjiga(); // Kreiranje praznog objekta klase Knjiga
        listaKnjiga.add(praznaKnjiga); // Dodavanje prazne knjige u listu

        List<Knjiga> popunjenaListaKnjiga = knjigaService.getSortedKnjiga();
        listaKnjiga.addAll(popunjenaListaKnjiga);

        ProdajaStavka prodajaStavka = new ProdajaStavka();
        prodajaStavka.setKolicina(1);


//        List<ProdajaStavka> listaProdajaStavki = prodajaStavkaService.getAllProdajaStavke();
        List<ProdajaStavka> listaProdajaStavki = prodajaStavkaService.getAllProdajaStavke()
                .stream()
                .filter(stavka -> stavka.getIdProdajaZaglavlje() == 0)
                .collect(Collectors.toList());

        model.addAttribute("listaProdajaStavki", listaProdajaStavki);
        model.addAttribute("prodajaStavka", prodajaStavka);
        model.addAttribute("knjiga", listaKnjiga);

        return "prodaja_stavke";
    }


    @PostMapping("/dodaj")
    public String addProdajaStavka(@ModelAttribute("prodajaStavka") ProdajaStavka prodajaStavka) {
        prodajaStavkaService.saveProdajaStavka(prodajaStavka);
        return "redirect:/prodaja_stavke";
    }

    @PostMapping("/save")
    public String saveProdajaStavka(@ModelAttribute("prodajaStavka") ProdajaStavka prodajaStavka) {

        int id = prodajaStavka.getProdajaZaglavlje().getIdProdajaZaglavlje();
        if (id == 0){
            prodajaStavka.setProdajaZaglavlje(null);
        }
        prodajaStavkaService.saveProdajaStavka(prodajaStavka);
        return "redirect:/prodaja_zaglavlja";
    }

    @PostMapping("/save_stavka")
    public String saveProdajaStavka2(@ModelAttribute("prodajaStavka") ProdajaStavka prodajaStavka) {

        int id = prodajaStavka.getProdajaZaglavlje().getIdProdajaZaglavlje();
        if (id == 0){
            prodajaStavka.setProdajaZaglavlje(null);
        }
        prodajaStavkaService.saveProdajaStavka(prodajaStavka);
        return "redirect:/prodaja_stavke";
    }
    @PostMapping("/save_racun")
    public String saveProdaja(@ModelAttribute("prodajaStavka") ProdajaStavka prodajaStavka, @ModelAttribute("prodajaZaglavlje") ProdajaZaglavlje prodajaZaglavlje) {
//    public String saveProdaja(@ModelAttribute("prodajaZaglavlje") ProdajaZaglavlje prodajaZaglavlje) {
        // prvo spremam zaglavlje računa
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            User user = userService.findByEmail(userDetails.getUsername());
            if (user != null) {
                prodajaZaglavlje.setUser(user);
                int id = prodajaZaglavlje.getPartner().getIdPartner();
                if (id == 0){
                    prodajaZaglavlje.setPartner(null);
                }
                prodajaZaglavljeService.saveProdajaZaglavlje(prodajaZaglavlje);
                // nalazim zadnj id
                int idZaglavlje = prodajaZaglavljeService.findMaxIdProdajaZaglavlje();

                List<ProdajaStavka> listaProdajaStavki = prodajaStavkaService.getAllProdajaStavke()
                        .stream()
                        .filter(stavka -> stavka.getIdProdajaZaglavlje() == 0)
                        .collect(Collectors.toList());


                for (ProdajaStavka stavka : listaProdajaStavki) {

                    ProdajaZaglavlje zaglavlje = prodajaZaglavljeService.getProdajaZaglavljeById(idZaglavlje);

                    stavka.setProdajaZaglavlje(zaglavlje);

                    prodajaStavkaService.saveProdajaStavka(stavka);
                }

                System.out.println(idZaglavlje);

            } else {
                System.out.println("Nije moguće pristupiti objektu klase User.");
            }
        } else {
            System.out.println("Nema prijavljenog korisnika.");
        }
        return "redirect:/prodaja_zaglavlja";
    }


    @GetMapping("/edit/{id}")
    public String showEditFormStavka(@PathVariable("id") int id, Model model) {
        try {
            List<Knjiga> listaKnjiga = knjigaService.getSortedKnjiga();
            ProdajaStavka prodajaStavka = prodajaStavkaService.getProdajaStavkaById(id);
            // Postavite potrebne atribute modela prema vašim potrebama
            model.addAttribute("prodajaStavka", prodajaStavka);
            model.addAttribute("knjiga", listaKnjiga);
            return "prodaja_stavka_form";
        } catch (ProdajaStavkaNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "prodaja_zaglavlja";
        }
    }

    @GetMapping("/edit_stavka/{id}")
    public String showEditFormStavka2(@PathVariable("id") int id, Model model) {
        try {
            List<Knjiga> listaKnjiga = knjigaService.getSortedKnjiga();
            ProdajaStavka prodajaStavka = prodajaStavkaService.getProdajaStavkaById(id);
            // Postavite potrebne atribute modela prema vašim potrebama
            model.addAttribute("prodajaStavka", prodajaStavka);
            model.addAttribute("knjiga", listaKnjiga);
            return "prodaja_forma";
        } catch (ProdajaStavkaNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "prodaja_stavke";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteProdajaStavka(@PathVariable("id") int id) {
        prodajaStavkaService.deleteProdajaStavka(id);
        return "redirect:/prodaja_zaglavlja";
    }

    @GetMapping("/delete_stavka/{id}")
    public String deleteProdajaStavka2(@PathVariable("id") int id) {
        prodajaStavkaService.deleteProdajaStavka(id);
        return "redirect:/prodaja_stavke";
    }
}

