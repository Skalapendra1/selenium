import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class test2 {

    WebDriver driver;
    String baseUrl;

    @Before
    public void beforeTest() {
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
        baseUrl = "http://www.sberbank.ru/ru/person";
        String secondUrl = "https://online.sber.insure/store/travel/?_ga=2.69512979.2048587694.1633721061-234715776.1630963903";
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(baseUrl);

    }

    @Test
    public void test_method() {
        driver.findElement(By.xpath("//li/a[text() = 'Страхование']")).click();
        driver.findElement(By.xpath("//*[text() = 'Все страховые программы']")).click();

        WebDriverWait wait = new WebDriverWait(driver, 3, 1000);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-product='Страхование путешественников']"))).click();

        WebElement title = driver.findElement(By.xpath("//h1[text()='Страхование путешественников']"));
        wait.until(ExpectedConditions.elementToBeClickable(title));
        Assert.assertEquals("Страхование путешественников", title.getText());


        driver.findElement(By.xpath("//*[@class='kitt-cookie-warning__close']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//span[text()='Оформить онлайн']/..")))).click();

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath
                ("//a[@target='_blank']/span[text()='Оформить на сайте']")))).click();

        ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));

        driver.findElement(By.xpath("//button[contains(text(),'Оформить')]")).click();

        fillField(By.xpath("//*[@id='surname_vzr_ins_0']"), "Смирнов");
        fillField(By.xpath("//*[@id='name_vzr_ins_0']"), "Сергей");
        fillField(By.xpath("//*[@id='birthDate_vzr_ins_0']"), "18.09.1987");

        driver.findElement(By.xpath("//*[@id='person_lastName']")).click();
        fillField(By.xpath("//*[@id='person_lastName']"), "Иванов");
        fillField(By.xpath("//*[@id='person_firstName']"), "Иван");
        fillField(By.xpath("//*[@id='person_middleName']"), "Иванович");
        fillField(By.xpath("//*[@id='person_birthDate']"), "18.09.1999");
        driver.findElement(By.xpath("//*[text()='Страхователь']")).click();


        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Женский']"))).click();
        fillField(By.xpath("//*[@id='passportSeries']"), "1111");
        fillField(By.xpath("//*[@id='passportNumber']"), "222222");
        fillField(By.xpath("//*[@id='documentDate']"), "19.09.2019");
        driver.findElement(By.xpath("//*[@id='documentIssue']")).click();
        fillField(By.xpath("//*[@id='documentIssue']"), "УФМСbhgffdd");


        Assert.assertEquals("Смирнов", driver.findElement(By.xpath("//*[@id='surname_vzr_ins_0']")).getAttribute("value"));
        Assert.assertEquals("Сергей", driver.findElement(By.xpath("//*[@id='name_vzr_ins_0']")).getAttribute("value"));
        Assert.assertEquals("18.09.1987", driver.findElement(By.xpath("//*[@id='birthDate_vzr_ins_0']")).getAttribute("value"));

        Assert.assertEquals("Иванов", driver.findElement(By.xpath("//*[@id='person_lastName']")).getAttribute("value"));
        Assert.assertEquals("Иван", driver.findElement(By.xpath("//*[@id='person_firstName']")).getAttribute("value"));
        Assert.assertEquals("Иванович", driver.findElement(By.xpath("//*[@id='person_middleName']")).getAttribute("value"));
        Assert.assertEquals("18.09.1999", driver.findElement(By.xpath("//*[@id='person_birthDate']")).getAttribute("value"));

        Assert.assertEquals("1111", driver.findElement(By.xpath("//*[@id='passportSeries']")).getAttribute("value"));
        Assert.assertEquals("222222", driver.findElement(By.xpath("//*[@id='passportNumber']")).getAttribute("value"));
        Assert.assertEquals("19.09.2019", driver.findElement(By.xpath("//*[@id='documentDate']")).getAttribute("value"));
        Assert.assertEquals("УФМСbhgffdd", driver.findElement(By.xpath("//*[@id='documentIssue']")).getAttribute("value"));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Продолжить')]"))).click();

        Assert.assertEquals("Поле не заполнено.", driver.findElement(By.xpath("//input-phone2[@name='phone']/span/validation-message/span[contains(text(),'Поле не заполнено')]")).getText());
        Assert.assertEquals("Поле не заполнено.", driver.findElement(By.xpath("//input-email[@name='email']/span/validation-message/span[contains(text(),'Поле не заполнено')]")).getText());
        Assert.assertEquals("Поле не заполнено.", driver.findElement(By.xpath("//input-email[@name='repeatEmail']/span/validation-message/span[contains(text(),'Поле не заполнено')]")).getText());

        Assert.assertEquals("При заполнении данных произошла ошибка", driver.findElement(By.xpath
                ("//*[@class = 'alert-form alert-form-error']")).getText());

    }

    public void fillField(By locator, String value) {
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }

    @After
    public void test_after() {
        driver.quit();

    }
}
