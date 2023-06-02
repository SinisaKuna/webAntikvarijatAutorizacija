package hr.antikvarijat.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "prodaja_stavka")
public class ProdajaStavka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prodaja_stavka")
    private int idProdajaStavka;

    @ManyToOne
    @JoinColumn(name = "id_prodaja_zaglavlje")
    private ProdajaZaglavlje prodajaZaglavlje;

    @ManyToOne
    @JoinColumn(name = "id_knjiga")
    private Knjiga knjiga;

    @Column(name = "kolicina")
    private int kolicina;

    @Column(name = "cijena_prodaje")
    private BigDecimal cijenaProdaje;

    public ProdajaStavka() {
    }

    public ProdajaStavka(ProdajaZaglavlje prodajaZaglavlje, Knjiga knjiga, int kolicina, BigDecimal cijenaProdaje) {
        this.prodajaZaglavlje = prodajaZaglavlje;
        this.knjiga = knjiga;
        this.kolicina = kolicina;
        this.cijenaProdaje = cijenaProdaje;
    }

    public int getIdProdajaStavka() {
        return idProdajaStavka;
    }

    public void setIdProdajaStavka(int idProdajaStavka) {
        this.idProdajaStavka = idProdajaStavka;
    }

    public ProdajaZaglavlje getProdajaZaglavlje() {
        return prodajaZaglavlje;
    }

    public void setProdajaZaglavlje(ProdajaZaglavlje prodajaZaglavlje) {
        this.prodajaZaglavlje = prodajaZaglavlje;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public BigDecimal getCijenaProdaje() {
        return cijenaProdaje;
    }

    public void setCijenaProdaje(BigDecimal cijenaProdaje) {
        this.cijenaProdaje = cijenaProdaje;
    }

//    public int getIdProdajaZaglavlje() {
//        return prodajaZaglavlje.getIdProdajaZaglavlje();
//    }


    public int getIdProdajaZaglavlje() {
        if (prodajaZaglavlje != null) {
            return prodajaZaglavlje.getIdProdajaZaglavlje();
        }
        return 0; // ili neku drugu vrijednost koju želite vratiti ako je prodajaZaglavlje null
    }
}
