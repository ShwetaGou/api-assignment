package org.openweather.stepdefinations;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import static io.restassured.RestAssured.*;

public class OpenweatherStepDef {

	RequestSpecification request;
	RequestSpecification oneCallrequest;
	Response response;
	String path;
	String apiKey;

	@Given("User sets current weather api endpoint {string}")
	public void user_sets_current_weather_api_endpoint(String baseURI) {
		RestAssured.baseURI = baseURI;
	}

	@And("User authenticate the API request with {string}")
	public void user_authenticate_the_API_request_with(String apikey) {
		this.apiKey = apikey;
		request = given().queryParam("appid", apikey);
	}

	// --------------------scenario0 1-------------------
	@Given("User sends HTTP request and get the response {string}")
	public void user_sends_HTTP_request_and_get_the_response(String requestpath) {
		path = requestpath;

	}

	@Given("I like to holiday in {string} {string}")
	public void i_like_to_holiday_in(String cityname, String country) {
		request.queryParam("q", cityname + "," + country);
		String path = "weather";
		Response responseForweather = request.when().get(path).then().extract().response();
		System.out.println("test");

		String lon = responseForweather.jsonPath().getString("coord.lon");
		String lat = responseForweather.jsonPath().getString("coord.lat");

		oneCallrequest = given().queryParam("appid", apiKey);
		oneCallrequest.queryParam("lon", lon).queryParam("lat", lat);

	}

	@Given("I only like to holiday on {string}")
	public void i_only_like_to_holiday_on(String day) {
		oneCallrequest.queryParam("exclude", "hourly,minutely");

	}

	@When("I look up the weather forecast")
	public void i_look_up_the_weather_forecast() {
		response = oneCallrequest.get("onecall").then().extract().response();

	}

	@Then("I receive the weather forecast")
	public void i_receive_the_weather_forecast() {
		response.then().log().all().statusCode(200);

	}

	@Then("the temperature is warmer than {int} degrees")
	public void the_temperature_is_warmer_than_degrees(Integer expectedTemperature) {

		String temp = response.jsonPath().getString("daily[7].temp.day");

		double d = Double.parseDouble(temp);
		int i = (int) d;
		System.out.println("temp :" + temp);

		if (i - 273 > expectedTemperature) {
			Assert.assertTrue(" tempreature is greter than 10 degree", true);
		} else {
			Assert.fail("Temperature is less than 10");
		}

	}

}
