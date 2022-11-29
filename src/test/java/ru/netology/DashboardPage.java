package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class DashboardPage {
    public DashboardPage() {
        heading.shouldBe(visible);
}

    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public int getCardBalance(int cardIndex) {
        var card = cards.get(cardIndex);
        val text = card.text();
        return extractBalance(text);
    }

    private int extractBalance(String text){
            var start = text.indexOf(balanceStart);
            var finish = text.indexOf(balanceFinish);
            var value = text.substring(start + balanceStart.length(), finish);
            return Integer.parseInt(value);
        }

    public TransferPage transferTo(int cardIndex) {
        SelenideElement button = $("[data-test-id=action-deposit]", cardIndex);
        button.click();
        return new TransferPage();
}
}
