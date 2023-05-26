package hr.antikvarijat.model;

import java.util.ArrayList;
import java.util.List;

public class Kolona {
    private String naziv;



    private String polje;

    public Kolona(String naziv, String polje) {
        this.naziv = naziv;
        this.polje = polje;
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


}

