import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.Color;
import ru.praktikum_services.qa_scooter.Orders;

import java.util.List;


import static ru.praktikum_services.qa_scooter.OrdersMethods.cancelOrder;
import static ru.praktikum_services.qa_scooter.OrdersMethods.createOrder;
import static org.apache.http.HttpStatus.*;

@RunWith(Parameterized.class)

public class CreateOrderTest {
    private final List<Color> COLOR;

    public CreateOrderTest(List<Color> color) {
        this.COLOR = color;
    }

    @Parameterized.Parameters
    public static Object[] getColorData() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                null
        };
    }

    @Test
    @DisplayName("Проверки: можно указать один из цветов — BLACK или GREY; можно указать оба цвета; можно совсем не указывать цвет")
//
    public void CreateOrdersWithVariableAndNotRequiredColor() {
        Orders order = new Orders("Акакий", "Акакиев", "г.Москва, ул.Чижова, д.32, кв.1",
                "Медведково", "8-963-555-44-33", 5, "2020-06-06",
                "Жду с нетерпением", COLOR);

        createOrder(order)
                .then()
                .statusCode(SC_CREATED);

        cancelOrder(order);
    }
}
