package hr.antikvarijat.model;

import hr.antikvarijat.model.Autor;
import hr.antikvarijat.model.Izdavac;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "knjiga")
public class Knjiga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_knjiga")
    private int idKnjiga;

    @Column(name = "naziv_knjige")
    private String nazivKnjige;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;

    @Column(name = "godina_izdanja")
    private String godinaIzdanja;

    @ManyToOne
    @JoinColumn(name = "id_izdavac")
    private Izdavac izdavac;

    @Column(name = "jezik")
    private String jezik;

    @Column(name = "broj_stranica")
    private int brojStranica;

    @Column(name = "vrsta_uveza")
    private String vrstaUveza;

    @Column(name = "dimenzija")
    private String dimenzija;

    @Column(name = "cijena_prodaje")
    private BigDecimal cijenaProdaje;

    private String nazivAutora;
    private String nazivIzdavaca;


    // konstruktor, getteri i setteri

    public Knjiga() {
    }

    public Knjiga(String nazivKnjige, Autor autor, String godinaIzdanja, Izdavac izdavac, String jezik,
                  int brojStranica, String vrstaUveza, String dimenzija, BigDecimal cijenaProdaje, String nazivAutora, String nazivIzdavaca) {
        this.nazivKnjige = nazivKnjige;
        this.autor = autor;
        this.godinaIzdanja = godinaIzdanja;
        this.izdavac = izdavac;
        this.jezik = jezik;
        this.brojStranica = brojStranica;
        this.vrstaUveza = vrstaUveza;
        this.dimenzija = dimenzija;
        this.cijenaProdaje = cijenaProdaje;
        this.nazivAutora = nazivAutora;
        this.nazivIzdavaca = nazivIzdavaca;
    }

    public int getIdKnjiga() {
        return idKnjiga;
    }

    public void setIdKnjiga(int idKnjiga) {
        this.idKnjiga = idKnjiga;
    }

    public String getNazivKnjige() {
        return nazivKnjige;
    }

    public void setNazivKnjige(String nazivKnjige) {
        this.nazivKnjige = nazivKnjige;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getGodinaIzdanja() {
        return godinaIzdanja;
    }

    public void setGodinaIzdanja(String godinaIzdanja) {
        this.godinaIzdanja = godinaIzdanja;
    }

    public Izdavac getIzdavac() {
        return izdavac;
    }

    public void setIzdavac(Izdavac izdavac) {
        this.izdavac = izdavac;
    }

    public String getJezik() {
        return jezik;
    }

    public void setJezik(String jezik) {
        this.jezik = jezik;
    }

    public int getBrojStranica() {
        return brojStranica;
    }

    public void setBrojStranica(int brojStranica) {
        this.brojStranica = brojStranica;
    }

    public String getVrstaUveza() {
        return vrstaUveza;
    }

    public void setVrstaUveza(String vrstaUveza) {
        this.vrstaUveza = vrstaUveza;
    }

    public String getDimenzija() {
        return dimenzija;
    }

    public void setDimenzija(String dimenzija) {
        this.dimenzija = dimenzija;
    }

    public BigDecimal getCijenaProdaje() {
        return cijenaProdaje;
    }

    public void setCijenaProdaje(BigDecimal cijenaProdaje) {
        this.cijenaProdaje = cijenaProdaje;
    }

    public String getNazivAutora() {
        return nazivAutora;
    }

    public void setNazivAutora(String nazivAutora) {
        this.nazivAutora = autor.getNazivAutora();
    }

    public String getNazivIzdavaca() {
        return nazivIzdavaca;
    }

    public void setNazivIzdavaca(String nazivIzdavaca) {
        this.nazivIzdavaca = izdavac.getNazivIzdavaca();
    }
}
