package second.project;


import org.json.simple.JSONObject;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;



public class RestAutomationBasics {
	
	@Test
	public void getRequest() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpReq = RestAssured.given();
		
		Response res = httpReq.get("/Hyderabad");
		
		String responseBody = res.getBody().asString();
		boolean cityIsPresent = responseBody.contains("Hyderabad");
		int code = res.getStatusCode();
		System.out.println(responseBody);
		
	}
	@Test
	public void getRequestResponseCheckUsingJson() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification req = RestAssured.given();
		
		Response res = req.get("/Hyderabad");
		
		JsonPath pathEvaluator = 	res.jsonPath();
		String city = pathEvaluator.get("City");
		
		/*
		ResponseBody body = res.getBody();
		//we have created CityTemperature class with all the node values as variables
		CityTemperature responseBody = body.as(CityTemperature.class);
		
		*/
	}
	
	@Test
	//POST request is used to submit the forms. It will create a new row in the DataBase. Return code is 200
	//PUT request is used to update the changes for the already existing record of Database. Return code is 201
	public void postRequest() {
		RestAssured.baseURI = "http://restapi.demoqa.com/customer";
		RequestSpecification req = RestAssured.given();
		
		JSONObject reqParm = new JSONObject();
		reqParm.put("FirstName", "Sowmya");
		reqParm.put("LastName", "Murukutla");
		reqParm.put("UserName", "sdimpleuser2dd2011");
		reqParm.put("Password", "password1");	
		reqParm.put("Email", "sample2ee26d9@gmail.com");
		
		req.body(reqParm.toJSONString());
		
		Response res = req.post("/register");
		
		int code = res.getStatusCode();
		String responseBody = res.getBody().asString();
		System.out.println(responseBody);
		
	}
	
	@Test
	public void postRequestResponseCheckUsingJsonDeserializing() {
		RestAssured.baseURI ="http://restapi.demoqa.com/customer";
		RequestSpecification req = RestAssured.given();
	
		JSONObject reqParams = new JSONObject();
		reqParams.put("FirstName", "Virender"); 
		reqParams.put("LastName", "Singh");
		reqParams.put("UserName", "63userf2d3d2011");
		reqParams.put("Password", "password1");	
		reqParams.put("Email","ed26dff39@gmail.com");
	
		req.body(reqParams.toJSONString());
		Response res = req.post("/register");
	
		ResponseBody body = res.getBody();
		System.out.println(res.body().asString());
	
		if(res.statusCode() == 200)
		{
			//we have created RegistrationFailureResponse class with all the node values as variables
			RegistrationFailureResponse responseBody = body.as(RegistrationFailureResponse.class);
	
			Assert.assertEquals("User already exists", responseBody.FaultId);
			Assert.assertEquals("FAULT_USER_ALREADY_EXISTS", responseBody.fault);	
		}
		else if (res.statusCode() == 201)
		{
			//we have created RegistrationSuccessResponse class with all the node values as variables
			RegistrationSuccessResponse responseBody = body.as(RegistrationSuccessResponse.class);
			
			Assert.assertEquals("OPERATION_SUCCESS", responseBody.SuccessCode);
			Assert.assertEquals("Operation completed successfully", responseBody.Message);	
		}	
	}
	

}
