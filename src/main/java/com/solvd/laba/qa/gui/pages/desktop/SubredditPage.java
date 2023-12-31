package com.solvd.laba.qa.gui.pages.desktop;

import com.solvd.laba.qa.gui.pages.common.SubredditPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Random;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = SubredditPageBase.class)
public class SubredditPage extends SubredditPageBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public SubredditPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//shreddit-join-button")
    private ExtendedWebElement joinButton;

    @FindBy(xpath = "//section[3]/div/div[1]/h2")
    private ExtendedWebElement rulesSection;

    @FindBy(xpath = "//section[1]/div/shreddit-subreddit-header")
    private ExtendedWebElement description;

    @Override
    public boolean isJoinButtonExist() {
        return joinButton.isPresent();
    }

    @Override
    public boolean isAboutTabExist() {
        throw new UnsupportedOperationException("Unused");
    }

    @Override
    public void clickAboutTab() {
        throw new UnsupportedOperationException("Unused");
    }

    @Override
    public boolean isRulesSectionExist() {
        return rulesSection.isPresent();
    }

    @Override
    public String getSubredditDescription() {
        return description.getAttribute("description");
    }
}
