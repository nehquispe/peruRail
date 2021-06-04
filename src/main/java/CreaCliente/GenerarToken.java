package CreaCliente;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class GenerarToken {

    public static String createAccessToken(String user, String password) {

        String baseUrl = "https://eu2-ibk-apm-uat-ext-001.azure-api.net";

        String body = "{\"username\":\""+ user+"\",\"password\":\"" + password+"\"}";
        try {
            return "Bearer " + given()
                    .header("Authorization", "Basic c3BhOnBhc3N3b3Jk")
                    .contentType(ContentType.JSON)
                    .header("Ocp-Apim-Subscription-Key", "2bf94a64237c45f7a88b865777de9b69")
                    .body(body)
                    .post(baseUrl + "/api/uat/login")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .response()
                    .jsonPath()
                    .getString("access_token");


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
