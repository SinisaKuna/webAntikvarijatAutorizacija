package hr.antikvarijat.servis;

import hr.antikvarijat.model.ProdajaZaglavlje;
import hr.antikvarijat.service.ProdajaZaglavljeService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class PdfGenerator {
    private final ProdajaZaglavljeService prodajaZaglavljeService;

    public PdfGenerator(ProdajaZaglavljeService prodajaZaglavljeService) {
        this.prodajaZaglavljeService = prodajaZaglavljeService;
    }

    public void generateInvoice(int idProdajaZaglavlje) {


        // Dohvatite podatke o prodaji na temelju idProdajaZaglavlje

//        ProdajaZaglavlje prodajaZaglavlje = retrieveProdajaZaglavlje(idProdajaZaglavlje);
        ProdajaZaglavlje prodajaZaglavlje = prodajaZaglavljeService.getProdajaZaglavljeById(idProdajaZaglavlje);
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

//            PDType1Font font = PDType1Font.HELVETICA;

            PDType0Font font = PDType0Font.load(document, new File("font/arial-unicode-ms.ttf"));





            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(font, 12);
            contentStream.setLeading(14.5f);

            // Postavite položaj za ispis teksta
            float y = page.getMediaBox().getHeight() - 50;

            // Ispišite naslov računa
            contentStream.beginText();
            contentStream.newLineAtOffset(50, y);
            contentStream.showText("Prodaja šđčćžŠĐČŽĆ");
//            contentStream.showText("Prodaja");
            contentStream.endText();
            y -= 20;

            // Ispišite ostale podatke o prodaji
            contentStream.beginText();
            contentStream.newLineAtOffset(50, y);

            contentStream.setFont(font, 12);
            contentStream.showText("partner: " + prodajaZaglavlje.getPartner().getNazivPartnera());
            y -= 20;

            // Nastavite s ispisivanjem ostalih podataka o prodaji...

            contentStream.endText();

            // Zatvorite PDPageContentStream
            contentStream.close();

            // Spremite PDF dokument na odredište
            document.save("pdf/prodaja.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ProdajaZaglavlje retrieveProdajaZaglavlje(int idProdajaZaglavlje) {
        // Implementirajte logiku dohvata podataka o prodaji na temelju idProdajaZaglavlje
        // Ovdje možete koristiti JPA ili neki drugi način dohvata podataka iz baze
        // Vratite ProdajaZaglavlje objekt koji sadrži potrebne podatke
        return null;
    }
}
