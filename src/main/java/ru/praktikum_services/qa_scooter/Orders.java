package ru.praktikum_services.qa_scooter;

import com.google.gson.Gson;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class Orders {
    private String firstname;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<Color> color;

    public Orders(String firstname, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<Color> color) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public void setRentTime(int rentTime) {
        this.rentTime = rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Color> getColor() {
        return color;
    }

    public void setColor(List<Color> color) {
        this.color = color;
    }

    //создание заказа
    public static Response createOrder(Orders order) {
        return
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(order)
                        .when()
                        .post("/api/v1/orders");
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

    //отмена заказа
    /*опытным путем пришла к выводу, что в документации ссылка на отмену заказа работает не совсем корректно,
    рабочая ссылка - api/v1/orders/cancel?track=track
     */
    public static void cancelOrder(Orders order) {
        int track = getTrack(order);
        OrderTrack orderTrack = new OrderTrack(track);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(orderTrack)
                .when()
                .put("/api/v1/orders/cancel?track=" + track);
    }
}

