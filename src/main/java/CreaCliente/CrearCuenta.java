package CreaCliente;

import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.apache.http.HttpStatus;
import java.io.FileWriter;



public class CrearCuenta {




    public static void main(String[] args) throws IOException {
        CrearCuentaBus crearCuentaBus = new CrearCuentaBus();

        //Read CSV file
        CSVReader reader = null;
        String CSV_file = "C:\\Auto\\DataGAC\\CrearCuentaCB.csv";

        String codigoUnico="";
        String sProducto="";
        String tiempoPlazo="";
        String tasaAnual="";
        String moneda="";

        String archCSV = "C:\\Auto\\DataGAC\\DataCUconCuentas.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(archCSV),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER);
        String[] headers = {"codunico","storeCode","accountnumber"};
        writer.writeNext(headers);

        try{
            reader = new CSVReader(new FileReader(CSV_file));
            String[] cell=reader.readNext();
            while( (cell= reader.readNext()) !=null){
                codigoUnico = cell[1];
                sProducto = cell[2];
                tiempoPlazo = cell[3];
                tasaAnual = cell[4];
                moneda = cell[6];
                //System.out.println(codigoUnico + "-" + sProducto + "-" + tiempoPlazo + "-" + tasaAnual);

                String accResult;
                accResult = crearCuentaBus.createCuentaBus(sProducto, tiempoPlazo, tasaAnual, codigoUnico, moneda);


                String[] res = new String[]{codigoUnico, accResult.substring(0,3), accResult.substring(3,13)};
                writer.writeNext( res );


            }

        }catch(IOException e){
            e.printStackTrace();
        }


        writer.close();



    }







    public void guardarCSV() throws IOException {
        String a = "Spain";
        String b = "ESP";

        String [] pais = {a, b};

        String archCSV = "C:\\Auto\\DataGAC\\ISO-Codes.csv";

        CSVWriter writer = new CSVWriter(new FileWriter(archCSV),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER);
        /*CSVWriter csvWriter = new CSVWriter(writer,
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);*/

        writer.writeNext(pais);

        String[] headerRecord = {"Name", "Email"};
        writer.writeNext(headerRecord);

        writer.close();
    }

}
