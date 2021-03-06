import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.Orders;

import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.praktikum_services.qa_scooter.OrdersMethods.cancelOrder;
import static ru.praktikum_services.qa_scooter.OrdersMethods.createOrder;

public class GetTrackWhenCreateOrderTest {

    Orders order = new Orders("Дима", "Акакиев", "г.Москва, ул.Чижова, д.32, кв.1",
            "Медведково", "8-963-555-44-33", 5, "2020-07-07",
            "Жду с нетерпением", null);

    @Test
    @DisplayName("Проверка, что валидный запрос на создание заказа возвращает номер трэка")
    public void ShouldGetTrackWhenCreateOrder() {
        createOrder(order)
                .then()
                .assertThat().body("track", notNullValue());
    }

    @After
    public void deleteTestData() {
        cancelOrder(order);
    }

}
