package hr.antikvarijat.controller;

import hr.antikvarijat.exception.KnjigaNotFoundException;
import hr.antikvarijat.model.*;
import hr.antikvarijat.service.AutorService;
import hr.antikvarijat.service.IzdavacService;
import hr.antikvarijat.service.KnjigaService;
import hr.antikvarijat.servis.Kolona;
import hr.antikvarijat.servis.Podatak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/knjige")
public class KnjigaController {
    private final KnjigaService knjigaService;
    private final AutorService autorService;
    private final IzdavacService izdavacService;

    @Autowired
    public KnjigaController(KnjigaService knjigaService, AutorService autorService, IzdavacService izdavacService) {
        this.knjigaService = knjigaService;
        this.autorService = autorService;
        this.izdavacService = izdavacService;
    }

    @GetMapping("")
    public String showKnjigeList(Model model) {

        List<Kolona> listeKolona = new ArrayList<>();

        listeKolona.add(new Kolona("ID", "idKnjiga", "idKnjiga"));
        listeKolona.add(new Kolona("Naziv knjige", "nazivKnjige", "idKnjiga"));
        listeKolona.add(new Kolona("Autor", "nazivAutora", "idKnjiga"));
        listeKolona.add(new Kolona("Izdavač", "nazivIzdavaca", "idKnjiga"));
        listeKolona.add(new Kolona("Uvez", "vrstaUveza", "idKnjiga"));
        listeKolona.add(new Kolona("Dimenzije", "dimenzija", "idKnjiga"));
        listeKolona.add(new Kolona("Godina izdanja", "godinaIzdanja", "idKnjiga"));
        listeKolona.add(new Kolona("Prodajna cijena", "cijenaProdaje", "idKnjiga"));

        List<Knjiga> listaPodataka = knjigaService.dohvatiSveKnjige();

        model.addAttribute("naslov", "Popis knjiga");
        model.addAttribute("dodajLink", "/knjige/new");
        model.addAttribute("urediLink", "/knjige/edit/{id}");
        model.addAttribute("obrisiLink", "/knjige/delete/{id}");
        model.addAttribute("listeKolona", listeKolona);

        model.addAttribute("listaPodataka", listaPodataka);

        return "tablica";
    }


    @GetMapping("/new")
    public String showForm(Model model) {
        Autor autor = new Autor();
        List<Autor> listaAutora = autorService.getAllAutori();
        Izdavac izdavac = new Izdavac();
        List<Izdavac> listaIzdavaca = izdavacService.getAllIzdavaci();
        Knjiga knjiga = new Knjiga();

        // NOVI DIO KODA POČETAK

        Podatak<Object> nazivKnjige = new Podatak<>("Naziv Knjige", "nazivKnjige","", "",new ArrayList<>());
        Podatak<Autor> popisAutora = new Podatak<>("Autor", "idAutor", "nazivAutor","autor.idAutor",listaAutora);
        Podatak<Izdavac> popisIzdavaca = new Podatak<>("Izdavač", "idIzdavac", "nazivIzdavaca","idIzdavac",listaIzdavaca);
        Podatak<Object> godinaIzdanja = new Podatak<>("Godina izdanja", "godinaIzdanja", "","",new ArrayList<>());


        List<Object> sviPodaci = new ArrayList<>();
        sviPodaci.add(nazivKnjige);
        sviPodaci.add(popisAutora);
//        sviPodaci.add(popisIzdavaca);
        sviPodaci.add(godinaIzdanja);

        List<String> item = new ArrayList<>();
        item.add("nazivAutora");
//        id.add("idIzdavac");


        model.addAttribute("naslov", "Knjiga");
        model.addAttribute("idPoljePodatka", "idKnjiga");
        model.addAttribute("nazivGumba", "Spremi");
        model.addAttribute("stranica", "Spremi");


        model.addAttribute("klasa", knjiga);
        model.addAttribute("item", item);
        model.addAttribute("tmpIzdavac", listaIzdavaca);
        model.addAttribute("tmpAutor", listaAutora);


        model.addAttribute("listaPodataka", sviPodaci);

        // NOVI DIO KODA KRAJ



        return "forma";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int idKnjiga, Model model, RedirectAttributes ra) {
        try {
            List<Autor> listaAutora = autorService.getAllAutori();
            List<Izdavac> listaIzdavaca = izdavacService.getAllIzdavaci();
            Knjiga knjiga = knjigaService.dohvatiKnjiguPoId(idKnjiga);
            model.addAttribute("knjiga", knjiga);
            model.addAttribute("izdavac", listaIzdavaca);
            model.addAttribute("autor", listaAutora);
            return "knjiga_form";
        } catch (KnjigaNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/knjige";
        }
    }


    @PostMapping("/save")
    public String addKnjiga(@ModelAttribute("knjiga") Knjiga knjiga) {
        knjigaService.spremiKnjigu(knjiga);
        return "redirect:/knjige";
    }

    @GetMapping("/delete/{id}")
    public String deleteKnjiga(@PathVariable int id) {
        knjigaService.obrisiKnjigu(id);
        return "redirect:/knjige";
    }

}
