package ru.praktikum_services.qa_scooter;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;


public class SetUp {
    private static RequestSpecification requestSpec;

    public SetUp (RequestSpecification requestSpec) {
        SetUp.requestSpec = requestSpec;
    }

    public static RequestSpecification getRequestSpecification()
    {
        requestSpec = RestAssured.given()
                .baseUri("http://qa-scooter.praktikum-services.ru/");
        return requestSpec;
    }
}
