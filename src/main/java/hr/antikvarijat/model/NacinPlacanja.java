package hr.antikvarijat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class NacinPlacanja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idNacinPlacanja;
    private String nazivNacinaPlacanja;
    private String oznakaNacinaPlacanja;

    public NacinPlacanja() {
    }

    public NacinPlacanja(String nazivNacinaPlacanja, String oznakaNacinaPlacanja) {
        this.nazivNacinaPlacanja = nazivNacinaPlacanja;
        this.oznakaNacinaPlacanja = oznakaNacinaPlacanja;
    }

    public int getIdNacinPlacanja() {
        return idNacinPlacanja;
    }

    public void setIdNacinPlacanja(int idNacinPlacanja) {
        this.idNacinPlacanja = idNacinPlacanja;
    }

    public String getNazivNacinaPlacanja() {
        return nazivNacinaPlacanja;
    }

    public void setNazivNacinaPlacanja(String nazivNacinaPlacanja) {
        this.nazivNacinaPlacanja = nazivNacinaPlacanja;
    }

    public String getOznakaNacinaPlacanja() {
        return oznakaNacinaPlacanja;
    }

    public void setOznakaNacinaPlacanja(String oznakaNacinaPlacanja) {
        this.oznakaNacinaPlacanja = oznakaNacinaPlacanja;
    }
}
