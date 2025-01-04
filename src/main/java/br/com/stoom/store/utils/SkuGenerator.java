package br.com.stoom.store.utils;

import org.springframework.stereotype.Component;

@Component
public class SkuGenerator {

    public String generateSku(String categoryName, String brandName, String productName) {
        return getFirstThreeChars(categoryName) + "-" +
                getFirstThreeChars(brandName) + "-" +
                getFirstThreeChars(productName) + "-" +
                System.currentTimeMillis();
    }

    private static String getFirstThreeChars(String value) {
        return value.length() > 3 ? value.substring(0, 3) : value;
    }
}
