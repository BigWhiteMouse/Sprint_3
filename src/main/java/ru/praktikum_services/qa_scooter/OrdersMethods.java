package ru.praktikum_services.qa_scooter;

import com.google.gson.Gson;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class OrdersMethods extends SetUp {
    public OrdersMethods(RequestSpecification requestSpec) {
        super(requestSpec);
    }

    public static final String CREATE_ORDER_URL = "/api/v1/orders";
    public static final String GET_ORDER_LIST_URL = "/api/v1/orders";
    public static final String CANCEL_ORDER_URL = "/api/v1/orders/cancel?track=";

    //создание заказа
    public static Response createOrder(Orders order) {
        return
                given()
                        .spec(getRequestSpecification())
                        .header("Content-type", "application/json")
                        .and()
                        .body(order)
                        .when()
                        .post(CREATE_ORDER_URL);
    }

    //получение номера трэка
    public static int getTrack(Orders order) {
        Gson gson = new Gson();
        Response response = createOrder(order);
        String message = response.body().asString();
        OrderTrack answer = gson.fromJson(message, OrderTrack.class);
        int track = answer.getTrack();
        return track;

    }

    //получение списка заказов
    public static Response getOrderList(){
        return
        given()
                .spec(getRequestSpecification())
                .header("Content-type", "application/json")
                .when()
                .get(GET_ORDER_LIST_URL);
    }

    //отмена заказа
    /*опытным путем пришла к выводу, что в документации ссылка на отмену заказа работает не совсем корректно,
    рабочая ссылка - api/v1/orders/cancel?track=track
     */
    public static void cancelOrder(Orders order) {
        int track = getTrack(order);
        OrderTrack orderTrack = new OrderTrack(track);
        given()
                .spec(getRequestSpecification())
                .header("Content-type", "application/json")
                .and()
                .body(orderTrack)
                .when()
                .put(CANCEL_ORDER_URL + track);
    }
}
