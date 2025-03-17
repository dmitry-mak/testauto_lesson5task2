package ru.netology.patterns2.test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.patterns2.data.DataGenerator;

import java.time.Duration;

public class AppIbankLoginTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldLoginRegisteredActiveUserTest() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id='login'] input.input__control").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(".heading").shouldBe(Condition.visible, Duration.ofSeconds(3))
                .shouldHave(Condition.text("Личный кабинет"));

    }

    @Test
    public void shouldNotLoginRegisteredBlockedUserTest() {
        var blockedUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id='login'] input.input__control").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification']").shouldBe(Condition.visible, Duration.ofSeconds(3))
                .shouldHave(Condition.text("Пользователь заблокирован"));
    }


    @Test
    public void shouldNotLoginWithInvalidLoginNameTest() {
        var activeUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id='login'] input.input__control").setValue("invalid.login");
        $("[data-test-id='password'] input").setValue(activeUser.getPassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification']").shouldBe(Condition.visible, Duration.ofSeconds(3))
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    public void shouldNotLoginWithInvalidPasswordTest() {
        var activeUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id='login'] input.input__control").setValue("invalid.login");
        $("[data-test-id='password'] input").setValue(activeUser.getPassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification']").shouldBe(Condition.visible, Duration.ofSeconds(3))
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
}
