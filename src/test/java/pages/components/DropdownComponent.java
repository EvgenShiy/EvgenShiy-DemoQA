package pages.components;

import static com.codeborne.selenide.Selenide.$;

public class DropdownComponent {
    public void setState(String value) {
        $("#react-select-3-input").setValue(value).pressEnter();
    }

    public void setCity(String value) {
        $("#react-select-4-input").setValue(value).pressEnter();
    }
}
