package com.shireesha.casestudy.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SeleniumTests {
private WebDriver driver;
	
	@Autowired
	public SeleniumTests(WebDriver driver) {
		this.driver = driver;
	}
	
	@Test
	void testHomePage() {
		driver.get("http://localhost:8080/");
		assertEquals("Spring Boot Gallery App", driver.getTitle());
	}
	
	@Test
	void testLoginInput() throws InterruptedException {
		driver.get("http://localhost:8080/login");
		WebElement usernameField = driver.findElement(By.name("email"));
		usernameField.sendKeys("siri@gmail.com");
		WebElement passwordField = driver.findElement(By.name("password"));
		passwordField.sendKeys("password");
		WebElement loginButton = driver.findElement(By.cssSelector("body > div.sub_container > div > form > div.col-8.form-div > button"));
		loginButton.click();
		
		assertEquals("Spring Boot Gallery App", driver.getTitle());
	}
}
