package org.selenium.gac.test;
import com.opencsv.CSVReader;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileReader;
import java.io.IOException;

import static org.selenium.gac.utils.Util.waitTime;

//import org.openqa.selenium.firefox.FirefoxDriver;
//comment the above line and uncomment below line to use Chrome

public class TituloValorCB {

    public static void main(String[] args) {
        // declaration and instantiation of objects/variables
        //System.setProperty("webdriver.gecko.driver","C:\\geckodriver.exe");
        //WebDriver driver = new FirefoxDriver();
        //comment the above 2 lines and uncomment below 2 lines to use Chrome
        System.setProperty("webdriver.chrome.driver","C:\\Auto\\Drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // 1 | open | /sda/ |
        String baseUrl = "http://intranetib/sda/";
        driver.get(baseUrl);


        // 3 | type | id=inpuser | XT8052
        driver.findElement(By.id("inpuser")).sendKeys("XT8052");
        // 4 | type | id=inp_clave | Interbank1.
        driver.findElement(By.id("inp_clave")).sendKeys("Interbank1.");
        // 5 | click | name=selambiente |
        driver.findElement(By.name("selambiente")).click();
        // 6 | select | name=selambiente | label=Pruebas
        {
            //var dropdown = driver.findElement(By.name("selambiente"));
            WebElement dropdown = driver.findElement(By.name("selambiente"));
            dropdown.findElement(By.xpath("//option[. = 'Pruebas']")).click();
        }
        // 7 | click | id=Aceptar |
        driver.findElement(By.id("Aceptar")).click();

        //--------------------------------------------------------------------
        //--------------------------------------------------------------------
        //--------------------------------------------------------------------


        // 8 | click | linkText=garantías y custodia | 
        driver.findElement(By.linkText("garantías y custodia")).click();


        //--------------------------------------------------------------------
        //--------------------------------------------------------------------
        //--------------------------------------------------------------------

        // 9 | click | id=ctl00_tvMenuPrincipalt4 |
        driver.findElement(By.id("ctl00_tvMenuPrincipalt4")).click();
        // 10 | selectFrame | index=0 |
        driver.switchTo().frame(0);
        // 11 | click | id=ddlTipoGarantia |
        driver.findElement(By.id("ddlTipoGarantia")).click();
        // 12 | select | id=ddlTipoGarantia | label=Garantía Título Valor
        {
            var dropdown = driver.findElement(By.id("ddlTipoGarantia"));
            dropdown.findElement(By.xpath("//option[. = 'Garantía Título Valor']")).click();
        }
        waitTime(1);
        var buttomIR =driver.findElement(By.name("btnIr"));
        buttomIR.click();

        //--------------------------------------------------------------------
        //--------------------------------------------------------------------
        //--------------------------------------------------------------------
        String CSV_file = "C:\\Auto\\DataGAC\\DataCUconCuentas.csv";

        String codunico="";
        String storeCode="";
        String accountnumber="";
        String moneda="";


        //Read CSV file
        CSVReader reader = null;
        try{
            reader = new CSVReader(new FileReader(CSV_file));
            String[] cell=reader.readNext();

            while( (cell= reader.readNext()) !=null){
                int i=0;

                codunico = cell[i];
                storeCode = cell[i+1];
                accountnumber = cell[i+2];
                moneda = cell[i+3];
                System.out.println(codunico + " - " + storeCode + " - " + accountnumber + " - " + moneda);

                waitTime(2);
                driver.findElement(By.id("ctl00_cphPrincipal_tabGarantias_tabDatosGarantias_UcBuscarCliente1_txtCUCliente")).click();
                driver.findElement(By.id("ctl00_cphPrincipal_tabGarantias_tabDatosGarantias_UcBuscarCliente1_txtCUCliente")).sendKeys(codunico);
                waitTime(2);
                driver.findElement(By.id("ctl00_cphPrincipal_tabGarantias_tabDatosGarantias_UcBuscarCliente1_txtCUCliente")).sendKeys(Keys.ENTER);

                waitTime(2);
                driver.findElement(By.id("ctl00_cphPrincipal_tabGarantias_tabDatosGarantias_UcBuscarTienda1_txtCodOficina")).sendKeys(storeCode);
                driver.findElement(By.id("ctl00_cphPrincipal_tabGarantias_tabDatosGarantias_UcBuscarTienda1_txtCodOficina")).sendKeys(Keys.ENTER);

                waitTime(2);
                driver.findElement(By.id("ctl00_cphPrincipal_tabGarantias_tabDatosGarantias_txtNROVALORADO")).sendKeys(accountnumber);
                driver.findElement(By.id("ctl00_cphPrincipal_tabGarantias_tabDatosGarantias_txtNROVALORADO")).sendKeys(Keys.ENTER);

                waitTime(2);

                if(moneda.equals("001")){
                    {
                        var dropdownMoneda = driver.findElement(By.id("ctl00_cphPrincipal_tabGarantias_tabDatosGarantias_UcBuscarCuenta1_ddlMonedaCta"));
                        dropdownMoneda.findElement(By.xpath("//*[@id='ctl00_cphPrincipal_tabGarantias_tabDatosGarantias_UcBuscarCuenta1_ddlMonedaCta']/option[2]")).click();
                    }
                }else {
                    {
                        var dropdownMoneda = driver.findElement(By.id("ctl00_cphPrincipal_tabGarantias_tabDatosGarantias_UcBuscarCuenta1_ddlMonedaCta"));
                    }
                }

                waitTime(2);
                driver.findElement(By.id("ctl00_cphPrincipal_tabGarantias_tabDatosGarantias_UcBuscarCuenta1_txtNroOficinaCta")).sendKeys(storeCode);
                driver.findElement(By.id("ctl00_cphPrincipal_tabGarantias_tabDatosGarantias_UcBuscarCuenta1_txtNumeroCta")).sendKeys(accountnumber);
                driver.findElement(By.id("ctl00_cphPrincipal_tabGarantias_tabDatosGarantias_UcBuscarCuenta1_txtNumeroCta")).sendKeys(Keys.ENTER);

                waitTime(2);
                driver.findElement(By.id("__tab_ctl00_cphPrincipal_tabGarantias_tabDeuda")).click();
                {
                    var dropdownMoneda = driver.findElement(By.id("ctl00_cphPrincipal_tabGarantias_tabDeuda_UcDeudaGarantia1_cmbTipoCobertura"));
                    dropdownMoneda.findElement(By.xpath("//*[@id='ctl00_cphPrincipal_tabGarantias_tabDeuda_UcDeudaGarantia1_cmbTipoCobertura']/option[2]")).click();
                }
                waitTime(2);
                Alert alert = driver.switchTo().alert();
                alert.accept();

                waitTime(2);
                driver.findElement(By.id("ctl00_cphPrincipal_tabGarantias_tabDeuda_UcDeudaGarantia1_chkMismo")).click();

                waitTime(2);
                driver.findElement(By.name("ctl00$cphPrincipal$btnRegistrar")).click();

                waitTime(2);
                Alert alert2 = driver.switchTo().alert();
                alert2.accept();

                waitTime(2);

                //--------------------------------------------------------------------
                //--------------------------------------------------------------------
                //--------------------------------------------------------------------

                // 9 | click | id=ctl00_tvMenuPrincipalt4 |
                driver.findElement(By.id("ctl00_tvMenuPrincipalt4")).click();
                // 10 | selectFrame | index=0 |
                driver.switchTo().frame(0);
                // 11 | click | id=ddlTipoGarantia |
                driver.findElement(By.id("ddlTipoGarantia")).click();
                // 12 | select | id=ddlTipoGarantia | label=Garantía Título Valor
                {
                    var dropdown = driver.findElement(By.id("ddlTipoGarantia"));
                    dropdown.findElement(By.xpath("//option[. = 'Garantía Título Valor']")).click();
                }
                waitTime(1);

                driver.findElement(By.name("btnIr")).click();

            }


        }catch(IOException e){
            e.printStackTrace();
        }


        waitTime(2);
        driver.close();

    }

}
