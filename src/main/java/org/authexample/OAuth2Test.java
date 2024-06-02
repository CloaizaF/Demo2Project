package org.authexample;

import io.restassured.path.json.JsonPath;
import org.pojo.course.CourseData;
import org.pojo.course.CourseMetaData;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OAuth2Test {

    public static void main(String[] args) {

        String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};

        String authServerResponse = given()
                .formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com",
                        "client_secret", "erZOWM9g3UtwNRj340YYaK_W",
                        "grant_type", "client_credentials",
                        "scope", "trust")
                .when().log().all()
                .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

        System.out.println(authServerResponse);

        JsonPath authServerResponseJson = new JsonPath(authServerResponse);
        String accessToken = authServerResponseJson.get("access_token");

        CourseMetaData courseDetailsDTO = given()
                .queryParam("access_token", accessToken)
                .when().log().all()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(CourseMetaData.class);

        System.out.println(courseDetailsDTO.getInstructor());
        System.out.println(courseDetailsDTO.getLinkedIn());

        System.out.println(courseDetailsDTO.getCourses().getApi().get(1).getCourseTitle());

        List<CourseData> apiCourses = courseDetailsDTO.getCourses().getApi();

        for(int i = 0; i < apiCourses.size(); i++){
            if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
                System.out.println(apiCourses.get(i).getPrice());
            }
        }

        ArrayList<String> foundCourses = new ArrayList<String>();
        List<CourseData> webAutomationCourses = courseDetailsDTO.getCourses().getWebAutomation();

        for(int i = 0; i < webAutomationCourses.size(); i++){
            foundCourses.add(webAutomationCourses.get(i).getCourseTitle());
        }

        List<String> expectedCourses = Arrays.asList(courseTitles);

        Assert.assertEquals(expectedCourses, foundCourses);

    }
}
