package second.project.stepdefinition;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CityWeather {

	Response res = null;
	
	@Given("check the weather API with $URI with the $city")
	public void theWeatherURI(String baseURL, String cityName) {
		RestAssured.baseURI = baseURL;
		RequestSpecification httpReq = RestAssured.given();		
		res = httpReq.get(cityName);
	}
	
	@Then("the body of response should contain city name at the node city")
	public void theBodyOfResponseShouldContain() {
		String responseBody = res.getBody().asString();
		boolean cityIsPresent = responseBody.contains("Hyderabad");
		int code = res.getStatusCode();
		System.out.println(responseBody);
	}
	
	
	
		
		
		
	
}
