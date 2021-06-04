package org.selenium.gac.PeruRail;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static utils.Util.waitTime;

public class testPeruRail {

    static String totalUSD;
    static String totalPEN;
    static String[] dataRuta;
    static WebDriver driver;;

    @BeforeClass
    public static void setEnviroment() {
        System.setProperty("webdriver.chrome.driver","C:\\Auto\\Drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.perurail.com");
        driver.manage().window().maximize();
    }

    @Test
    public void testPeruRail() throws IOException {
        dataRuta = dataCSV("CSVDataFile\\dataPeruRail.csv","CP1");

        encontrarTickets(driver, dataRuta);
        waitTime(8);

        boolean expedicion;

        if (dataRuta[3].equals("")){
            seleccionarExpedicion(driver,dataRuta);
            expedicion = true;
            waitTime(2);
        }else{
            escogerCabinas(driver,dataRuta);
            expedicion = false;
            waitTime(2);
        }

        ingresarDatosPasajeros(driver,dataRuta,expedicion);
        waitTime(2);

        ingresarDatosPago(driver,dataRuta,expedicion);
        waitTime(2);
        validaCarrito(driver,dataRuta,expedicion);

        waitTime(4);
        driver.close();
    }


    public void validaCarrito (WebDriver driver, String[] datos, boolean expedicion){
        driver.findElement(By.xpath("//*[@id='compra']/a")).click();
        String train = "";
        String montoUSD = "";

        if (expedicion){
            //montoUSD = driver.findElement(By.xpath("//*[@id='compra']/div/div[3]/div[1]/div[1]/span[2]")).getAttribute("innerHTML");
            //totalUSD = "USD " + driver.findElement(By.xpath("//*[@id='compra']/div/div[2]/div[5]/span")).getAttribute("innerHTML");
        }else{
            train = driver.findElement(By.xpath("//*[@id='compra']/div/div[2]/div[3]/div[1]/div[2]")).getAttribute("innerHTML");
            montoUSD = driver.findElement(By.xpath("//*[@id='priceUSDrc']")).getAttribute("innerHTML");

            Assert.assertEquals(datos[3],train);
            Assert.assertEquals(totalUSD.trim(),montoUSD.trim());
        }


    }

    public void ingresarDatosPago (WebDriver driver, String[] datos, boolean expedicion) {
        if (expedicion){
            // 83 | click | css=.visa |
            driver.findElement(By.xpath("//input[@id='visa']")).click();


        }else {
            /*// 82 | mouseOut | id=btnContinuarPas |
            {
                WebElement element = driver.findElement(By.tagName("body"));
                Actions builder = new Actions(driver);
                builder.moveToElement(element, 0, 0).perform();
            }*/

            // 83 | click | css=.visa |
            driver.findElement(By.cssSelector(".visa")).click();
        }

    }
    public void ingresarDatosPasajeros (WebDriver driver, String[] datos, boolean expedicion) throws IOException {

        if (expedicion){
            ingresarExpedicionPasajeros(driver,"adulto1", "1");

            // Sin son 2 adultos
            if (datos[7].equals("2")){
                ingresarExpedicionPasajeros(driver,"adulto2", "2");
            }
            // Si es un niño adicional
            if (datos[8].equals("1")){
                ingresarDatosAdultoNinos(driver,"nino", "2");
            }
            driver.findElement(By.id("enviarPago")).click();

        }else {
            ingresarDatosAdultoNinos(driver,"adulto1", "1");

            // Sin son 2 adultos
            if (datos[7].equals("2")){
                ingresarDatosAdultoNinos(driver,"adulto2", "2");
            }

            // Si es un niño adicional
            if (datos[8].equals("1")){
                ingresarDatosAdultoNinos(driver,"nino", "2");
            }

            driver.findElement(By.id("btnContinuarPas")).click();
        }



    }

