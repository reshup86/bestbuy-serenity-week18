package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductsPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ProductsSteps {

    @Step("Creating product with name :{0}, type : {1}, price :{2}, shipping:{3},upc:{4},description:{5},manufacturer:{6},model:{7},url:{8},image{9}")
    public ValidatableResponse createProduct(String name, String type, int price, Integer shipping, String upc, String description, String manufacturer, String model, String url, String image) {

        ProductsPojo productsPojo = new ProductsPojo();
        productsPojo.setName(name);
        productsPojo.setType(type);
        productsPojo.setPrice(price);
        productsPojo.setShipping(shipping);
        productsPojo.setUpc(upc);
        productsPojo.setDescription(description);
        productsPojo.setManufacturer(manufacturer);
        productsPojo.setModel(model);
        productsPojo.setUrl(url);
        productsPojo.setImage(image);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(productsPojo)
                .when()
                .post(EndPoints.CREATE_PRODUCTS)
                .then();
    }

    @Step("Get product details of id : {0}")
    public HashMap<String, Object> getProductInfoById(int productID) {
        HashMap<String, Object> productMap = SerenityRest.given().log().all()
                .when()
                .pathParams("productID", productID)
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then()
                .statusCode(200)
                .extract()
                .path("");
        return productMap;
    }

    @Step("Update product details of id: {0}")
    public ValidatableResponse updateProduct(int productID, String name) {
        ProductsPojo productPojo = new ProductsPojo();
        productPojo.setName(name);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .body(productPojo)
                .pathParam("productID", productID)
                .when()
                .patch(EndPoints.UPDATE_SINGLE_PRODUCT_BY_ID)
                .then();
    }


    @Step("Delete product details by id: {0}")
    public ValidatableResponse deleteProduct(int productID) {

        return SerenityRest.given().log().all()
                .pathParam("productID", productID)
                .when()
                .delete(EndPoints.DELETE_SINGLE_PRODUCT_BY_ID)
                .then();
    }

    @Step("Getting product with ID {0}")
    public ValidatableResponse getProductByID(int productID) {
        return SerenityRest.given().log().all()
                .pathParam("productID", productID)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then();
    }
}
