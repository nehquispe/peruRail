package CreaCliente;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class CrearCuentaBus {

    public static String createCuentaBus(String sProducto,
                                         String tiempoPlazo,
                                         String tasaAnual,
                                         String codigoUnico,
                                         String moneda) {

        String baseUrl = "https://10.11.32.73:7020/ibk/uat/api/cliente.aperturaCuenta/v1.0";

        String body = createCuentaBody(sProducto, tiempoPlazo, tasaAnual, codigoUnico, moneda);

        Response response;
        //System.out.println(body);

        try {
            response = given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Basic dUJzZUFzaUFBZG06SWJrYWRtMTgr")
                    .header("X-IBM-Client-Id", "9e48c834-31ba-4849-82e3-bada86634d22")
                    .body(body)
                    .relaxedHTTPSValidation()
                    .post(baseUrl)
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .response();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        String account;
        account = response.jsonPath().getString("MessageResponse.Body.aperturaCuentaResponse.nroCuenta");

        System.out.println(account);
        //System.out.println(accResult);

        //System.out.println(response.jsonPath().getString(""));


        return account;
    }

    public static String createCuentaBody(String sProducto,
                                          String tiempoPlazo,
                                          String tasaAnual,
                                          String codigoUnico,
                                          String moneda){

        String body = "{\n" +
                "\t\"MessageRequest\": {\n" +
                "\t\t\"Header\": {\n" +
                "\t\t\t\"HeaderRequest\": {\n" +
                "\t\t\t\t\"request\": {\n" +
                "\t\t\t\t\t\"serviceId\": \"SRM\",\n" +
                "\t\t\t\t\t\"consumerId\": \"ASI\",\n" +
                "\t\t\t\t\t\"moduleId\": \"ASSI\",\n" +
                "\t\t\t\t\t\"channelCode\": \"15\",\n" +
                "\t\t\t\t\t\"messageId\": \"63557004d698a2c4\",\n" +
                "\t\t\t\t\t\"timestamp\": \"2021-04-11T05:27:41.721-05:00\",\n" +
                "\t\t\t\t\t\"countryCode\": \"PE\",\n" +
                "\t\t\t\t\t\"groupMember\": \"08\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"identity\": {\n" +
                "\t\t\t\t\t\"netId\": \"AV\",\n" +
                "\t\t\t\t\t\"userId\": \"XT8052\",\n" +
                "\t\t\t\t\t\"supervisorId\": \"XT8052\",\n" +
                "\t\t\t\t\t\"deviceId\": \"00.00.00.00\",\n" +
                "\t\t\t\t\t\"serverId\": \"ASSICLOUD\",\n" +
                "\t\t\t\t\t\"branchCode\": \"100\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"Body\": {\n" +
                "\t\t\t\"aperturaCuenta\": {\n" +
                "\t\t\t\t\"tipoFuncion\": \"03\",\n" +
                "\t\t\t\t\"codigoRed\": \"AV\",\n" +
                "\t\t\t\t\"aplicativo\": \"ST\",\n" +
                "\t\t\t\t\"banco\": \"03\",\n" +
                "\t\t\t\t\"producto\": \"008\",\n" +
                "\t\t\t\t\"sProducto\": \""+ sProducto + "\",\n" +
                "\t\t\t\t\"moneda\": \""+moneda+"\",\n" +
                "\t\t\t\t\"tipoPlazo\": \"D\",\n" +
                "\t\t\t\t\"tiempoPlazo\": \""+tiempoPlazo+"\",\n" +
                "\t\t\t\t\"tasaAnual\": "+ tasaAnual +",\n" +
                "\t\t\t\t\"conector\": \"IND\",\n" +
                "\t\t\t\t\"ubicaTD\": \"N\",\n" +
                "\t\t\t\t\"codFondo\": \"0\",\n" +
                "\t\t\t\t\"clientes\": {\n" +
                "\t\t\t\t\t\"cliente\": [\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"posCliente\": \"1\",\n" +
                "\t\t\t\t\t\t\t\"codigoUnico\": \"00" + codigoUnico +"\"\n" +
                "\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t]\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"validaTrjDebito\": \"N\",\n" +
                "\t\t\t\t\"bloqueoCuenta\": \"N\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";

        return body;
    }


}