    public void ingresarDatosAdultoNinos (WebDriver driver, String pasajero, String pos) throws IOException {
        String[] datapasajero = dataCSV("CSVDataFile\\dataPasajeros.csv",pasajero);

        if (pasajero.equals("adulto2") || pasajero.equals("nino")){
            driver.findElement(By.xpath("//div[@id='itm1-2']")).click();
        }

        driver.findElement(By.id("txt_nombre[suite][cab1]["+pos+"]")).sendKeys(datapasajero[0]);
        driver.findElement(By.id("txt_apellido[suite][cab1]["+pos+"]")).sendKeys(datapasajero[1]);


        driver.findElement(By.xpath("//*[@id='txt_fecha_nacimiento[suite][cab1]["+pos+"]']")).click();
        waitTime(1);
        driver.findElement(By.linkText(datapasajero[2])).click();

        // select | id=sel_nacion[suite][cab1][1] | label=Peru
        {
            WebElement dropdown = driver.findElement(By.id("sel_nacion[suite][cab1]["+pos+"]"));
            dropdown.findElement(By.xpath("//*[@id='sel_nacion[suite][cab1]["+pos+"]']/option[170]")).click();
        }
        // click | id=sel_tpdoc
        driver.findElement(By.xpath("//*[@id='sel_tpdoc[suite][cab1]["+pos+"]']/option[2]")).click();

        // type | id=txt_nroid
        driver.findElement(By.id("txt_nroid[suite][cab1]["+pos+"]")).sendKeys( datapasajero[5] );

        // 41 | select | id=sel_sexo[suite][cab1][1] | label=Male
        {
            WebElement dropdown = driver.findElement(By.id("sel_sexo[suite][cab1]["+pos+"]"));
            String sex = datapasajero[6].equals("Male") ? "2" : "3";
            dropdown.findElement(By.xpath("//*[@id='sel_sexo[suite][cab1]["+pos+"]']/option["+sex+"]")).click();
        }

        if (pasajero.contains("adulto")){
            // type | id=txt_telefono / txt_mail / txt_mail_conf
            driver.findElement(By.id("txt_telefono[suite][cab1]["+pos+"]")).sendKeys(datapasajero[7] );
            driver.findElement(By.id("txt_mail[suite][cab1]["+pos+"]")).sendKeys(datapasajero[8]);
            driver.findElement(By.id("txt_mail_conf[suite][cab1]["+pos+"]")).sendKeys(datapasajero[8]);
        }

        waitTime(2);
    }

    public void seleccionarExpedicion (WebDriver driver, String[] datos) {


        driver.findElement(By.xpath("//*[@id='div_2020023714_20']")).click();
        waitTime(1);
        if (datos[0].equals("Round trip")){
            driver.findElement(By.xpath("//*[@id='div_2021026391_11']")).click();
            waitTime(1);
        }
        driver.findElement(By.xpath("//*[@id='formTrenSeleccionar']/div/div/input")).click();

    }


    public void ingresarExpedicionPasajeros (WebDriver driver, String pasajero, String pos) throws IOException {
        String[] datapasajero = dataCSV("CSVDataFile\\dataPasajeros.csv",pasajero);

        if (pasajero.equals("adulto2") || pasajero.equals("nino")){
            driver.findElement(By.xpath("//*[@id='formPasajeroRegistrar']/div[2]/div[1]")).click();
        }

        driver.findElement(By.xpath("//*[@id='formPasajero"+pos+"-nomPasajero']")).sendKeys(datapasajero[0]);
        driver.findElement(By.xpath("//*[@id='formPasajero"+pos+"-apePasajero']")).sendKeys(datapasajero[1]);

        driver.findElement(By.xpath("//*[@id='formPasajero"+pos+"-fecNacimiento']")).click();
        driver.findElement(By.xpath("//a[contains(text(),'17')]")).click();

        //driver.findElement(By.xpath("//select[@id='formPasajero"+pos+"-idPais']")).click();
        driver.findElement(By.xpath("//*[@id='formPasajero"+pos+"-idPais']/option[170]")).click();

        driver.findElement(By.xpath("//*[@id='formPasajero"+pos+"-idDocumentoIdentidad']")).click();
        driver.findElement(By.xpath("//*[@id='formPasajero"+pos+"-idDocumentoIdentidad']/option[2]")).click();

        driver.findElement(By.xpath("//input[@id='formPasajero"+pos+"-numDocumentoIdentidad']")).sendKeys(datapasajero[5]);

        {
            WebElement dropdown = driver.findElement(By.id("formPasajero"+pos+"-idSexo"));
            String sex = datapasajero[6].equals("Male") ? "2" : "3";
            dropdown.findElement(By.xpath("//*[@id='formPasajero"+pos+"-idSexo']/option["+sex+"]")).click();
        }
        if (pasajero.contains("adulto")){
            driver.findElement(By.xpath("//input[@id='formPasajero"+pos+"-numTelefono']")).sendKeys(datapasajero[7]);
            driver.findElement(By.xpath("//input[@id='formPasajero"+pos+"-desEmail']")).sendKeys(datapasajero[8]);
            driver.findElement(By.xpath("//input[@id='formPasajero"+pos+"-desEmailConfirmacion']")).sendKeys(datapasajero[8]);
        }

        waitTime(2);

    }

