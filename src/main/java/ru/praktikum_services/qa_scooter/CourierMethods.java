package ru.praktikum_services.qa_scooter;

import com.google.gson.Gson;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class CourierMethods extends SetUp {

    public CourierMethods(RequestSpecification requestSpec) {
        super(requestSpec);
    }

    public static final String CREATE_COURIER_URL = "/api/v1/courier";
    public static final String COURIER_LOGIN_URL = "/api/v1/courier/login";
    public static final String DELETE_COURIER_URL = "/api/v1/courier/";

    //создание курьера
    public static Response createCourier(Courier courier) {
        return
                given()
                        .spec(getRequestSpecification())
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post(CREATE_COURIER_URL);
    }

    //логин курьера в системе
    public static Response сourierLogin(Courier courier) {
        return
                given()
                        .spec(getRequestSpecification())
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post(COURIER_LOGIN_URL);
    }

    //получение id курьера
    public static int getCourierId(Courier courier) {
        Gson gson = new Gson();
        Response response = сourierLogin(courier);
        String message = response.body().asString();
        CourierId answer = gson.fromJson(message, CourierId.class);
        int courierId = answer.getId();
        return courierId;
    }

    //удаление курьера из системы
    public static void deleteCourier(Courier courier) {
        int id = getCourierId(courier);
        CourierId courierId = new CourierId(id);
        given()
                .spec(getRequestSpecification())
                .header("Content-type", "application/json")
                .and()
                .body(courierId)
                .when()
                .delete(DELETE_COURIER_URL + id);
    }
}
