package org.ecommerceexample;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.pojo.ecommerce.LoginRequest;
import org.pojo.ecommerce.LoginResponse;
import org.pojo.ecommerce.OrderDetail;
import org.pojo.ecommerce.OrdersList;
import org.testng.Assert;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EcommerceAPITest {
    public static void main(String[] args) {

        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).build();

        LoginRequest loginRequestObject = new LoginRequest();
        loginRequestObject.setUserEmail("loaizafcamilo@gmail.com");
        loginRequestObject.setUserPassword("EnjoyLife1104%");

        RequestSpecification loginRequest = given().relaxedHTTPSValidation().log().all().spec(requestSpecification).body(loginRequestObject);
        LoginResponse loginResponseObject = loginRequest.when().post("/api/ecom/auth/login")
                .then().log().all().extract().response().as(LoginResponse.class);

        System.out.println(loginResponseObject.getToken());
        System.out.println(loginResponseObject.getUserId());

        String token = loginResponseObject.getToken();
        String userId = loginResponseObject.getUserId();

        //Add Product
        RequestSpecification addProductSpec = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token)
                .build();

        RequestSpecification addProductRequest = given().log().all().spec(addProductSpec)
                .param("productName", "qwerty")
                .param("productAddedBy", userId)
                .param("productCategory", "fashion")
                .param("productSubCategory", "shirts")
                .param("productPrice", "11500")
                .param("productDescription", "Adidas Originals")
                .param("productFor", "women")
                .multiPart("productImage", new File("C:\\Users\\loaiz\\IdeaProjects\\Demo2project\\src\\main\\resources\\adidas.jpeg"));

        String addProductResponse = addProductRequest.when().post("/api/ecom/product/add-product")
                .then().log().all().extract().response().asString();

        JsonPath productResponse = new JsonPath(addProductResponse);
        String productId = productResponse.get("productId");

        //Create Order
        RequestSpecification createOrderSpec = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON)
                .addHeader("authorization", token).build();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCountry("Colombia");
        orderDetail.setProductOrderedId(productId);

        List<OrderDetail> ordersDetailList = new ArrayList<OrderDetail>();
        ordersDetailList.add(orderDetail);

        OrdersList ordersList = new OrdersList();
        ordersList.setOrders(ordersDetailList);

        RequestSpecification createOrderRequest = given().log().all().spec(createOrderSpec).body(ordersList);
        String responseCreateOrder = createOrderRequest.when().post("/api/ecom/order/create-order")
                .then().log().all().extract().response().asString();

        System.out.println(responseCreateOrder);

        //Delete Product

        RequestSpecification deleteProductSpec = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON)
                .addHeader("authorization", token).build();

        RequestSpecification deleteProductRequest = given().log().all().spec(deleteProductSpec).pathParam("productId", productId);

        String deleteProductResponse = deleteProductRequest.when().delete("/api/ecom/product/delete-product/{productId}")
                .then().log().all().extract().response().asString();

        JsonPath deleteProductResponseJson = new JsonPath(deleteProductResponse);
        String message = deleteProductResponseJson.get("message");

        Assert.assertEquals("Product Deleted Successfully", message);

    }
}