    public void escogerCabinas (WebDriver driver, String[] datos) {
        // select | name=selectRooms[suite] | 1 CABIN
        {
            WebElement dropdown = driver.findElement(By.name("selectRooms[suite]"));
            dropdown.findElement(By.xpath("//option[. = '" + datos[6] + " CABIN']")).click();
        }

        // select | name=selectRooms[suite] | Adulto
        int numAdultos = 0;
        numAdultos = Integer.parseInt(datos[7]) + 1;
        driver.findElement(By.xpath("//*[@id='suite']/div/div[2]/div[1]/select/option["+numAdultos+"]")).click();

        if (datos[8].equals("1")){
            // select | name=selectRooms[suite] | Niño
            int numNinos = Integer.parseInt(datos[8]) + 1;
            driver.findElement(By.xpath("//*[@id='suite']/div/div[2]/div[2]/select/option["+numNinos+"]")).click();
        }

        totalUSD = driver.findElement(By.xpath("//*[@id='priceUSD']")).getText();
        totalPEN = driver.findElement(By.xpath("//*[@id='pricePEN']")).getText();

        driver.findElement(By.id("continuar_bae")).click();

    }

    public void encontrarTickets (WebDriver driver, String[] datos) {

        // click | Selecciona elemento tipo viaje |
        if (datos[0].equals("One Way")){
            //System.out.println(datos[0]);
            driver.findElement(By.cssSelector(".input-radio:nth-child(2) span")).click();
        }else {
            driver.findElement(By.cssSelector(".input-radio:nth-child(1) span")).click();
        }

        // select | id=destinoSelect
        {
            WebElement dropdown = driver.findElement(By.id("destinoSelect"));
            dropdown.findElement(By.xpath("//option[. = '" + datos[1] + "']")).click();
        }

        // select | id=rutaSelect
        {
            WebElement dropdown = driver.findElement(By.id("rutaSelect"));
            dropdown.findElement(By.xpath("//option[. = '" + datos[2] + "']")).click();
        }

        // select | id=cbTrenSelect
        Boolean DisplayTrenSelect = driver.findElement(By.xpath("//*[@id='cbTrenSelect']")).isDisplayed();

        if (DisplayTrenSelect) {
            {
                WebElement dropdown = driver.findElement(By.id("cbTrenSelect"));
                dropdown.findElement(By.xpath("//option[. = '" + datos[3] + "']")).click();
            }
        }

        // click | id=salida |
        driver.findElement(By.id("salida")).click();
        waitTime(1);

        //----------------------------------------------------
        if (datos[4].equals("23")){
            driver.findElement(By.xpath("//*[@id='salida']")).click();
            driver.findElement(By.xpath("//a[contains(text(),'23')]")).click();
        }else if (datos[4].equals("31")){
            driver.findElement(By.xpath("//*[@id='salida']")).click();
            driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
            driver.findElement(By.xpath("//a[contains(text(),'31')]")).click();
        }

        if (datos[0].equals("Round trip")){
            driver.findElement(By.id("regreso")).click();
            waitTime(1);
            driver.findElement(By.xpath("//*[@id='regreso']")).click();
            driver.findElement(By.xpath("//a[contains(text(),'28')]")).click();
        }


        //----------------------------------------------------
        waitTime(2);
        // 1click | Buscar |
        driver.findElement(By.cssSelector("span:nth-child(1) > img")).click();
    }

    public void capturaPantalla (WebDriver driver, String nombre, String ruta) throws IOException {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(ruta+"\\"+nombre+".png"));
    }

    public final String[] dataCSV (String nombreArchivo, String Pclave) throws IOException {
        String cadena = "";
        File f = new File(nombreArchivo);
        FileReader fr = new FileReader(f.getAbsolutePath());
        BufferedReader br = new BufferedReader(fr);

        while((cadena = br.readLine())!=null) {

            if (cadena.startsWith(Pclave)) {
                break;
            }
            cadena = "";
        }
        br.close();
        fr.close();

        return cadena.substring(Pclave.length()+1).split(",");

    }

}
