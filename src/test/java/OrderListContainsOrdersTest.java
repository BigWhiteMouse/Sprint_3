import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.praktikum_services.qa_scooter.OrdersMethods.getOrderList;


public class OrderListContainsOrdersTest {

    @Test
    @DisplayName("Проверка, что в тело ответа возвращается список заказов")
    public void OrderListShouldContainOrders() {
        getOrderList()
                .then()
                .assertThat().body("orders.id", notNullValue());
    }
}
