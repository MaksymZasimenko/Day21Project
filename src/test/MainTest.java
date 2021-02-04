package test;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class MainTest {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/srg_kosenko/Documents/Tech Lead Docs/Automation/Selenium/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

        driver.get("https://orangehrm-demo-6x.orangehrmlive.com/client/#/dashboard");
        //Sergey STEPS 1 - 3 -----------------------------------------------------------------
        //Login as Admin
        driver.findElement(By.id("btnLogin")).click();
        //Open Admin -> Announcements -> News

        driver.findElement(By.id("menu_admin_viewAdminModule")).click();
        driver.findElement(By.id("menu_news_Announcements")).click();
        driver.findElement(By.id("menu_news_viewNewsList")).click();

        driver.switchTo().frame(driver.findElement(By.id("noncoreIframe")));

        List<WebElement> announcements = driver.findElements(By.xpath("//tr[@class='dataRaw']/td[2]"));
        List<WebElement> publishDate = driver.findElements(By.xpath("//tr[@class='dataRaw']//td[3]"));
        List<WebElement> publishUserRole = driver.findElements(By.xpath("//tr[@class='dataRaw']//td[6]"));
        List<WebElement> attachment = driver.findElements(By.xpath("//tr[@class='dataRaw']//td[7]/i"));

        // Creating Map and Inner List
        Map<String, List<String>> newsList = new LinkedHashMap<>();
        List<String> tempList = new LinkedList<>();

        // Handeling Attacments presence (via color checking)
        List<String> attachStr = new LinkedList<>();
        for (int i = 0; i < attachment.size(); i++) {
            if (attachment.get(i).getCssValue("Color").equals("rgb(201, 201, 201)")) {
                attachStr.add("Attachment not Present");
            } else {
                attachStr.add("Attachment is Present");
            }
        }

        // Add elements to List
        for (int i = 0; i < announcements.size(); i++) {
            tempList.add(publishDate.get(i).getText() + " | " + publishUserRole.get(i).getText() + " | " + attachStr.get(i));
        }
        // Add elements to Map
        for (int i = 0; i < announcements.size(); i++) {
            if (!newsList.containsKey(announcements.get(i).getText())) {
                newsList.put(announcements.get(i).getText(), Collections.singletonList(tempList.get(i)));
            } else {
                newsList.put(announcements.get(i).getText() + " *Duplicated Value*", Collections.singletonList(tempList.get(i)));
            }
        }
        // Print count of news
        System.out.println("Size of newsList " + newsList.size());

        // Print Map
        for (String key : newsList.keySet()) {
            System.out.println(key + " | " + newsList.get(key));
        }

        //Add title
//        driver.switchTo().frame("noncoreIframe");
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

    }
}




