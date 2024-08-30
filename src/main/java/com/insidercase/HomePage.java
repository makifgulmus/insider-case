package com.insidercase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By navbar = By.id("navbarNavDropdown");
    private By homeCtaContainer = By.cssSelector(".home_cta_container");
    private By cookieAcceptButton = By.id("wt-cli-accept-all-btn"); 

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openHomePage() {
        driver.get("https://useinsider.com/");
    }

    public boolean isNavbarPresent() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(navbar)).isDisplayed();
    }

    public boolean isHomeCtaContainerPresent() {
        return driver.findElements(homeCtaContainer).size() > 0;
    }

    public void acceptCookies() {
        try {
            WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(cookieAcceptButton));
            acceptButton.click();
        } catch (Exception e) {
            System.out.println("Cookie popup not found or already closed.");
        }
    }
}
