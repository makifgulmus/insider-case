package com.insidercase;

import org.testng.Assert;
import org.testng.annotations.Test;

public class InsiderQATest extends BaseTest {

    @Test
    public void testInsiderWorkflow() {
        //Test the Home Page
        HomePage homePage = new HomePage(driver);
        homePage.openHomePage();
        homePage.acceptCookies();
        Assert.assertTrue(homePage.isNavbarPresent(), "Navbar is not present!");
        Assert.assertTrue(homePage.isHomeCtaContainerPresent(), "CTA container is not present!");

        //Test the Careers Page
        CareersPage careersPage = new CareersPage(driver);
        careersPage.navigateToCareersPage();
        Assert.assertTrue(careersPage.areCareerSectionsPresent(), "Career sections are not present!");

        //Test the Quality Assurance Jobs Page
        QAPage qaPage = new QAPage(driver);
        qaPage.navigateToQaPage();
        qaPage.clickSeeAllQaJobs();
        qaPage.filterJobsByLocation();
        Assert.assertTrue(qaPage.isJobsListPresent(), "Jobs list is not present!");
        Assert.assertTrue(qaPage.isNoPositionsAvailableNotPresent(), "No positions available message is present!");
        Assert.assertTrue(qaPage.areJobDetailsCorrect(), "Job details are incorrect!");

        //Test the View Role (Lever) Page
        qaPage.clickFirstViewRole();
        Assert.assertTrue(qaPage.isJobDetailsPageCorrect(), "Job details page is incorrect!");
    }
}
