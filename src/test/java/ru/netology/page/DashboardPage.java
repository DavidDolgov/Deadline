package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement personalArea = $("[data-test-id=dashboard] .icon");

    public DashboardPage() {
        personalArea.shouldBe(Condition.visible);
    }
}
