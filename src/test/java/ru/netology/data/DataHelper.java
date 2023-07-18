package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

public class DataHelper {

    private static Faker faker = new Faker(new Locale("en"));
    private static QueryRunner runner = new QueryRunner();

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

    public static String getRandomLogin() {
        return faker.name().username();
    }

    public static String getRandomPassword() {
        return faker.internet().password();
    }

    public static AuthInfo getRandomUser() {
        return new AuthInfo(getRandomLogin(),getRandomPassword());
    }

    public static AuthInfo getOtherLoginAuthInfo() {
        return new AuthInfo(getRandomLogin(), "qwerty123");
    }

    public static AuthInfo getOtherPasswordAuthInfo() {
        return new AuthInfo("vasya", getRandomPassword());
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getVerificationCode() {
        var selectCode = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        try (var connection = getConnect()) {
            var code = runner.query(connection, selectCode, new ScalarHandler<String>());
            return new VerificationCode(code);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static VerificationCode getRandomVerificationCode() {
        return new VerificationCode(faker.numerify("000000"));
    }

    private static Connection getConnect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static void clearAll() {
        var connection = getConnect();
        runner.execute(connection, "DELETE FROM auth_codes");
        runner.execute(connection, "DELETE FROM card_transactions");
        runner.execute(connection, "DELETE FROM cards");
        runner.execute(connection, "DELETE FROM users");
    }
}
