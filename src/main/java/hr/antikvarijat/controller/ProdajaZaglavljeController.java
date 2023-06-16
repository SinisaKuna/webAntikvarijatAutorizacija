package hr.antikvarijat.controller;

import hr.antikvarijat.dto.UserDto;
import hr.antikvarijat.exception.ProdajaStavkaNotFoundException;
import hr.antikvarijat.model.*;
import hr.antikvarijat.exception.ProdajaZaglavljeNotFoundException;
import hr.antikvarijat.service.*;
import hr.antikvarijat.impl.UserServiceImpl;


import hr.antikvarijat.util.PdfGenerator;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/prodaja_zaglavlja")
public class ProdajaZaglavljeController {

    private final ProdajaZaglavljeService prodajaZaglavljeService;

    private final ProdajaStavkaService prodajaStavkaService;

    private final KnjigaService knjigaService;
    private final PartnerService partnerService;
    private final NacinPlacanjaService nacinPlacanjaService;
    private final UserServiceImpl userService;

    @Autowired
    public ProdajaZaglavljeController(ProdajaZaglavljeService prodajaZaglavljeService, ProdajaStavkaService prodajaStavkaService, KnjigaService knjigaService, PartnerService partnerService,
                                      NacinPlacanjaService nacinPlacanjaService, UserServiceImpl userService) {
        this.prodajaZaglavljeService = prodajaZaglavljeService;
        this.prodajaStavkaService = prodajaStavkaService;
        this.knjigaService = knjigaService;
        this.partnerService = partnerService;
        this.nacinPlacanjaService = nacinPlacanjaService;
        this.userService = userService;
    }

    @GetMapping("")
    public String showProdajaZaglavljeList(Model model) {
        List<ProdajaZaglavlje> listaProdajaZaglavlja = prodajaZaglavljeService.getAllProdajaZaglavlje();
        List<ProdajaStavka> listaProdajaStavki = prodajaStavkaService.getAllProdajaStavke();

        model.addAttribute("listaProdajaZaglavlja", listaProdajaZaglavlja);
        model.addAttribute("listaProdajaStavki", listaProdajaStavki);
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

//        List<Partner> listaPartnera = partnerService.getAllPartners();
        List<Partner> listaPartnera = new ArrayList<>();
        Partner prazanPartner = new Partner(); // Kreiranje praznog objekta klase
        listaPartnera.add(prazanPartner); // Dodavanje praznog objekta u listu
        listaPartnera.addAll(partnerService.getSortedPartner());



        List<NacinPlacanja> listaNacinaPlacanja = nacinPlacanjaService.getAllNacinPlacanja();

        User user = userService.findByEmail("");

        model.addAttribute("prodajaZaglavlje", prodajaZaglavlje);
        model.addAttribute("partner", listaPartnera);
        model.addAttribute("nacinPlacanja", listaNacinaPlacanja);

        return "prodaja_zaglavlje_form";
    }


