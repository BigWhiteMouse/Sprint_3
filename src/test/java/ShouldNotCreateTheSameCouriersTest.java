import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.Courier;

import static org.hamcrest.Matchers.equalTo;
import static ru.praktikum_services.qa_scooter.CourierMethods.createCourier;
import static ru.praktikum_services.qa_scooter.CourierMethods.deleteCourier;
import static org.apache.http.HttpStatus.*;

@RunWith(Parameterized.class)

public class ShouldNotCreateTheSameCouriersTest {
    private final String LOGIN;
    private final String PASSWORD;
    private final String FIRST_NAME;
    Courier courierOne = new Courier("katya123", "123456", "Katerina");

    public ShouldNotCreateTheSameCouriersTest(String login, String password, String firstName) {
        this.LOGIN = login;
        this.PASSWORD = password;
        this.FIRST_NAME = firstName;
    }

    @Parameterized.Parameters
    public static Object[] getCourierData() {
        return new Object[][]{
                {"katya123", "123456", "Katerina"},
                {"katya123", "1234567", "Petr"},
        };
    }

    @Test
    @DisplayName("Проверки: нельзя создать двух одинаковых курьеров; если создать пользователя с логином, " +
            "который уже есть, возвращается ошибка")
    public void ShouldNotCreateTwoSameCouriers() {
        Courier courierTwo = new Courier(LOGIN, PASSWORD, FIRST_NAME);

        createCourier(courierOne);

        createCourier(courierTwo)
                .then().statusCode(SC_CONFLICT)
                .and().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @After
    public void deleteTestData() {
        deleteCourier(courierOne);
    }


}
