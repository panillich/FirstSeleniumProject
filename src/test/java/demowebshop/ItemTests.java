package demowebshop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ItemTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        driver.get("https://demowebshop.tricentis.com/login");
        login("panillich@gmail.com", "SecretPass123!");
    }

    @Test
    public void addItemToCartTest() throws InterruptedException {
        driver.get("https://demowebshop.tricentis.com/");
        List<WebElement> productItems = driver.findElements(By.cssSelector(".product-item"));
        Assert.assertTrue(
                productItems.size() >= 2,
                "There must be at least 2 products on the main page!"
        );

        WebElement secondProduct = productItems.get(1);
        String expectedProductName = secondProduct.findElement(By.cssSelector(".product-title a")).getText();
        secondProduct.findElement(By.cssSelector("input[value='Add to cart']")).click();

        Thread.sleep(2000);
        driver.findElement(By.cssSelector("span.close")).click();
        click(By.cssSelector(".ico-cart"));

        By productInCart = By.xpath("//td[@class='product']//a[normalize-space(text())='" + expectedProductName + "']");
        Assert.assertTrue(
                isElementPresent(productInCart),
                "The item '" + expectedProductName + "' not found in the cart!"
        );
    }
}