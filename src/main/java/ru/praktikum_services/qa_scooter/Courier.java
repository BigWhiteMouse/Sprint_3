package ru.praktikum_services.qa_scooter;

import com.google.gson.Gson;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Courier {

    private String login;
    private String password;
    private String firstName;
    private String ok;

    public Courier(String login, String password, String firstName){
        this.login = login;
        this.password = password;
        this.firstName = firstName;
   }

    public Courier(){

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    //создание курьера
    public static Response createCourier(Courier courier){
        return
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    //логин курьера в системе
    public static Response сourierLogin (Courier courier){
        return
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
     }

     //получение id курьера
     public static int getCourierId(Courier courier){
         Gson gson = new Gson();
         Response response = сourierLogin(courier);
         String message = response.body().asString();
         CourierId answer = gson.fromJson (message, CourierId.class);
         int courierId = answer.getId();
         return courierId;
     }

     //удаление курьера из системы
     public static void deleteCourier(Courier courier){
        int id = getCourierId(courier);
        CourierId courierId = new CourierId(id);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courierId)
                .when()
                .delete("/api/v1/courier/"+id);
     }
}
