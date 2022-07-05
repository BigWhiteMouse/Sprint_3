import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.Courier;

import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.praktikum_services.qa_scooter.CourierMethods.*;
import static org.apache.http.HttpStatus.*;

public class CourierLoginTest {

    Courier courier = new Courier("katya123", "123456", "Katerina");

    @Before
    public void createTestData() {
        createCourier(courier);
    }

    @Test
    @DisplayName("Проверка, что курьер может авторизоваться")
    public void CourierLoginShouldReturnCorrectStatusCode() {
        сourierLogin(courier)
                .then()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Проверка, что успешный запрос на авторизацию возвращает id курьера")
    public void CourierLoginShouldReturnId() {
        сourierLogin(courier)
                .then()
                .assertThat().body("id", notNullValue());
    }

    @After
    public void deleteTestData() {
        deleteCourier(courier);
    }
}
