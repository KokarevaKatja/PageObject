package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class TransferPage {

    private ElementsCollection headings = $$(".heading");
    private SelenideElement amount = $("[data-test-id=amount] input");
    private SelenideElement from = $("[data-test-id=from] input");
    private SelenideElement actionTransfer = $("[data-test-id=action-transfer]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification] .notification__content");


    public TransferPage() {
        headings.find(Condition.exactText("Пополнение карты")).shouldBe(Condition.visible);
    }

    public void transfer(String transferAmount, DataHelper.CardInfo cardInfo) {
        amount.setValue(transferAmount);
        from.setValue(cardInfo.getCardNumber());
        actionTransfer.click();
    }

    public void getErrorNotification(String expectedText) {
        errorNotification.shouldHave(Condition.text(expectedText), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    public DashboardPage validTransfer(String transferAmount, DataHelper.CardInfo cardInfo) {
        transfer(transferAmount, cardInfo);
        return new DashboardPage();
    }
}
