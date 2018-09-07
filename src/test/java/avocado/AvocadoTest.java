package avocado;

import com.cegeka.switchfully.security.ArmyInfoDto;
import com.cegeka.switchfully.security.ArmyResource;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class AvocadoTest extends RestAssuredTest {

    @Test
    public void getDeployedArmyInfo_givenKnownUsernameAndPasswordEncodedAsBasicAuthenticationHeader_thenShouldAllowAccess() {
        ArmyInfoDto actual = givenRequestForUser("JMILLER", "THANKS")
                .when()
                .get(String.format("%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "Belgium"))
                .then()
                .assertThat()
                .statusCode(OK.value())
                .extract()
                .body()
                .as(ArmyInfoDto.class);

        assertThat(actual.country).isEqualTo("Belgium");
    }

    @Test
    public void getDeployedArmyInfo_givenKnownUsernameAndWrongPasswordEncodedAsBasicAuthenticationHeader_thenShouldNotAllowAccess() {
        assertUnauthorized("JMILLER", "JBAKER", String.format("%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "Belgium"));
    }

    @Test
    public void getDeployedArmyInfo_givenUnknownUsernameAndPasswordEncodedAsBasicAuthenticationHeader_thenShouldNotAllowAccess() {
        assertUnauthorized("FONZ", "AYE", String.format("%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "Belgium"));
    }

    @Test
    public void launchNukes_givenKnownUsernameAndWrongPasswordEncodedAsBasicAuthenticationHeader_thenShouldNotAllowAccess() {
        assertUnauthorized("JMILLER", "JBAKER", String.format("%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "nuke"));
    }

    @Test
    public void launchNukes_givenUnknownUsernameAndPasswordEncodedAsBasicAuthenticationHeader_thenShouldNotAllowAccess() {
        assertUnauthorized("FONZ", "AYE", String.format("%s/%s", ArmyResource.ARMY_RESOURCE_PATH, "Belgium"));
    }

    private void assertUnauthorized(String user, String password, String endpoint) {
        givenRequestForUser(user, password)
                .when()
                .get(endpoint)
                .then()
                .assertThat()
                .statusCode(UNAUTHORIZED.value());
    }
}
