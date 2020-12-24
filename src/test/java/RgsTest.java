import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class RgsTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void before() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, 10, 1000);


        String baseUrl = "https://rgs.ru/";
        //1.Перейти на rgs.ru
        driver.get(baseUrl);
    }

    @Test
    public void firstTest() {
        //2.Выбрать меню
        String menuXPath = "//a[@class='hidden-xs' and normalize-space()='Меню']";
        driver.findElement(By.xpath(menuXPath)).click();

        //3.Выбрать категорию ДМС
        String dmsXpath = "//form//li/a[normalize-space()='ДМС']";
        WebElement webElementDms = driver.findElement(By.xpath(dmsXpath));
        webElementDms.click();



//        //4.Проверить наличие заголовка - Добровольное медицинское страхование
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому"
                 ,"ДМС 2020 | Рассчитать стоимость добровольного медицинского страхования и оформить ДМС в Росгосстрах"
                 ,driver.getTitle());



        //5.Отправить заявку
        String application = "//div/a[normalize-space()='Отправить заявку']";
        WebElement sendAnApplication = driver.findElement(By.xpath(application));
        sendAnApplication.click();


        //6.Проверить, что открылась страница , на которой
        //присутствует текст - Заявка на добровольное
        //медицинское страхование
        String form = "//h4/*";
        waitUtilElementToBeVisible(By.xpath(form));
        WebElement webElement2 = driver.findElement(By.xpath(form));
        Assert.assertEquals("Отсутствует заголовок/не соответствует требумому"
                ,"Заявка на добровольное медицинское страхование",webElement2.getText());


        //7.Заполнить поля
        String name = "Хаба";
        String lastName = "Баба";
        String phone = "91111111111";
        String mName = "Хабабабовна";
        String region = "М";
        String email = "fdsfs";
        String contactDate = "12.12.2030";
        String comment = "fsdfsd";



        WebElement webElementLastName = driver.findElement(By.xpath("//input[@name='LastName']"));
        WebElement webElementName = driver.findElement(By.xpath("//input[@name='FirstName']"));
        WebElement webElementMiddleName = driver.findElement(By.xpath("//input[@name='MiddleName']"));
        WebElement webElementRegion = driver.findElement(By.xpath("//select[@name='Region']"));
        WebElement webElementEmail = driver.findElement(By.xpath("//input[@name='Email']"));
        WebElement webElementContactDate = driver.findElement(By.xpath("//input[@name='ContactDate']"));
        WebElement webEllemtnComment = driver.findElement(By.xpath("//textarea[@name='Comment']"));
        WebElement webElementCheckBox = driver.findElement(By.xpath("//input[@class='checkbox']"));
        WebElement webElementPhoneNumber = driver.findElement(By.xpath("//label[text()='Телефон']/following-sibling::input"));



        webElementLastName.sendKeys(lastName);
        webElementName.sendKeys(name);
        webElementMiddleName.sendKeys(mName);
        webElementRegion.sendKeys(region);
        webElementRegion.sendKeys(Keys.ENTER);
        webElementEmail.sendKeys(email);
        webElementContactDate.click();
        webElementContactDate.sendKeys(contactDate);
        webEllemtnComment.sendKeys(comment);
        webElementCheckBox.click();
        webElementPhoneNumber.click();
        webElementPhoneNumber.sendKeys(phone);

        //8. Проверить, что все поля заполнены
        //введенными значениями
        Assert.assertEquals(webElementLastName.getAttribute("value"),lastName);
        Assert.assertEquals(webElementName.getAttribute("value"),name);
        Assert.assertEquals(webElementMiddleName.getAttribute("value"),mName);
        Assert.assertEquals(webElementEmail.getAttribute("value"),email);
        Assert.assertEquals(webElementContactDate.getAttribute("value"),contactDate);
        Assert.assertEquals(webEllemtnComment.getAttribute("value"),comment);
        Assert.assertEquals(webElementPhoneNumber.getAttribute("value"),"+7 (911) 111-11-11");




        //9.Проверить, что у Поля - Эл. почта
        //присутствует сообщение об ошибке -
        //Введите корректный email
        String err = "//span[@class='validation-error-text']";
        WebElement webElement3 = driver.findElement(By.xpath(err));
        Assert.assertEquals("Введите адрес электронной почты",webElement3.getText());
    }

    @After
    public void after() {
         driver.quit();
    }


    private void waitUtilElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}



