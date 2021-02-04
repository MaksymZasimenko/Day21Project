package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class TestNick {
    public static void main(String[] str){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Nick\\Desktop\\Study\\libs\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

        driver.get("https://orangehrm-demo-6x.orangehrmlive.com/client/#/dashboard");
//        Logging In
        driver.findElement(By.name("Submit")).click();
//        Navigating to News section
        driver.findElement(By.xpath("//li[@id=\"menu_admin_viewAdminModule\"]")).click();
        driver.findElement(By.xpath("//li[@id=\"menu_news_Announcements\"]")).click();
        driver.findElement(By.xpath("//a[@id=\"menu_news_viewNewsList\"]")).click();
//        Checking the news
//        Problem. Locating element by contains(text()) works fine, how to check checkbox next to it for deletion? Opt 1 - Robot.
        driver.findElement(By.xpath("//label[@for=\"checkbox_ohrmList_chkSelectRecord_52\"]")).click();
//        Deleting it
        driver.findElement(By.xpath("//a[@id=\"frmList_ohrmListComponent_Menu\"]")).click();
        driver.findElement(By.xpath("a[@id=\"newsDelete\"]")).click();
        driver.findElement(By.xpath("a[@id=\"news-delete-button\"]")).click();
        driver.close();
    }
}
