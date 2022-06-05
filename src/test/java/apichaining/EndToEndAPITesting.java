package apichaining;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndToEndAPITesting {
	Response response;
	
	@Test
	public void test() {
		
response = GetAllMethod();
String ResponseBody = response.getBody().asString();
System.out.println(ResponseBody);
Assert.assertEquals(response.getStatusCode(), 200);
		
response = PostMethod("karan","suresh","3000","karans@gmail.com");
		
		Assert.assertEquals(response.getStatusCode(), 201);
		JsonPath Jpath = response.jsonPath();
        int EmpID= Jpath.get("id");
        System.out.println(EmpID);
	
        response = Putmethod(EmpID,"kiran","ananthram");
        AssertJUnit.assertEquals(response.getStatusCode(), 200);
        
        response = Deletemethod(EmpID);
        AssertJUnit.assertEquals(response.getStatusCode(), 200);
        AssertJUnit.assertEquals(response.getBody().asString(), "{}");
        
        response = Getmethod(EmpID);
        AssertJUnit.assertEquals(response.getStatusCode(), 404);
        AssertJUnit.assertEquals(response.getBody().asString(), "{}");
		
		
	}
	
	public Response GetAllMethod() {
		
		RestAssured.baseURI = "http://54.221.60.0:8088/employees";
		RequestSpecification request = RestAssured.given();
		  Response response= request.get();
		  
		   
		   
		   return response;
	}
	
public Response PostMethod(String firstName, String lastName, String salary, String email) {
		
		JSONObject jobj = new JSONObject();
		jobj.put("firstName", firstName);
		jobj.put("lastName", lastName);
		jobj.put("salary", salary);
		jobj.put("email", email);
		

		RestAssured.baseURI = "http://54.221.60.0:8088/employees";

		RequestSpecification request = RestAssured.given();
		
		        Response response=request.contentType(ContentType.JSON).accept(ContentType.JSON).body(jobj.toString()).post();
		return response;
		
		
	}
public Response Putmethod(int EmpID, String firstName, String lastName) {
	
	JSONObject jobj = new JSONObject();
	jobj.put("firstName", firstName);
	jobj.put("lastName", lastName);

	RequestSpecification request = RestAssured.given();
	
	        Response response=request.contentType(ContentType.JSON).accept(ContentType.JSON).body(jobj.toString()).put("/"+EmpID);
	return response;
}

public Response Deletemethod(int EmpID) {
	RestAssured.baseURI = "http://localhost:3000/employees";
	
	RequestSpecification request = RestAssured.given();
			
			Response response = request.delete("/" + EmpID);
			
			return response;
}
public Response Getmethod(int EmpID) {

	
	RequestSpecification request = RestAssured.given();
			
			Response response = request.get("/" + EmpID);
			
			return response;
}
}