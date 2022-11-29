package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class TransferBetweenCardsTest {

    LoginPage loginPage;

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999/", LoginPage.class);
    }

    @Test
    void shouldLoginSuccessfully() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyBetweenCardsSuccessfully() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        var card1 = DataHelper.getCard1Info();
        var card2 = DataHelper.getCard2Info();
        var balanceCard1 = dashboardPage.getCardBalance(card1.getCardIndex());
        var balanceCard2 = dashboardPage.getCardBalance(card2.getCardIndex());

        var transferAmount = DataHelper.generateValidSum(balanceCard2);
        var expectedBalanceCard1 = balanceCard1 + transferAmount;
        var expectedBalanceCard2 = balanceCard2 - transferAmount;
        var transferPage = dashboardPage.transferTo(card1.getCardIndex());
        dashboardPage = transferPage.validTransfer(String.valueOf(transferAmount), card2);
        var actualBalanceCard1 = dashboardPage.getCardBalance(card1.getCardIndex());
        var actualBalanceCard2 = dashboardPage.getCardBalance(card2.getCardIndex());

        Assertions.assertEquals(expectedBalanceCard1, actualBalanceCard1);
        Assertions.assertEquals(expectedBalanceCard2, actualBalanceCard2);
    }


    @Test
    void shouldDisplayErrorMessageIfTransferInvalidSum() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        var card1 = DataHelper.getCard1Info();
        var card2 = DataHelper.getCard2Info();
        var balanceCard1 = dashboardPage.getCardBalance(card1.getCardIndex());
        var balanceCard2 = dashboardPage.getCardBalance(card2.getCardIndex());

        var transferAmount = DataHelper.generateInvalidSum(balanceCard1);
        var transferPage = dashboardPage.transferTo(card2.getCardIndex());
        transferPage.transfer(String.valueOf(transferAmount), card2);
        transferPage.getErrorNotification("На вашей карте недостаточно средств для перевода");

        Assertions.assertEquals(dashboardPage.getCardBalance(card1.getCardIndex()), balanceCard1);
        Assertions.assertEquals(dashboardPage.getCardBalance(card2.getCardIndex()), balanceCard2);
    }
}
