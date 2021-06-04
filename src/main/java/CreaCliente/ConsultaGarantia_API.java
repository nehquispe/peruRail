package CreaCliente;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;


public class ConsultaGarantia_API {

    public static String consultaGarantia(String codigoUnico, String token) {

        String baseUrl = "https://eu2-ibk-apm-uat-ext-001.azure-api.net/assi-common/common/customers/"+codigoUnico+"/guarantees";

        Response response;

        try {
            response = given()
                    .contentType(ContentType.JSON)
                    .header("Ocp-Apim-Subscription-Key", "a0dc0cb2408f450997665140f575df37")
                    .header("application", "ASSI")
                    .header("storeCode", "100")
                    .header("channelSale", "1")
                    .header("user", "XT8052")
                    .header("company", "INTERBANK")
                    .header("Authorization", "Bearer "+ token)
                    .relaxedHTTPSValidation()
                    .get(baseUrl)
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .response();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //System.out.println(response.jsonPath().getString(""));


        return response.jsonPath().getString("");
    }



}
