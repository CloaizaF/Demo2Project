package org.authexample;

import io.restassured.RestAssured;
import org.pojo.place.LocationDetails;
import org.pojo.place.PlaceDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SerializeTest {

    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        LocationDetails locationDetails = new LocationDetails();
        locationDetails.setLat(-38.383494);
        locationDetails.setLng(33.427362);

        List<String> types = new ArrayList<>();
        types.add("shoe park");
        types.add("shop");

        PlaceDetails placeDetails = new PlaceDetails();
        placeDetails.setLocation(locationDetails);
        placeDetails.setAccuracy(50);
        placeDetails.setName("Rahul Shetty Academy");
        placeDetails.setPhone_number("(+91) 983 893 3937");
        placeDetails.setAddress("29, side layout, cohen 09");
        placeDetails.setTypes(types);
        placeDetails.setWebsite("http://rahulshettyacademy.com");
        placeDetails.setLanguage("French-IN");

        String addPlaceResponse = given().queryParam("key", "qaclick123").
                header("Content-Type", "application/json").body(placeDetails)
                .when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response().asString();

        System.out.println(addPlaceResponse);
    }
}
