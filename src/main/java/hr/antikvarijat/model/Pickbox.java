package hr.antikvarijat.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pickbox")
public class Pickbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "datum")
    private String datum;

    @Column(name = "dan")
    private String dan;

    @Column(name = "vrsta")
    private String vrsta;

    @Column(name = "vrijeme")
    private String vrijeme;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "prijevod")
    private String prijevod;

    public Pickbox() {
    }

    public Pickbox(String datum, String dan, String vrsta, String vrijeme, String naziv, String prijevod) {
        this.datum = datum;
        this.dan = dan;
        this.vrsta = vrsta;
        this.vrijeme = vrijeme;
        this.naziv = naziv;
        this.prijevod = prijevod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getDan() {
        return dan;
    }

    public void setDan(String dan) {
        this.dan = dan;
    }

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public String getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(String vrijeme) {
        this.vrijeme = vrijeme;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getPrijevod() {
        return prijevod;
    }

    public void setPrijevod(String prijevod) {
        this.prijevod = prijevod;
    }
}