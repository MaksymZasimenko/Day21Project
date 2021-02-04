package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class SergeyPart1 {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/Users/srg_kosenko/Documents/Tech Lead Docs/Automation/Selenium/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

        driver.get("https://orangehrm-demo-6x.orangehrmlive.com/client/#/dashboard");

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

    }
}




