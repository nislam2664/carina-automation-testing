package com.solvd.laba.qa.gui.pages.desktop;

import com.solvd.laba.qa.gui.pages.common.*;
import com.zebrunner.carina.utils.config.Configuration;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Random;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = HomePageBase.class)
public class HomePage extends HomePageBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(xpath = "//section[@class=\"mt-md\"]")
    private ExtendedWebElement popularCommunitiesSection;

    @FindBy(xpath = "//section/div/div/li/ul/li/a/span[1]/span[2]/span[1]/span")
    private List<ExtendedWebElement> popularCommunitiesList;

    @FindBy(id = "popular-communities-list-see-more")
    private ExtendedWebElement seeMoreButton;

    @FindBy(id = "USER_DROPDOWN_ID")
    private ExtendedWebElement userDropdown;

    private ExtendedWebElement randomSubreddit;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPopularCommunitiesSectionExist() {
        return popularCommunitiesSection.isPresent();
    }

    @Override
    public void clickSeeMorePopularCommunities() {
        seeMoreButton.click();
    }

    @Override
    public void selectRandomSubreddit() {
        Random rand = new Random();
        int element_num = rand.nextInt(popularCommunitiesList.size());
        randomSubreddit = popularCommunitiesList.get(element_num);
    }

    @Override
    public SubredditPageBase clickRandomSubreddit() {
        randomSubreddit.scrollTo();
        randomSubreddit.hover();
        randomSubreddit.click();
        return initPage(driver, SubredditPageBase.class);
    }

    @Override
    public String getRandomSubredditName() {
        return randomSubreddit.getText();
    }

    @Override
    public String getRandomSubredditURL() {
        return Configuration.getRequired("base_url") + getRandomSubredditName() + "/";
    }

    @Override
    public void clickSeeMoreResources() {
        getSidebar().clickSeeMore();
    }

    @Override
    public boolean isUserDropdownExist() {
        return userDropdown.clickIfPresent();
    }

    @Override
    public void open() {
        LOGGER.info("Home page is being opened");
        super.open();
    }
}
