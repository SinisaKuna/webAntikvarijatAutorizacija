package hr.antikvarijat.servis;

import hr.antikvarijat.model.Drzava;
import hr.antikvarijat.model.Grad;
import hr.antikvarijat.repository.DrzavaRepository;
import hr.antikvarijat.service.DrzavaService;

import java.util.ArrayList;
import java.util.List;

public class Podatak<T> {

    private String naziv;
    private String polje;

    private String veza;

    private String opcija;

    private List<T> lista;

    public Podatak(String naziv, String polje, String opcija, String veza, List<T> lista) {
        this.naziv = naziv;
        this.polje = polje;
        this.opcija = opcija;
        this.lista = lista;
        this.veza = veza;
    }
    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getPolje() {
        return polje;
    }

    public void setPolje(String polje) {
        this.polje = polje;
    }

    public String getOpcija() {
        return opcija;
    }

    public void setOpcija(String opcija) {
        this.opcija = opcija;
    }

    public String getVeza() {
        return veza;
    }

    public void setVeza(String veza) {
        this.veza = veza;
    }

    public List<T> getLista() {
        return lista;
    }

    public void setLista(List<T> lista) {
        this.lista = lista;
    }


}
