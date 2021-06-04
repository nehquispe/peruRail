package CreaCliente;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;




public class ConsultaGarantia {




    public static void main(String[] args) throws IOException {

        String token = GenerarToken.createAccessToken("B36199","Interbank7.");

        //Read CSV file
        CSVReader reader = null;
        String CSV_file = "C:\\Auto\\DataGAC\\FFMM\\DataGAC.csv";

        String codigoUnico="";


        String archCSV = "C:\\Auto\\DataGAC\\FFMM\\Response\\Externo.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(archCSV),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER);



        try{

            reader = new CSVReader(new FileReader(CSV_file));
            String[] cell=reader.readNext();

            while( (cell= reader.readNext()) !=null){
                codigoUnico = cell[0];

                String accResult;
                accResult = ConsultaGarantia_API.consultaGarantia(codigoUnico, token);


                System.out.println(codigoUnico + " - " + accResult);

                String[] res = new String[]{codigoUnico,accResult};
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
