package com.dogapi.utils;

import org.testng.annotations.DataProvider;

public class TestData {

    @DataProvider(name = "validBreeds")
    public static Object[][] validBreeds() {
        return new Object[][]{
                {"hound"},
                {"labrador"},
                {"bulldog"}
        };
    }
}
