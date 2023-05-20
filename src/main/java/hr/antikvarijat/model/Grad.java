package hr.antikvarijat.model;

import jakarta.persistence.*;

@Entity
@Table(name = "grad")
public class Grad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grad")
    private int idGrad;

    @Column(name = "postanski_broj", nullable = false)
    private String postanskiBroj;

    @Column(name = "naziv_grada", nullable = false)
    private String nazivGrada;

    @ManyToOne
    @JoinColumn(name = "id_drzava", referencedColumnName = "id_drzava", nullable = false)
    private Drzava drzava;

    // konstruktori, getteri i setteri

    public Grad() {
    }

    public Grad(String postanskiBroj, String nazivGrada, Drzava drzava) {
        this.postanskiBroj = postanskiBroj;
        this.nazivGrada = nazivGrada;
        this.drzava = drzava;
    }

    public int getIdGrad() {
        return idGrad;
    }

    public void setIdGrad(int idGrad) {
        this.idGrad = idGrad;
    }

    public String getPostanskiBroj() {
        return postanskiBroj;
    }

    public void setPostanskiBroj(String postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }

    public String getNazivGrada() {
        return nazivGrada;
    }

    public void setNazivGrada(String nazivGrada) {
        this.nazivGrada = nazivGrada;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }
}
