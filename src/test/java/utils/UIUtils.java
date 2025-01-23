package utils;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class UIUtils {
    public static void removeAdvertisements() {
        executeJavaScript("$('#fixedban').remove();");
        executeJavaScript("$('footer').remove();");
        executeJavaScript("document.querySelectorAll('.advertisement-class').forEach(el => el.style.display = 'none');");
    }
}
