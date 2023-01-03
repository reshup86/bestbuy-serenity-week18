package com.bestbuy.testsuite;

import com.bestbuy.bestbuyinfo.StoresSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StoresCRUDTestWithSteps extends TestBase {

    static String name = "Sia" + TestUtils.getRandomValue();
    static String type = "Big Box" + TestUtils.getRandomValue();
    static String address = "111 Apple lane";
    static String address2 = "Apple Place";
    static String city = "Ahmedabad";
    static String state = "Gujarat";
    static String zip = "12345";
    static Double lat = 44.05;
    static Double lng = 93.56;
    static String hours = "Mon-Sat:10-5";
    static HashMap<Object, Object> services = new HashMap<>();

    static int storeID;


    @Steps
    StoresSteps storesSteps;

    @Title("This will create a new store")
    @Test
    public void test001(){

        ValidatableResponse response = storesSteps.createStore(name,type,address,address2,city,state,zip,lat,lng,hours,services);
        response.log().all().statusCode(201);
        storeID = response.log().all().extract().path("id");
    }

    @Title("This will verify that store was added")
    @Test
    public void test002(){

        HashMap<String, Object> storeMap = storesSteps.getStoreById(storeID);
        Assert.assertThat(storeMap, hasValue(name));
    }

    @Title("This test will Update the services information")
    @Test
    public void test003() {
        name = name + "_updated";
        storesSteps.updateStore(storeID,name);
        HashMap<String, ?> storesList = storesSteps.getStoreById(storeID);
        Assert.assertThat(storesList, hasValue(name));
        System.out.println(storesList);
    }

    @Title("This test will Delete the stores by ID")
    @Test
    public void test004() {
        storesSteps.deleteStore(storeID).statusCode(200).log().all();
        storesSteps.getStoreByID(storeID).statusCode(404).log().all();
    }
}
