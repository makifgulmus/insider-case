package com.insidercase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CareersPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By companyMenu = By.xpath("//a[@id='navbarDropdownMenuLink' and contains(text(), 'Company')]");
    private By careersLink = By.xpath("//a[contains(text(), 'Careers')]");
    private By findOurCallingSection = By.id("career-find-our-calling");
    private By ourLocationSection = By.id("career-our-location");
    private By lifeAtInsiderHeader = By.xpath("//h2[text()='Life at Insider']");

    public CareersPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Explicit wait
    }

    public void navigateToCareersPage() {
        WebElement companyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(companyMenu));
        companyElement.click();
        WebElement careersElement = wait.until(ExpectedConditions.visibilityOfElementLocated(careersLink));
        careersElement.click();
    }

    public boolean areCareerSectionsPresent() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(findOurCallingSection)).isDisplayed() &&
                wait.until(ExpectedConditions.presenceOfElementLocated(ourLocationSection)).isDisplayed() &&
                wait.until(ExpectedConditions.presenceOfElementLocated(lifeAtInsiderHeader)).isDisplayed();
    }
}
