import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.Courier;

import static org.hamcrest.Matchers.equalTo;
import static ru.praktikum_services.qa_scooter.CourierMethods.*;
import static org.apache.http.HttpStatus.*;

public class ShouldNotGetCourierLoginWithoutRequiredFieldsTest {
    Courier courierOne = new Courier("katya123", "123456", "Katerina");

    @Before
    public void createTestData() {
        createCourier(courierOne);
    }

    @Test
    @DisplayName("Проверка, что, если не передать в запросе поле логин, возвращается ошибка")
    public void ShouldNotGetCourierLoginWithoutLogin() {
        Courier courierTwo = new Courier(null, "123456", null);

        сourierLogin(courierTwo)
                .then().statusCode(SC_BAD_REQUEST)
                .and().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка, что, если не передать в запросе поле пароль, возвращается ошибка")
    @Description("опытным путем выяснилось, что при отсутствии логина система возвращает код 400 и текст ошибки " +
            "согласно документации," +
            "а при отсутствии пароля - просто 504 ошибку")
    public void ShouldNotGetCourierLoginWithoutPassword() {
        Courier courierTwo = new Courier("katya123", null, null);

        сourierLogin(courierTwo)
                .then().statusCode(SC_GATEWAY_TIMEOUT);
    }

    @After
    public void deleteTestData() {
        deleteCourier(courierOne);
    }
}
