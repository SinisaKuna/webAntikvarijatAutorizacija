package hr.antikvarijat.servis;

import hr.antikvarijat.model.ProdajaStavka;
import hr.antikvarijat.model.ProdajaZaglavlje;
import hr.antikvarijat.service.ProdajaStavkaService;
import hr.antikvarijat.service.ProdajaZaglavljeService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

public class PdfGenerator {
    private final ProdajaZaglavljeService prodajaZaglavljeService;

    private final ProdajaStavkaService prodajaStavkaService;

    public PdfGenerator(ProdajaZaglavljeService prodajaZaglavljeService, ProdajaStavkaService prodajaStavkaService) {
        this.prodajaZaglavljeService = prodajaZaglavljeService;
        this.prodajaStavkaService = prodajaStavkaService;
    }

    public void generateInvoice(int idProdajaZaglavlje) {


        // Dohvatite podatke o prodaji na temelju idProdajaZaglavlje
        ProdajaZaglavlje prodajaZaglavlje = prodajaZaglavljeService.getProdajaZaglavljeById(idProdajaZaglavlje);

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDType0Font font = PDType0Font.load(document, new File("font/Roboto-Medium.ttf"));

            PDPageContentStream contentStream = new PDPageContentStream(document, page);


            contentStream.setFont(font, 12);
            contentStream.setLeading(14.5f);

            // Postavite položaj za ispis teksta
            float y = page.getMediaBox().getHeight() - 50;

            // Ispišite naslov računa
            contentStream.beginText();
            contentStream.newLineAtOffset(50, y);
            contentStream.showText("Web antikvarijat, Ulica jablanova bb, zadnja pošta Donja Motičina, OIB: nema, IBAN: još manje");
            contentStream.endText();
            y -= 10; // manji razmak do linije

            //  crta liniju
            contentStream.setStrokingColor(Color.BLACK);
            contentStream.setLineWidth(1.0f);
            contentStream.moveTo(50, y);
            contentStream.lineTo(562, y);
            contentStream.stroke();
            y -= 30;

            // Ispišite ostale podatke o prodaji
//            contentStream.beginText();
//            contentStream.newLineAtOffset(50, y);

            contentStream.setFont(font, 10);
            if (prodajaZaglavlje.getPartner() != null) {
                contentStream.beginText();
                contentStream.newLineAtOffset(350, y);
                contentStream.showText(prodajaZaglavlje.getPartner().getNazivPartnera());
                contentStream.endText();
                y -= 20;
                contentStream.beginText();
                contentStream.newLineAtOffset(350, y);
                contentStream.showText(prodajaZaglavlje.getPartner().getUlicaBroj());
                contentStream.endText();
                y -= 20;
                contentStream.beginText();
                contentStream.newLineAtOffset(350, y);
                contentStream.showText(prodajaZaglavlje.getPartner().getGrad().getPostanskiBroj() + " " + prodajaZaglavlje.getPartner().getGrad().getNazivGrada());
                contentStream.endText();
                y -= 20;
                contentStream.beginText();
                contentStream.newLineAtOffset(350, y);
                contentStream.showText("OIB: " + prodajaZaglavlje.getPartner().getOib());
                contentStream.endText();
                y -= 20;

//                contentStream.setStrokingColor(Color.BLACK);
//                contentStream.setLineWidth(1.0f);
//                contentStream.moveTo(50, y);
//                contentStream.lineTo(562, y);
//                contentStream.stroke();
//                y -= 20;

            }
            y -= 20;
            contentStream.beginText();
            contentStream.newLineAtOffset(50, y);
            contentStream.showText("Račun broj: " + prodajaZaglavlje.getIdProdajaZaglavlje() + "-1-1");
            contentStream.endText();
            // y -= 20;

            contentStream.beginText();
            contentStream.newLineAtOffset(350, y);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String croDatum = dateFormat.format(prodajaZaglavlje.getDatumProdaje());
            contentStream.showText("Datum izdavanja računa: " + croDatum);
            contentStream.endText();
            y -= 20;

            contentStream.beginText();
            contentStream.newLineAtOffset(350, y);
            contentStream.showText("Oznaka operatera: " + prodajaZaglavlje.getUser().getName());
            contentStream.endText();
            y -= 20;

            // knjiga

            y -= 30;
            List<ProdajaStavka> listaProdajaStavki = prodajaStavkaService.getProdajaStavkeZaRacun(idProdajaZaglavlje);

//            for (ProdajaStavka prodajaStavka : listaProdajaStavki) {
//                contentStream.beginText();
//                contentStream.newLineAtOffset(50, y);
//                contentStream.showText(prodajaStavka.getKnjiga().getNazivKnjige());
//                contentStream.showText("       količina: ");
//                contentStream.showText(String.valueOf(prodajaStavka.getKolicina()));
//                contentStream.showText("       cijena: ");
//                contentStream.showText(String.valueOf(prodajaStavka.getCijenaProdaje()));
//                contentStream.showText("       iznos: ");
//                BigDecimal cijenaProdaje = prodajaStavka.getCijenaProdaje();
//                int kolicina = prodajaStavka.getKolicina();
//                BigDecimal kolicinaBigDecimal = BigDecimal.valueOf(kolicina);
//                BigDecimal iznosStavke = cijenaProdaje.multiply(kolicinaBigDecimal);
//                contentStream.showText(String.valueOf(iznosStavke));
//                contentStream.endText();
//                y -= 20;
//            }

            contentStream.setStrokingColor(Color.DARK_GRAY);
            contentStream.setLineWidth(1);
            int pageWidth = (int) page.getTrimBox().getWidth(); //get width of the page
            int pageHeight = (int) page.getTrimBox().getHeight(); //get height of the page

            int initX = 50;
//            int initY = pageHeight - 50;
            float initY = y;
            int cellHeight = 20;
            int cellWidth = 100;

            int colCount = 4;
            int rowCount = listaProdajaStavki.size() + 1;

            for (int i = 1; i <= rowCount; i++) {
                for (int j = 1; j <= colCount; j++) {

                    if (i == 1) {

                        if (j == 1) {
                            int sirina = 100;
                            contentStream.addRect(initX, initY, cellWidth + sirina, -cellHeight);
                            contentStream.beginText();
                            contentStream.newLineAtOffset(initX + 10, initY - cellHeight + 7);
                            contentStream.setFont(font, 10);
                            contentStream.showText("naziv knjige");
                            contentStream.endText();
                            initX += cellWidth + sirina;

                        } else if (j == 2) {
                            contentStream.addRect(initX, initY, cellWidth, -cellHeight);
                            contentStream.beginText();
                            contentStream.newLineAtOffset(initX + 15, initY - cellHeight + 7);
                            contentStream.setFont(font, 10);
                            contentStream.showText("količina");
                            contentStream.endText();
                            initX += cellWidth;

                        } else if (j == 3) {
                            contentStream.addRect(initX, initY, cellWidth, -cellHeight);
                            contentStream.beginText();
                            contentStream.newLineAtOffset(initX + 15, initY - cellHeight + 7);
                            contentStream.setFont(font, 10);
                            contentStream.showText("cijena");
                            contentStream.endText();
                            initX += cellWidth;

                        } else if (j == 4) {
                            contentStream.addRect(initX, initY, cellWidth, -cellHeight);
                            contentStream.beginText();
                            contentStream.newLineAtOffset(initX + 15, initY - cellHeight + 7);
                            contentStream.setFont(font, 10);
                            contentStream.showText("iznos");
                            contentStream.endText();
                            initX += cellWidth;

                        }

                    } else {


                        ProdajaStavka prodajaStavka = listaProdajaStavki.get(i-2);

                        if (j == 1) {
                            int sirina = 100;
                            contentStream.addRect(initX, initY, cellWidth + sirina, -cellHeight);
                            contentStream.beginText();
                            contentStream.newLineAtOffset(initX + 10, initY - cellHeight + 7);
                            contentStream.setFont(font, 10);
                            contentStream.showText(prodajaStavka.getKnjiga().getNazivKnjige());
                            contentStream.endText();
                            initX += cellWidth + sirina;

                        } else if (j == 2) {
                            contentStream.addRect(initX, initY, cellWidth, -cellHeight);
                            contentStream.beginText();
                            contentStream.newLineAtOffset(initX + 30, initY - cellHeight + 7);
                            contentStream.setFont(font, 10);
                            contentStream.showText(String.valueOf(prodajaStavka.getKolicina()));
                            contentStream.endText();
                            initX += cellWidth;

                        } else if (j == 3) {
                            contentStream.addRect(initX, initY, cellWidth, -cellHeight);
                            contentStream.beginText();
                            contentStream.newLineAtOffset(initX + 20, initY - cellHeight + 7);
                            contentStream.setFont(font, 10);
                            contentStream.showText(String.valueOf(prodajaStavka.getCijenaProdaje()));
                            contentStream.endText();
                            initX += cellWidth;

                        } else if (j == 4) {
                            contentStream.addRect(initX, initY, cellWidth, -cellHeight);
                            contentStream.beginText();
                            contentStream.newLineAtOffset(initX + 15, initY - cellHeight + 7);
                            contentStream.setFont(font, 10);
                            BigDecimal cijenaProdaje = prodajaStavka.getCijenaProdaje();
                            int kolicina = prodajaStavka.getKolicina();
                            BigDecimal kolicinaBigDecimal = BigDecimal.valueOf(kolicina);
                            BigDecimal iznosStavke = cijenaProdaje.multiply(kolicinaBigDecimal);
                            contentStream.showText(String.valueOf(iznosStavke));
                            contentStream.endText();
                            initX += cellWidth;

                        }
                    }
                }
                initX = 50;
                initY -= cellHeight;
            }

            contentStream.stroke();


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

    public static void creteTablePdf() throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDType0Font font = PDType0Font.load(document, new File("font/Roboto-Medium.ttf"));

        int pageWidth = (int) page.getTrimBox().getWidth(); //get width of the page
        int pageHeight = (int) page.getTrimBox().getHeight(); //get height of the page

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.setStrokingColor(Color.DARK_GRAY);
        contentStream.setLineWidth(1);

        int initX = 50;
        int initY = pageHeight - 50;
        int cellHeight = 20;
        int cellWidth = 100;

        int colCount = 5;
        int rowCount = 3;

        for (int i = 1; i <= rowCount; i++) {
            for (int j = 1; j <= colCount; j++) {
                if (j == 2) {
                    int sirina2 = 50;
                    contentStream.addRect(initX, initY, cellWidth + sirina2, -cellHeight);

                    contentStream.beginText();
                    contentStream.newLineAtOffset(initX + 30, initY - cellHeight + 7);
                    contentStream.setFont(font, 10);
                    contentStream.showText("tablica [" + i + "," + j + "]");
                    contentStream.endText();

                    initX += cellWidth + sirina2;
                } else {
                    contentStream.addRect(initX, initY, cellWidth, -cellHeight);

                    contentStream.beginText();
                    contentStream.newLineAtOffset(initX + 10, initY - cellHeight + 7);
                    contentStream.setFont(font, 10);
                    contentStream.showText("tablica [" + i + "," + j + "]");
                    contentStream.endText();

                    initX += cellWidth;
                }

            }
            initX = 50;
            initY -= cellHeight;
        }

        contentStream.stroke();
        contentStream.close();


        document.save("pdf/table.pdf");
        document.close();
        System.out.println("table pdf created");
    }

    public static void main(String[] args) throws IOException {
        creteTablePdf();
    }


}
