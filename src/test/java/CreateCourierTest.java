import com.google.gson.Gson;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.Courier;
import org.junit.Assert;

import static ru.praktikum_services.qa_scooter.CourierMethods.createCourier;
import static ru.praktikum_services.qa_scooter.CourierMethods.deleteCourier;
import static org.apache.http.HttpStatus.*;


public class CreateCourierTest {

    Courier courier = new Courier("katya123", "123456", "Katerina");

    @Test
    @DisplayName("Проверка, что можно создать, запрос возвращает правильный код ответа")
    public void CreateCourierShouldReturnCorrectStatusCode() {
        createCourier(courier)
                .then().statusCode(SC_CREATED);
    }


    @Test
    @DisplayName("Проверка, что успешный запрос на создание курьера возвращает ok: true")
    public void CreateCourierShouldReturnCorrectBodyAnswer() {
        Gson gson = new Gson();
        Response response = createCourier(courier);
        String message = response.body().asString();
        Courier answer = gson.fromJson(message, Courier.class);
        Assert.assertTrue(answer.getOk(), true);
    }

    @After
    public void deleteTestData() {
        deleteCourier(courier);
    }
}
