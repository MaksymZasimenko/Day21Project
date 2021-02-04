package test;

import com.google.common.annotations.VisibleForTesting;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestMZ {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\zasim\\OneDrive\\Desktop\\Automation\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

        //Disregard this part , it is only for me to get to my task page
        driver.get("https://orangehrm-demo-6x.orangehrmlive.com/client/#/dashboard");
        driver.findElement(By.id("loginAsButtonGroup")).click();
        List<WebElement> users = driver.findElements(By.className("login-as"));
        users.get(1).click();
        driver.findElement(By.xpath("//span[contains(text(), 'Admin')]")).click();
        driver.findElement(By.xpath("//span[contains(text(), 'Announcements')]")).click();
        driver.findElement(By.id("menu_news_viewNewsList")).click();

        //Here is actual task
        //Add title
        driver.switchTo().frame("noncoreIframe");
        driver.findElement(By.xpath("//i[@class='large material-icons']")).click();
        driver.findElement(By.id("news_topic")).sendKeys("Congratulations Anna");
        Thread.sleep(2000);

        //Add news
        driver.switchTo().frame("news_description_ifr");
        driver.findElement(By.id("tinymce")).click();
        driver.findElement(By.id("tinymce")).sendKeys("Promotion was awarded to Anna on 1/7/2020");

        //Click next button
        driver.switchTo().parentFrame();
        driver.findElement(By.xpath("//div[@class='row']//button[@id='nextBtn']")).click();

        //Publish to all other users
        driver.findElement(By.xpath("//label[contains(text(), 'Publish To - All User Roles')]")).click();

        //Click publish
        driver.findElement(By.xpath("//button[contains(text(), 'Publish')]")).click();
        Thread.sleep(2000);


        // ------------- Tim part step 10 - 12 -----------

        driver.findElement(By.xpath("//span[@id='account-job']")).click();
        driver.findElement(By.xpath("//a[@id='logoutLink']")).click();

        // Log in as 1st Level Supervisor
        driver.findElement(By.xpath("//button[@class='btn btn-primary dropdown-toggle']")).click();
        driver.findElement(By.xpath("//a[text()='1st Level Supervisor']")).click();

        // Go to News section
        driver.findElement(By.xpath("//span[text()='Announcements']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//span[text()='News']")).click();

        driver.findElement(By.xpath("//div[@id='header']")).click();
        Thread.sleep(2000);

        // Verify Topic and Description values
        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='header']")).getText(), "Congratulations Anna");
        Assert.assertEquals(driver.findElement(By.xpath("(//div[@class='html-content']//p)[1]")).getText(), "Promotion was awarded to Anna on 1/7/2020");

        driver.findElement(By.xpath("//span[@id='account-job']")).click();
        driver.findElement(By.xpath("//a[@id='logoutLink']")).click();



        Thread.sleep(2000);
        driver.close();
    }
}
