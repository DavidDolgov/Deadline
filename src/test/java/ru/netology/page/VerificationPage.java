package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement errorNotification = $("[data-test-id=error-notification] .notification__title");
    private SelenideElement codeInputInvalid = $("[data-test-id=code].input_invalid .input__sub");

    public VerificationPage() {
        codeField.shouldHave(Condition.visible);
    }

    public DashboardPage validVerify(DataHelper.VerificationCode info) {
        codeField.setValue(info.getCode()).sendKeys(Keys.ENTER);
        return new DashboardPage();
    }

    public void noValidVerify(DataHelper.VerificationCode info) {
        codeField.setValue(info.getCode()).sendKeys(Keys.ENTER);
        errorNotification.shouldBe(Condition.visible);
    }

    public void emptyCode() {
        codeField.sendKeys(Keys.ENTER);
        codeInputInvalid.shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
}
