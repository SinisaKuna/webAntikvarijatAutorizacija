package hr.antikvarijat.model;


import jakarta.persistence.*;

@Entity
@Table(name = "operater")
public class Operater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_operater")
    private int idOperater;

    @Column(name = "ime")
    private String ime;

    @Column(name = "prezime")
    private String prezime;

    @Column(name = "oib")
    private String oib;

    @Column(name = "korisnicko_ime")
    private String korisnickoIme;

    @Column(name = "lozinka")
    private String lozinka;

    // konstruktor, getteri i setteri

    public Operater() {
    }

    public Operater(String ime, String prezime, String oib, String korisnickoIme, String lozinka) {
        this.ime = ime;
        this.prezime = prezime;
        this.oib = oib;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    public int getIdOperater() {
        return idOperater;
    }

    public void setIdOperater(int idOperater) {
        this.idOperater = idOperater;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
}
