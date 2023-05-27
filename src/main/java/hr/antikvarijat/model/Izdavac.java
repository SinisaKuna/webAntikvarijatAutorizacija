package hr.antikvarijat.model;

import jakarta.persistence.*;

@Entity
@Table(name = "izdavac")
public class Izdavac {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_izdavac")
    private int idIzdavac;

    @Column(name = "naziv_izdavaca")
    private String nazivIzdavaca;

    @ManyToOne
    @JoinColumn(name = "id_grad", referencedColumnName = "id_grad", nullable = false)
    private Grad grad;

    private String nazivGrada;

    public Izdavac() {
    }

    public Izdavac(int idIzdavac, String nazivIzdavaca, Grad grad, String nazivGrada) {
        this.idIzdavac = idIzdavac;
        this.nazivIzdavaca = nazivIzdavaca;
        this.grad = grad;
        this.nazivGrada = nazivGrada;
    }

    public String getNazivGrada() {
        return nazivGrada;
    }

    public void setNazivGrada(String nazivGrada) {
        this.nazivGrada = grad.getNazivGrada();
    }

    public int getIdIzdavac() {
        return idIzdavac;
    }

    public void setIdIzdavac(int idIzdavac) {
        this.idIzdavac = idIzdavac;
    }

    public String getNazivIzdavaca() {
        return nazivIzdavaca;
    }

    public void setNazivIzdavaca(String nazivIzdavaca) {
        this.nazivIzdavaca = nazivIzdavaca;
    }

    public Grad getGrad() {
        return grad;
    }

    public void setGrad(Grad grad) {
        this.grad = grad;
    }
}
