package ru.netology;

import lombok.Value;

import java.util.Random;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }


    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    static class CardInfo {
        private String cardNumber;
        private int cardIndex;

    }


        public static CardInfo getCard1Info() {
            return new CardInfo("5559 0000 0000 0001", 0);
        }


        public static CardInfo getCard2Info() {
            return new CardInfo("5559 0000 0000 0002", 1);
        }


        public static int generateValidSum(int balance) {
            return new Random().nextInt(balance) + 1;
        }

        public static int generateInvalidSum(int balance) {
        return Math.abs(balance) + new Random().nextInt(20_000);
        }
    }
