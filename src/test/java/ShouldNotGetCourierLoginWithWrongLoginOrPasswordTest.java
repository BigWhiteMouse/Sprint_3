import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.Courier;

import static org.hamcrest.Matchers.equalTo;
import static ru.praktikum_services.qa_scooter.Courier.*;

@RunWith(Parameterized.class)



public class ShouldNotGetCourierLoginWithWrongLoginOrPasswordTest {
    private final String LOGIN;
    private final String PASSWORD;
    private final String FIRST_NAME;
    Courier courierOne = new Courier("katya123", "123456", "Katerina");

    public ShouldNotGetCourierLoginWithWrongLoginOrPasswordTest(String login, String password, String firstName){
        this.LOGIN = login;
        this.PASSWORD = password;
        this.FIRST_NAME = firstName;
    }

    @Parameterized.Parameters
    public static Object[] getCourierData() {
        return new Object[][] {
                {"katya456", "89101112", null},
                {"katya123", "89101112", null},
                {"katya456", "1234567", null},
                {" ", "123456", null},
                {"katya123", " ", null}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        createCourier(courierOne);
    }

    @Test
    @DisplayName("Проверки: если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;" +
            "система вернёт ошибку, если неправильно указать логин или пароль;")
    public void ShouldNotGetCourierLoginWithWrongLoginOrPassword(){
        Courier courierTwo = new Courier(LOGIN,PASSWORD,FIRST_NAME);

        сourierLogin(courierTwo)
                .then().statusCode(404)
                .and().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void deleteTestData(){
        deleteCourier(courierOne);
    }
}
