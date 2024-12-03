package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class ApiSpecs {

    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setContentType(JSON)
            .log(ALL)
            .addFilter(withCustomTemplates()) // Кастомный фильтр для шаблонов Allure
            .build();

    public static ResponseSpecification successLoginResponse200Spec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(ALL)
            .build();

    public static ResponseSpecification successDeleteAllBooksResponse204Spec = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(ALL)
            .build();
}
