import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.Courier;

import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.praktikum_services.qa_scooter.Courier.*;

public class CourierLoginTest {

    Courier courier = new Courier("katya123", "123456", "Katerina");

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        createCourier(courier);
    }

    @Test
    @DisplayName("Проверка, что курьер может авторизоваться")
    public void CourierLoginShouldReturnCorrectStatusCode(){
         сourierLogin(courier)
        .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Проверка, что успешный запрос на авторизацию возвращает id курьера")
    public void CourierLoginShouldReturnId(){
        сourierLogin(courier)
                .then()
                .assertThat().body("id", notNullValue());
    }

    @After
    public void deleteTestData(){
        deleteCourier(courier);
    }
}
