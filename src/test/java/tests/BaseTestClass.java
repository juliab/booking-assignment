package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import util.DriverManager;

public abstract class BaseTestClass {

    protected WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = DriverManager.getDriverInstance();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
