import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.Courier;

import static org.hamcrest.Matchers.equalTo;
import static ru.praktikum_services.qa_scooter.Courier.createCourier;


@RunWith(Parameterized.class)

public class ShouldNotCreateCouriersWithoutRequiredFieldsTest {
    private final String LOGIN;
    private final String PASSWORD;
    private final String FIRST_NAME;

    public ShouldNotCreateCouriersWithoutRequiredFieldsTest(String login, String password, String firstName){
        this.LOGIN = login;
        this.PASSWORD = password;
        this.FIRST_NAME = firstName;
    }

    @Parameterized.Parameters
    public static Object[] getCourierData() {
        return new Object[][] {
                {null, "1234567", "Katerina"},
                {"katya123", null, "Katerina"},
                {null, null, "Katerina"}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Проверки: чтобы создать курьера, нужно передать в ручку все обязательные поля; " +
            "если одного из полей нет, запрос возвращает ошибку;")
    @Description("Насколько я поняла из документации API, обязательными являются только 2 поля: " +
            "login и password, если не указать firstName, ошибка не возвращается")
    public void ShouldNotCreateTwoSameCouriers(){
        Courier courier = new Courier(LOGIN,PASSWORD,FIRST_NAME);

        createCourier(courier)
                .then().statusCode(400)
                .and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

}
