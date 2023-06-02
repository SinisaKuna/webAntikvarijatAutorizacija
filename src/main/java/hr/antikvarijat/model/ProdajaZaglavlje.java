package hr.antikvarijat.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "prodaja_zaglavlje")
public class ProdajaZaglavlje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prodaja_zaglavlje")
    private int idProdajaZaglavlje;

    @Column(name = "broj_prodaje", length = 20)
    private String brojProdaje;

    @Column(name = "datum_prodaje")
    private Date datumProdaje;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_partner")
    private Partner partner;

    @Column(name = "zki", length = 50)
    private String zki;

    @Column(name = "jir", length = 50)
    private String jir;

    @ManyToOne
    @JoinColumn(name = "id_nacin_placanja")
    private NacinPlacanja nacinPlacanja;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    public ProdajaZaglavlje() {
        // prazan konstruktor potreban za JPA
    }

    // Konstruktor bez ID-a
    public ProdajaZaglavlje(String brojProdaje, Date datumProdaje, Partner partner, String zki, String jir,
                            NacinPlacanja nacinPlacanja, User user) {
        this.brojProdaje = brojProdaje;
        this.datumProdaje = datumProdaje;
        this.partner = partner;
        this.zki = zki;
        this.jir = jir;
        this.nacinPlacanja = nacinPlacanja;
        this.user = user;
    }

    // Getteri i setteri

    public int getIdProdajaZaglavlje() {
        return idProdajaZaglavlje;
    }

    public void setIdProdajaZaglavlje(int idProdajaZaglavlje) {
        this.idProdajaZaglavlje = idProdajaZaglavlje;
    }

    public String getBrojProdaje() {
        return brojProdaje;
    }

    public void setBrojProdaje(String brojProdaje) {
        this.brojProdaje = brojProdaje;
    }

    public Date getDatumProdaje() {
        return datumProdaje;
    }

    public void setDatumProdaje(Date datumProdaje) {
        this.datumProdaje = datumProdaje;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public String getZki() {
        return zki;
    }

    public void setZki(String zki) {
        this.zki = zki;
    }

    public String getJir() {
        return jir;
    }

    public void setJir(String jir) {
        this.jir = jir;
    }

    public NacinPlacanja getNacinPlacanja() {
        return nacinPlacanja;
    }

    public void setNacinPlacanja(NacinPlacanja nacinPlacanja) {
        this.nacinPlacanja = nacinPlacanja;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
