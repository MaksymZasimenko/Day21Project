package datadriven.base;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class TestBase {

/*
//     WebDriver
//    Logs
//    Properties
//    Excel
//    Db
//    Mail
//    Extent Reports
*/

    public static ThreadLocal<RemoteWebDriver> dr = new ThreadLocal<RemoteWebDriver>();
    public RemoteWebDriver driver = null;
    public Properties OR = new Properties();
    public Properties Config = new Properties();
    public FileInputStream fis;
    public Logger log = Logger.getLogger("evpinoyLogger");
    public WebDriverWait wait;


    @BeforeSuite
    public void setUp() throws IOException {
        if(driver==null) {
            fis = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/properties/Config.properties");
            Config.load(fis);
            fis = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/properties/OR.properties");
            OR.load(fis);
        }
    }

    public WebDriver getDriver() {
        return dr.get();
    }

    public void  serWebDriver(RemoteWebDriver driver) {
        dr.set(driver);
    }

    public void openBrowser(String browser) throws MalformedURLException {
        DesiredCapabilities cap=null;
        if(browser.equalsIgnoreCase("firefox")) {
            cap = DesiredCapabilities.firefox();
            cap.setBrowserName("firefox");
            cap.setPlatform(Platform.ANY);
        }else if(browser.equals("chrome")) {
            cap = DesiredCapabilities.chrome();
            cap.setBrowserName("Chrome");
            cap.setPlatform(Platform.ANY);
        }
        driver = new RemoteWebDriver(new URL(""),cap);
        serWebDriver(driver);
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        getDriver().manage().window().maximize();
    }

    public void navigate(String url){
        getDriver().get(Config.getProperty(url));
    }

    public void click(String locator) {

        getDriver().findElement(By.xpath(OR.getProperty(locator))).click();
       // test.log(LogStatus.INFO,"Clicking on :" + locator);
    }

    public String getTitle() {
        return getDriver().getTitle();
    }

    static WebElement dropdown;

    public void select(String locator, String value) {

        if (locator.endsWith("_CSS")) {
            dropdown = getDriver().findElement(By.cssSelector(OR.getProperty(locator)));
        } else if (locator.endsWith("_XPATH")) {
            dropdown = getDriver().findElement(By.xpath(OR.getProperty(locator)));
        } else if (locator.endsWith("_ID")) {
            dropdown = getDriver().findElement(By.id(OR.getProperty(locator)));
        }

        Select select = new Select(dropdown);
        select.selectByVisibleText(value);

      //  test.log(LogStatus.INFO,"Selecting from dropdown: " + locator + " and picked -  " + value);
    }

    public void type(String locator,String value) {

        if (locator.endsWith("_CSS")) {
            getDriver().findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
        } else if (locator.endsWith("_XPATH")) {
            getDriver().findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
        } else if (locator.endsWith("_ID")) {
            getDriver().findElement(By.id(OR.getProperty(locator))).sendKeys(value);
        }

       // test.log(LogStatus.INFO,"Typing in locator: " + locator + " and entered" + value);
    }

    public boolean isElementPresent(By by) {
        try {
            getDriver().findElement(by); return true;
        }catch (NoSuchElementException e) {
            return false;
        }
    }

}
