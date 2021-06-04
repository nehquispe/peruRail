package org.selenium.gac.PeruRail;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Test1 {

    public static void main (String[] args) throws IOException {
        test2();
    }

    public static void test1() throws IOException {
        System.setProperty("webdriver.chrome.driver","C:\\Auto\\Drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.google.com/");

        String nombre="AXQ";
        String ruta="C:\\Users\\everis\\Downloads\\Brevete\\Test\\";
        NombreMetodo(driver,nombre,ruta);
    }

    public static void test2() throws IOException {
        String nombreArchivo = "C:\\Users\\everis\\Downloads\\Brevete\\Test\\TEST2.txt";
        try{
            String rtn = NombreMetodo2(nombreArchivo,"lx");
            System.out.println(rtn);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public static void NombreMetodo (WebDriver driver, String nombre, String ruta) throws IOException
    {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(ruta+"\\"+nombre+".png"));
    }

    public static final String NombreMetodo2 (String nombreArchivo, String Pclave) throws IOException {
        String cadena = "";
        File f = new File(nombreArchivo);
        FileReader fr = new FileReader(f.getAbsolutePath());
        BufferedReader br = new BufferedReader(fr);

        while((cadena = br.readLine())!=null) {
            System.out.println(cadena);
            if (cadena.startsWith(Pclave)) {
                break;
            }
            cadena = "";
        }
        br.close();
        fr.close();


        return cadena.substring(Pclave.length()+1);

    }



}
