package hr.antikvarijat.model;

import jakarta.persistence.*;

@Entity
@Table(name = "drzava")
public class Drzava {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_drzava")
    private int idDrzava;

    @Column(name = "naziv_drzave")
    private String nazivDrzave;

    // Konstruktor, getteri i setteri

    public Drzava() {
    }

    public Drzava(String nazivDrzave) {
        this.nazivDrzave = nazivDrzave;
    }

    public int getIdDrzava() {
        return idDrzava;
    }

    public void setIdDrzava(int idDrzava) {
        this.idDrzava = idDrzava;
    }

    public String getNazivDrzave() {
        return nazivDrzave;
    }

    public void setNazivDrzave(String nazivDrzave) {
        this.nazivDrzave = nazivDrzave;
    }
}
