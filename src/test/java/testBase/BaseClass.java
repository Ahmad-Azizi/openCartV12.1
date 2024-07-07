package testBase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseClass {
    public static WebDriver driver;
    public Logger logger;
    public Properties p;

    @BeforeClass(groups = {"Sanity", "Regression","Master"})
    @Parameters({"os", "browser"})
    public void setup(String os, String br) throws IOException {


        logger =  LogManager.getLogger(this.getClass());

        //Loading config.properties file

        FileReader file = new FileReader(".//src/test/resources/config.properties");

        p = new Properties();
        p.load(file);

        if(p.getProperty("execution_env").equalsIgnoreCase("remote")){
            DesiredCapabilities capabilities= new DesiredCapabilities();

            if(os.equalsIgnoreCase("mac")){
                capabilities.setPlatform(Platform.MAC);
            }
            else if (os.equalsIgnoreCase("safari")) {
                capabilities.setPlatform(Platform.MAC);
            }else {
                System.out.printf("NO os matching");
                return;
            }
            switch (br.toLowerCase()){
                case "chrome": capabilities.setBrowserName("chrome");
                    break;
                case "firefox": capabilities.setBrowserName("firefox");
                    break;
                default:
                    System.out.printf("No matching browser");
                    return;
            }
            driver = new RemoteWebDriver(new URL("http://10.0.0.101:4444/wd/hub"),capabilities);

        }
        if(p.getProperty("execution_env").equalsIgnoreCase("local")){
            switch ((br.toLowerCase())){
                case "chrome" : driver= new ChromeDriver();break;
                case "safari" : driver= new SafariDriver();break;
                case "firefox": driver= new FirefoxDriver();break;
                default:
                    System.out.printf("rivaled browser name...");
            }
        }

        switch ((br.toLowerCase())){
            case "chrome" : driver= new ChromeDriver();break;
            case "safari" : driver= new SafariDriver();break;
            case "firefox": driver= new FirefoxDriver();break;
            default:
                System.out.printf("rivaled browser name...");
                return;

        }
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get(p.getProperty("appUrl"));//reading url from properties file

        driver.get("https://tutorialsninja.com/demo/index.php?route=common/home");
        driver.manage().window().maximize();
    }
    public String randomeString(){
        String generatedString= RandomStringUtils.randomAlphabetic(5);
        return generatedString;
    }
    public String randomeNumber(){
        String generatedNumber=RandomStringUtils.randomAlphabetic(5);
        return generatedNumber;
    }
    public String randomeAlphaNumeric() {
        String generatedString = RandomStringUtils.randomAlphabetic(5);
        String generatedNumber = RandomStringUtils.randomNumeric(3);
        return (generatedString + "@" + generatedNumber);
    }
    public String captureScreen(String tname) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(sourceFile, new File("src/test/resources/screenshots/" + tname +".JPEG"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sourceFile.renameTo(sourceFile);
        String targetFilePath = "jpeg";
        return targetFilePath;
    }
    @AfterClass(groups = {"Sanity", "Regression","Master"})
    public void tearDown(){
        driver.quit();
    }
}