package hr.antikvarijat.servis;

// Java code to generate QR code

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeGenerator {

    // Function to create the QR code
    public static void createQR(String data, String path,
                                String charset, Map hashMap,
                                int height, int width)
            throws WriterException, IOException
    {

        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(data.getBytes(charset),  "ISO-8859-1"),
                BarcodeFormat.QR_CODE, width, height);

        MatrixToImageWriter.writeToFile(
                matrix,
                path.substring(path.lastIndexOf('.') + 1),
                new File(path));
    }


    public static void createPDf417(String data, String path,
                                String charset, Map hashMap,
                                int height, int width)
            throws WriterException, IOException
    {

        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(data.getBytes(charset),  "ISO-8859-1"),
                BarcodeFormat.PDF_417, width, height);

        MatrixToImageWriter.writeToFile(
                matrix,
                path.substring(path.lastIndexOf('.') + 1),
                new File(path));
    }

    // Driver code
    public static void main(String[] args)
            throws WriterException, IOException,
            NotFoundException
    {

        // The data that the QR code will contain
//        String data = "https://www.betastudio.hr/";
        String data = "\n" +
                "Ohridski čomlek\n" +
                "===============\n" +
                "\n" +
                "\n" +
                "Sastojci:\n" +
                "---------\n" +
                "\n" +
                "70 dkg junećeg mesa\n" +
                "50 dkg sitnog luka\n" +
                "glavica češnjaka\n" +
                "tučena crvena paprika\n" +
                "1 dl crnog vina\n" +
                "10 dkg maslaca\n" +
                "peršin\n" +
                "lovorov list\n" +
                "sol\n" +
                "papar\n" +
                "\n" +
                "\n" +
                "\n" +
                "Priprema:\n" +
                "---------\n" +
                "\n" +
                "Izrežite meso na manje kocke. \n" +
                "Očistite luk i češnjak, a peršin sitno nasjeckajte. \n" +
                "U dublju zemljanu posudu stavite sve sastojke, \n" +
                "začinite i sve dobro promiješajte. \n" +
                "\n" +
                "Na kraju zalijte vinom i odozgo poslažite \n" +
                "listiće maslaca. \n" +
                "\n" +
                "Od malo brašna i vode napravite tijesto koje ćete \n" +
                "zalijepiti na rub posude i poklopca da bolje dihta.\n" +
                "\n" +
                "Poklopite posudu i lagano kuhajte jelo na \n" +
                "tihoj vatri par sati. x";

        // The path where the image will get saved
        String path = "dat/recept3.png";

        // Encoding charset
        String charset = "utf-8";



        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        Map<EncodeHintType, ErrorCorrectionLevel> hashMap
                = new HashMap<EncodeHintType,
                ErrorCorrectionLevel>();

        hashMap.put(EncodeHintType.ERROR_CORRECTION,
                ErrorCorrectionLevel.H);


        // Create the QR code and save
        // in the specified folder
        // as a jpg file
//        createQR(data, path, charset, hashMap, 200, 200);
        createPDf417(data, path, charset, hashMap, 200, 200);
        System.out.println("QR Code Generated!!! ");
    }
}

