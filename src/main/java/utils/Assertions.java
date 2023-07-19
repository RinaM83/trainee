package utils;

import elements.BaseElement;
import org.assertj.core.api.SoftAssertions;

public class Assertions {
    public static final String IS_VISIBLE = "Check that element %s is visible";
    public static final String IS_NOT_VISIBLE = "Check that element %s is not visible";
    public static void isVisible(SoftAssertions softAssertions, BaseElement element, String name){
        softAssertions.assertThat(element.isVisible()).as(String.format(IS_VISIBLE,name)).isTrue();
    }

    public static void isNotVisible(SoftAssertions softAssertions, BaseElement element, String name){
        softAssertions.assertThat(element.isNotVisible()).as(String.format(IS_NOT_VISIBLE,name)).isTrue();
    }
}
