package tradingcarbon.my_app.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import tradingcarbon.my_app.config.BaseIT;


public class ProjectResourceTest extends BaseIT {

    @Test
    @Sql({"/data/userData.sql", "/data/projectData.sql"})
    void getAllProjects_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/projects")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).projectId", Matchers.equalTo("a96e0a04-d20f-3096-bc64-dac2d639a577"));
    }

    @Test
    @Sql({"/data/userData.sql", "/data/projectData.sql"})
    void getProject_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/projects/a96e0a04-d20f-3096-bc64-dac2d639a577")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("projectName", Matchers.equalTo("Et ea rebum."));
    }


    @Test
    @Sql("/data/userData.sql")
    void createProject_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/projectDTORequest.json"))
                .when()
                    .post("/api/projects")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, projectRepository.count());
    }

    @Test
    @Sql({"/data/userData.sql", "/data/projectData.sql"})
    void updateProject_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/projectDTORequest.json"))
                .when()
                    .put("/api/projects/a96e0a04-d20f-3096-bc64-dac2d639a577")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("Vel illum dolore.", projectRepository.findById(UUID.fromString("a96e0a04-d20f-3096-bc64-dac2d639a577")).orElseThrow().getProjectName());
        assertEquals(2, projectRepository.count());
    }

    @Test
    @Sql({"/data/userData.sql", "/data/projectData.sql"})
    void deleteProject_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/projects/a96e0a04-d20f-3096-bc64-dac2d639a577")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, projectRepository.count());
    }

}
