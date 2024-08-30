package com.insidercase;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class QAPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;

    private By seeAllQaJobsButton = By.xpath("//a[text()='See all QA jobs']");
    private By locationDropdown = By.id("select2-filter-by-location-container");
    private By locationOption = By.xpath("//li[contains(text(), 'Istanbul, Turkey')]");
    private By departmentDropdown = By.xpath("//span[@title='Quality Assurance']");
    private By jobsList = By.id("jobs-list");
    private By noPositionsAvailable = By.xpath("//p[text()='No positions available.']");
    private By positionDepartment = By.cssSelector(".position-department");
    private By positionLocation = By.cssSelector(".position-location");
    private By viewRoleButton = By.xpath("//div[@id='jobs-list']//a[contains(text(), 'View Role')]");
    private By applyButton = By.xpath("//a[contains(text(), 'Apply for this job')]");
    public By getJobTitle(String jobTitleText) {return By.xpath("//h2[contains(text(), '" + jobTitleText + "')]");}
    public String jobTitleText = "";

    public QAPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    public void hoverAndScrollToElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform(); 
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    

    public void navigateToQaPage() {
        driver.get("https://useinsider.com/careers/quality-assurance/");
    }

    public void clickSeeAllQaJobs() {
        WebElement seeAllQaJobs = wait.until(ExpectedConditions.visibilityOfElementLocated(seeAllQaJobsButton));
        seeAllQaJobs.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(departmentDropdown));
    }

    public void filterJobsByLocation() {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(locationDropdown));
        dropdown.click();

        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(locationOption));
        option.click();
    }
    
    public boolean isJobsListPresent() {
        //Filter results are sometimes flickering, so we need to wait for the list to stabilize.
        wait.until(driver -> {
            WebElement element = driver.findElement(jobsList);
            String oldAttribute = element.getAttribute("innerHTML");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String newAttribute = element.getAttribute("innerHTML");
            return oldAttribute.equals(newAttribute);
        });
        return wait.until(ExpectedConditions.presenceOfElementLocated(jobsList)).isDisplayed();
    }

    public boolean isNoPositionsAvailableNotPresent() {
        return driver.findElements(noPositionsAvailable).isEmpty();
    }

    public boolean areJobDetailsCorrect() {
        hoverAndScrollToElement(positionLocation);
        List<WebElement> departments = driver.findElements(positionDepartment);
        List<WebElement> locations = driver.findElements(positionLocation);

        for (WebElement department : departments) {
            if (!department.getText().contains("Quality Assurance")) {
                return false;
            }
        }

        for (WebElement location : locations) {
            if (!location.getText().contains("Istanbul, Turkey")) {
                return false;
            }
        }

        return true;
    }

    public void clickFirstViewRole() {
        hoverAndScrollToElement(viewRoleButton);
        jobTitleText = driver.findElement(By.xpath("//p[contains(@class, 'position-title')]")).getText();
        System.out.println("Job Title: " + jobTitleText);
        WebElement viewRole = wait.until(ExpectedConditions.elementToBeClickable(viewRoleButton));
        viewRole.click();
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    public boolean isJobDetailsPageCorrect() {
        By jobTitle = getJobTitle(jobTitleText);
        WebElement jobTitleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(jobTitle));
        WebElement applyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(applyButton));
        return jobTitleElement.isDisplayed() && applyElement.isDisplayed() && driver.getCurrentUrl().contains("lever.co");
    }
}
