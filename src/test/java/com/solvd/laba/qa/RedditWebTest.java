package com.solvd.laba.qa;

import com.solvd.laba.qa.gui.components.RedditHelpItem;
import com.solvd.laba.qa.gui.pages.common.*;
import com.solvd.laba.qa.gui.pages.common.CommunitiesPageBase;
import com.solvd.laba.qa.gui.pages.common.SearchResultPageBase;
import com.solvd.laba.qa.gui.pages.common.SubredditPageBase;
import com.solvd.laba.qa.gui.pages.common.LogInPageBase;
import com.zebrunner.agent.core.annotation.TestLabel;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import com.zebrunner.carina.core.registrar.tag.Priority;
import com.zebrunner.carina.core.registrar.tag.TestPriority;
import com.zebrunner.carina.utils.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.lang.invoke.MethodHandles;

import static org.testng.Assert.assertTrue;

public class RedditWebTest implements IAbstractTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public String username = Configuration.getRequired("username");
    public String password = Configuration.getRequired("password");
    public String subreddit = Configuration.getRequired("subreddit_search");

    @Test
    @MethodOwner(owner = "nislam")
    @TestPriority(Priority.P2)
    @TestLabel(name = "home page", value={"web", "functional"})
    public void testHomePage() {
        LOGGER.info("[TEST] Open Reddit Home Page");
        SoftAssert softAssert = new SoftAssert();
        HomePageBase homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        assertTrue(homePage.isPageOpened(), "Home page was not opened");
        softAssert.assertEquals(homePage.getDriver().getTitle(), "Reddit - Dive into anything");
        assertTrue(homePage.isHeaderExist(), "Header was not found");
        softAssert.assertTrue(homePage.getHeader().isLogoExist(), "Home logo does not exist");
        softAssert.assertTrue(homePage.getHeader().isSearchBarExist(), "Search bar does not exist");
        softAssert.assertTrue(homePage.getHeader().isGetAppButtonExist(), "'Get App' button does not exist");
        softAssert.assertTrue(homePage.getHeader().isLogInButtonExist(), "'Log In' button does not exist");
        assertTrue(homePage.isSidebarExist(), "Sidebar was not found");
        softAssert.assertAll();
    }

    @Test
    @MethodOwner(owner = "nislam")
    @TestPriority(Priority.P2)
    @TestLabel(name = "community search bar", value={"web", "functional"})
    public void testCommunitySearchBar() {
        HomePageBase homePage = initPage(getDriver(), HomePageBase.class);
        SoftAssert softAssert = new SoftAssert();

        homePage.open();
        assertTrue(homePage.isPageOpened(), "Home page was not opened");
        homePage.clickSeeMoreResources();
        CommunitiesPageBase communitiesPage = homePage.clickCommunities();
        assertTrue(communitiesPage.isPageOpened(), "Communities page was not opened");
        assertTrue(communitiesPage.isHeaderExist(), "Header does not exist");
        softAssert.assertTrue(communitiesPage.getHeader().isSearchBarExist(), "Search bar does not exist");
        communitiesPage.searchInSearchBar(subreddit);
        SearchResultPageBase searchResultPage = communitiesPage.hitEnter();
        assertTrue(searchResultPage.isPageOpened(), "Search results page was not opened");
        searchResultPage.clickCommunitiesTab();
        SubredditPageBase subredditPage = searchResultPage.clickSubreddit(subreddit);
        softAssert.assertEquals(subredditPage.getSubredditName(), subreddit, "Wrong subreddit was selected");
        softAssert.assertAll();
    }

    @Test
    @MethodOwner(owner = "nislam")
    @TestPriority(Priority.P2)
    @TestLabel(name = "check popular communities", value={"web", "functional"})
    public void testCheckPopularCommunities() {
        HomePageBase homePage = initPage(getDriver(), HomePageBase.class);
        SoftAssert softAssert = new SoftAssert();

        homePage.open();
        assertTrue(homePage.isPageOpened(), "Home page was not opened");
        softAssert.assertTrue(homePage.isPopularCommunitiesSectionExist(), "'Popular Communities' section was does not exist");
        homePage.clickSeeMorePopularCommunities();
        homePage.selectRandomSubreddit();
        String subredditName = homePage.getRandomSubredditName();
        String subredditURL = homePage.getRandomSubredditURL();
        System.out.println(subredditName);
        SubredditPageBase subredditPage = homePage.clickRandomSubreddit();
        assertTrue(subredditPage.isPageOpened(), "Subreddit page was not opened");
        softAssert.assertEquals(subredditName, subredditPage.getSubredditName(), "Wrong subreddit name");
        softAssert.assertEquals(subredditURL, subredditPage.getSubredditURL(), "Wrong URL address");
        softAssert.assertAll();
    }

    @Test
    @MethodOwner(owner = "nislam")
    @TestPriority(Priority.P2)
    @TestLabel(name = "log in", value={"web", "functional"})
    public void testLogIn() {
        HomePageBase homePage = initPage(getDriver(), HomePageBase.class);
        SoftAssert softAssert = new SoftAssert();

        homePage.open();
        assertTrue(homePage.isPageOpened(), "Home page was not opened");
        homePage.clickSeeMoreResources();
        CommunitiesPageBase communitiesPage = homePage.clickCommunities();
        assertTrue(communitiesPage.isPageOpened(), "Communities page was not opened");
        LogInPageBase logInPage = communitiesPage.clickLogIn();
        assertTrue(logInPage.isPageOpened(), "Log In page was not opened");

        logInPage.writeUsername("wrong_username");
        logInPage.writePassword(password);
        logInPage.clickSubmit();
        softAssert.assertFalse(communitiesPage.getHeader().isLogoExist(), "Should have given log-in errors");

        logInPage.writeUsername(username);
        logInPage.writePassword(password);
        logInPage.clickSubmit();
        softAssert.assertTrue(communitiesPage.getHeader().isLogoExist(), "Logo does not exist");
        HomePageBase homePageAfterLogIn = communitiesPage.clickLogo();
        softAssert.assertTrue(homePageAfterLogIn.isUserDropdownExist(), "User dropdown does not exist -- log-in was unsuccessful");
        softAssert.assertAll();
    }

    @Test
    @MethodOwner(owner = "nislam")
    @TestPriority(Priority.P2)
    @TestLabel(name = "help page item selection", value={"web", "functional"})
    public void testHelpPageWithItemSelection() {
        HomePageBase homePage = initPage(getDriver(), HomePageBase.class);
        SoftAssert softAssert = new SoftAssert();

        homePage.open();
        assertTrue(homePage.isPageOpened(), "Home page was not opened");
        HelpPageBase helpPage = homePage.clickHelp();
        assertTrue(helpPage.isPageOpened(), "Help page was not opened");
        helpPage.chooseHelpItem(RedditHelpItem.REDDIT_101);
        softAssert.assertAll();
    }

    @Test
    @MethodOwner(owner = "nislam")
    @TestPriority(Priority.P2)
    @TestLabel(name = "help page search bar", value={"web", "functional"})
    public void testHelpPageWithSearchBar() {
        HomePageBase homePage = initPage(getDriver(), HomePageBase.class);
        SoftAssert softAssert = new SoftAssert();

        homePage.open();
        assertTrue(homePage.isPageOpened(), "Home page was not opened");
        HelpPageBase helpPage = homePage.clickHelp();
        assertTrue(helpPage.isPageOpened(), "Help page was not opened");
        helpPage.writeInSearchBar("What is my cake day?");
        helpPage.hitEnter();
        softAssert.assertAll();
    }

}
