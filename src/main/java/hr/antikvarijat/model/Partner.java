package hr.antikvarijat.model;

import jakarta.persistence.*;

@Entity
@Table(name = "partner")
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_partner")
    private int idPartner;

    @Column(name = "naziv_partnera")
    private String nazivPartnera;

    @Column(name = "ulica_broj")
    private String ulicaBroj;

    @ManyToOne
    @JoinColumn(name = "id_grad")
    private Grad grad;

    @Column(name = "oib")
    private String oib;

    @Column(name = "email")
    private String email;

    @Column(name = "telefon")
    private String telefon;

    // Konstruktor, getteri i setteri

    public Partner() {
    }

    public Partner(String nazivPartnera, String ulicaBroj, Grad grad, String oib, String email, String telefon) {
        this.nazivPartnera = nazivPartnera;
        this.ulicaBroj = ulicaBroj;
        this.grad = grad;
        this.oib = oib;
        this.email = email;
        this.telefon = telefon;
    }

    public int getIdPartner() {
        return idPartner;
    }

    public void setIdPartner(int idPartner) {
        this.idPartner = idPartner;
    }

    public String getNazivPartnera() {
        return nazivPartnera;
    }

    public void setNazivPartnera(String nazivPartnera) {
        this.nazivPartnera = nazivPartnera;
    }

    public String getUlicaBroj() {
        return ulicaBroj;
    }

    public void setUlicaBroj(String ulicaBroj) {
        this.ulicaBroj = ulicaBroj;
    }

    public Grad getGrad() {
        return grad;
    }

    public void setGrad(Grad grad) {
        this.grad = grad;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
