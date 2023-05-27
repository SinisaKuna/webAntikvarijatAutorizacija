package hr.antikvarijat.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autor")
    private int idAutor;

    @Column(name = "naziv_autora")
    private String nazivAutora;

    @ManyToOne
    @JoinColumn(name = "id_drzava")
    private Drzava drzava;

    private String nazivDrzave;

    public Autor() {
    }

    public Autor(String nazivAutora, Drzava drzava, String nazivDrzave) {
        this.nazivAutora = nazivAutora;
        this.drzava = drzava;
        this.nazivDrzave = nazivDrzave;
    }
    public String getNazivDrzave() {
        return nazivDrzave;
    }

    public void setNazivDrzave(String nazivDrzave) {
        this.nazivDrzave = drzava.getNazivDrzave();
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public String getNazivAutora() {
        return nazivAutora;
    }

    public void setNazivAutora(String nazivAutora) {
        this.nazivAutora = nazivAutora;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }
}
