package test;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

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


         //Ermek's task #8  Store news count before update===

        int newsCountBeforeUpdate = newsList.size();

        // Print Map
        for (String key : newsList.keySet()) {
            System.out.println(key + " | " + newsList.get(key));
        }
        System.out.println("News count before update: " + newsCountBeforeUpdate);// Task#8

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
        Thread.sleep(4000);

        //Store news count after update - Task #8
        List<WebElement>newsAfterUpdate = driver.findElements(By.xpath("//table[@id='resultTable']//td//a[@class='newsTopic']"));
        System.out.println(newsAfterUpdate.size());
        int newsCountAfterUpdate = newsAfterUpdate.size();


        Assert.assertTrue(newsCountAfterUpdate > newsCountBeforeUpdate, "New messages was not added. Test failed.");
        System.out.println("News count after update: " + newsCountAfterUpdate);
        String expMessageText = "Congratulations Anna";
        Thread.sleep(2000);
        String actMessageText = newsAfterUpdate.get(0).getText();
        Assert.assertEquals(actMessageText, expMessageText, "Actual message is not correct. Test failed");

        // ------------- Tim part step 10 - 12 -----------

        // log off from Admin
        driver.findElement(By.xpath("//span[@id='account-job']")).click();
        driver.findElement(By.xpath("//a[@id='logoutLink']")).click();

        // Log in as 1st Level Supervisor
        driver.findElement(By.xpath("//button[@class='btn btn-primary dropdown-toggle']")).click();
        driver.findElement(By.xpath("//a[text()='1st Level Supervisor']")).click();

        // Go to News section
        driver.findElement(By.xpath("//span[text()='Announcements']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//span[text()='News']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@id='header']")).click();
        Thread.sleep(2000);

        // Verify Topic and Description values
        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='header']")).getText(), "Congratulations Anna");
        Assert.assertEquals(driver.findElement(By.xpath("(//div[@class='html-content']//p)[1]")).getText(), "Promotion was awarded to Anna on 1/7/2020");

        driver.findElement(By.xpath("//span[@id='account-job']")).click();
        driver.findElement(By.xpath("//a[@id='logoutLink']")).click();

        Thread.sleep(2000);







        /////// Alisher part ----->16 - 17 <------////////

        //Verify that item doesn't exist in the table anymore
//        driver.switchTo().frame("noncoreIframe");
//        List<WebElement> list = driver.findElements(By.xpath("//table[@class='highlight bordered']//tbody//td"));
//        for (int i = 0; i < list.size(); i++)
//            Assert.assertTrue(!list.get(i).getText().contains("Congratulations Anna Promotion was awarded to Anna on 1/7/2020"));
//    }


       ////Verify row count is one less after delete
//        List<WebElement> newsCountBeforeUpdate = driver.findElements(By.xpath("//table[@id='resultTable']//td//a[@class='newsTopic']"));
//        if (newsCountBeforeUpdate == newsAfterUpdate.size()) {
//        Assert.assertTrue(newsAfterUpdate.size() == newsCountBeforeUpdate, "News was deleted");
//
//    }




        driver.close();
    }
}