    @GetMapping("/new_stavka")
    public String showFormStavka(Model model) {
        try {
            List<Knjiga> listaKnjiga = knjigaService.getSortedKnjiga();
            ProdajaStavka prodajaStavka = new ProdajaStavka();
            // Postavite potrebne atribute modela prema vašim potrebama
            model.addAttribute("prodajaStavka", prodajaStavka);
            model.addAttribute("knjiga", listaKnjiga);
            return "prodaja_stavka_form";
        } catch (ProdajaStavkaNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "prodaja_zaglavlja";
        }
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

                int id = prodajaZaglavlje.getPartner().getIdPartner();
                if (id == 0){
                    prodajaZaglavlje.setPartner(null);
                }
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

//            List<Partner> listaPartnera = partnerService.getAllPartners();
            List<Partner> listaPartnera = new ArrayList<>();
            Partner prazanPartner = new Partner(); // Kreiranje praznog objekta klase
            listaPartnera.add(prazanPartner); // Dodavanje praznog objekta u listu
            listaPartnera.addAll(partnerService.getSortedPartner());


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


//    @GetMapping("/pdf/{id}")
//    public String pdfEditForm(@PathVariable("id") int idProdajaZaglavlje, Model model) {
//        try {
//
//            PdfGenerator pdfGenerator = new PdfGenerator(prodajaZaglavljeService);
//            pdfGenerator.generateInvoice(idProdajaZaglavlje);
//
//
//            return "redirect:/prodaja_zaglavlja/view";
//        } catch (ProdajaZaglavljeNotFoundException e) {
//            return "redirect:/prodaja_zaglavlja";
//        }
//    }


    @GetMapping("/pdf/{id}")
    public void pdfEditForm(@PathVariable("id") int idProdajaZaglavlje, HttpServletResponse response) {
        try {
            PdfGenerator pdfGenerator = new PdfGenerator(prodajaZaglavljeService, prodajaStavkaService);
            pdfGenerator.generateInvoice(idProdajaZaglavlje);

            // Postavi odgovarajuće zaglavlje
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=prodaja.pdf");

            // Učitaj generisani PDF dokument
            File pdfFile = new File("pdf/prodaja.pdf");
            FileInputStream fileInputStream = new FileInputStream(pdfFile);

            // Kopiraj PDF sadržaj u odgovor
            IOUtils.copy(fileInputStream, response.getOutputStream());

            // Zatvori tokove
            fileInputStream.close();
            response.getOutputStream().flush();
        } catch (IOException | ProdajaZaglavljeNotFoundException e) {
            // Handlaj izuzetak
        }
    }


    @GetMapping("/delete_stavka/{id}")
    public String deleteProdajaStavka(@PathVariable("id") int id) {
        prodajaStavkaService.deleteProdajaStavka(id);
        return "redirect:/prodaja_zaglavlja";
    }


//    @GetMapping("/view")
//    public void viewPdf(HttpServletResponse response) throws IOException {
//        String pdfPath = "pdf/prodaja.pdf";
//        Path file = Paths.get(pdfPath);
//
//        if (Files.exists(file)) {
//            response.setContentType("application/pdf");
//            response.setHeader("Content-Disposition", "inline; filename=" + file.getFileName());
//
//            try (InputStream inputStream = new FileInputStream(pdfPath)) {
//                StreamUtils.copy(inputStream, response.getOutputStream());
//            }
//        } else {
//            response.sendError(HttpServletResponse.SC_NOT_FOUND);
//        }
//    }

//    @GetMapping("/view")
//    public void viewPdf(HttpServletResponse response) throws IOException {
//        String pdfPath = "pdf/prodaja.pdf";
//        Path file = Paths.get(pdfPath);
//
//        if (Files.exists(file)) {
//            response.setContentType("application/pdf");
//            response.setHeader("Content-Disposition", "inline; filename=" + file.getFileName());
//
//            try (InputStream inputStream = new FileInputStream(pdfPath)) {
//                StreamUtils.copy(inputStream, response.getOutputStream());
//            }
//        } else {
//            response.sendError(HttpServletResponse.SC_NOT_FOUND);
//        }
//    }

    @GetMapping("/edit_stavka/{id}")
    public String showEditFormStavka(@PathVariable("id") int id, Model model) {
        try {
            List<Knjiga> listaKnjiga = knjigaService.getSortedKnjiga();
            ProdajaStavka prodajaStavka = prodajaStavkaService.getProdajaStavkaById(id);
            // Postavite potrebne atribute modela prema vašim potrebama
            model.addAttribute("prodajaStavka", prodajaStavka);
            model.addAttribute("knjiga", listaKnjiga);
            return "prodaja_forma";
        } catch (ProdajaStavkaNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "prodaja_zaglavlja";
        }
    }


    @GetMapping("/delete/{id}")
    public String deleteProdajaZaglavlje(@PathVariable int id) {
        prodajaZaglavljeService.deleteProdajaZaglavlje(id);
        return "redirect:/prodaja_zaglavlja";
    }
}
