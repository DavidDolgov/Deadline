package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class DeadlineTest {
    @BeforeEach
    void setUP() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @AfterAll
    static void removal() {
        DataHelper.clearAll();
    }

    @Test
    @DisplayName("Положительный сценарий. Переход в личный кабинет")
    void shouldGoToPersonalAccount() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    @DisplayName("LoginPageTest - Неверный логин; Верный пароль")
    void negativePathTestForLoginField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getOtherLoginAuthInfo();
        loginPage.noValidLogin(authInfo);
    }

    @Test
    @DisplayName("LoginPageTest - Неверный пароль; Верный логин")
    void negativePathTestForPasswordField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getOtherPasswordAuthInfo();
        loginPage.noValidLogin(authInfo);
    }

    @Test
    @DisplayName("LoginPageTest - Неверный пароль; Неверный логин")
    void negativePathTestForPasswordAndLoginField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getRandomUser();
        loginPage.noValidLogin(authInfo);
    }

    @Test
    @DisplayName("LoginPageTest - Пустой логин; Верный пароль")
    void negativePathTestForEmptyLoginField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        loginPage.emptyLogin(authInfo);
    }

    @Test
    @DisplayName("LoginPageTest - Пустой пароль; Верный логин")
    void negativePathTestForEmptyPasswordField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        loginPage.emptyPassword(authInfo);
    }

    @Test
    @DisplayName("VerificationPageTest - Неверный код")
    void negativePathTestForCodeField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getRandomVerificationCode();
        verificationPage.noValidVerify(verificationCode);
    }

    @Test
    @DisplayName("VerificationPageTest - Пустой код")
    void negativePathTestForEmptyCodeField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.emptyCode();
    }

}
