package hr.antikvarijat.servis;

public class OznakaNacinaPlacanja {

    private String oznakaNacinaPlacanja;
    private String opis;

    public OznakaNacinaPlacanja(String oznaka, String opis) {
        this.oznakaNacinaPlacanja = oznaka;
        this.opis = opis;
    }

    public String getOznakaNacinaPlacanja() {
        return oznakaNacinaPlacanja;
    }

    public void setOznakaNacinaPlacanja(String oznakaNacinaPlacanja) {
        this.oznakaNacinaPlacanja = oznakaNacinaPlacanja;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
