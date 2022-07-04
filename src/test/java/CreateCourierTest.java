import com.google.gson.Gson;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.Courier;
import org.junit.Assert;
import static ru.praktikum_services.qa_scooter.Courier.*;


public class CreateCourierTest {

    Courier courier = new Courier("katya123", "123456", "Katerina");

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }


    @Test
    @DisplayName("Проверка, что можно создать, запрос возвращает правильный код ответа")
    public void CreateCourierShouldReturnCorrectStatusCode(){
        createCourier(courier)
                .then().statusCode(201);
    }


    @Test
    @DisplayName("Проверка, что успешный запрос на создание курьера возвращает ok: true")
    public void CreateCourierShouldReturnCorrectBodyAnswer(){
        Gson gson = new Gson();
        Response response = createCourier(courier);
        String message = response.body().asString();
        Courier answer = gson.fromJson (message, Courier.class);
        Assert.assertTrue(answer.getOk(),true);
    }

    @After
    public void deleteTestData(){
        deleteCourier(courier);
    }
}
