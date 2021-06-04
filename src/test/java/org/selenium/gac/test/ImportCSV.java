package org.selenium.gac.test;

import com.opencsv.CSVReader;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.selenium.gac.utils.Util.waitTime;

public class ImportCSV {



        public static void main (String[] args){

            System.setProperty("webdriver.chrome.driver","C:\\Auto\\Drivers\\chromedriver.exe");
            String baseurl = "https://www.google.com.pe";
            String CSV_file = "C:\\Auto\\Drivers\\TestDataGAC.csv";

            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
            //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);


            driver.get(baseurl);




            //Read CSV file
            CSVReader reader = null;
            try{
                reader = new CSVReader(new FileReader(CSV_file));
                String[] cell=reader.readNext();

                while( (cell= reader.readNext()) !=null){

                    int i=0;
                    
                    //Busca Palabra
                    String keyword = cell[i] + " " + cell[i+1];
                    driver.findElement(By.name("q")).sendKeys(keyword);
                    driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
                    waitTime(3);


                    //regresan home
                    driver.findElement(By.cssSelector(".logo img")).click();

                    i++;
                }

            }catch(IOException e){
                e.printStackTrace();
            }



        }


}
