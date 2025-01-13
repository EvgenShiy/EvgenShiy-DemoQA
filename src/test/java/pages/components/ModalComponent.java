package pages.components;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class ModalComponent {

    public void checkResult(String key, String value) {
        $(".modal-dialog").should(appear);
        $(".table-responsive").$(byText(key)).parent().shouldHave(text(value));
    }


    public void negativeCheck() {
        $("form#userForm").shouldHave(attribute("class", "was-validated"));
    }
}
