import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;//equalTo is from Hemcrest package

import org.testng.Assert;

import Files.Payload;
public class GetPlace2 {
	
	public static void main(String []args)
	{
		//validate if Add place API is working as expected
		
		//given-all input details
		//when-submit the API-resouce & http method
		//Then-validate the response
		
		//Add Place->update place with new address->get place to validate if new address is present in response
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(Payload.AddPlace()).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope",equalTo("APP")).extract().response().asString();
		
		System.out.println(response);
		JsonPath js=new JsonPath(response);//for parsing json
		
		String placeId=js.getString("place_id");
		System.out.println(placeId);
		
		//update place
		
		String newAddress="Sumer Kendra, Mumbai";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+ "\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "").
		when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));	
		
		
		//get Place
		
		String getPlaceResponse=given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", "placeId")
		.when().get("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js1=new JsonPath(getPlaceResponse);
		
		String actualAddress=js1.getString("address");
		
		System.out.println(actualAddress);
		
		Assert.assertEquals("actualAddress", "newAddress");
	}

}
