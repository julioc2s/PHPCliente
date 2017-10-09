package com.android.phpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import com.google.gson.Gson;
import android.util.Log;

public class ServerAccess {

	//Convert an inputstream to a string
	public static String convertStreamToString(InputStream is) 
    {
    	BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    	StringBuilder sb = new StringBuilder();
    	
    	String line = null;
    	try {
    		while ((line = reader.readLine()) != null) {
    			sb.append(line + "\n");
    		}
    	} 
    	catch (IOException e) {
    		Log.e("PHP Client","Error : "+e.getMessage());
    	} 
    	finally {
    		try {
    			is.close();
    		} catch (IOException e1) {
    			Log.e("PHP Client","Error : "+e1.getMessage());
    		}
    	}
    	return sb.toString();
    }

	//Get the response form the server as an object
	public Object getResponseObject(ArrayList<NameValuePair> Parameters,Class c)
	{
		try{
			//Create a HTTP Client
			HttpClient httpclient = new DefaultHttpClient();
			
			//Create and object to Post values to the server
			//The url is specified in the Constants class to increase modifiability
			HttpPost httppost = new HttpPost(Constants.SERVICE_URL);
			
			//Set the attributes to be posted as Parameters
			httppost.setEntity(new UrlEncodedFormEntity(Parameters));
			
			//Execute the post and get the response
			HttpResponse response = httpclient.execute(httppost);
			
			//Get the response as ans entity
			HttpEntity entity = response.getEntity();
			
			//Get the content of the response as a stream
			InputStream stream=entity.getContent();
			
			//Convert the stream to a GSON object
	        String result= convertStreamToString(stream);
	        
	        //Create a GSON object
	        Gson gson=new Gson();
	        
	        //Convert the respose string to a object of the given type
	        //Here Object class is used so that we can use the same method to get any
	        //class's object as response
			Object responseObject=gson.fromJson(result, c);
			//Return the object 
	        return responseObject;
		}catch(Exception e){
			Log.e("PHP Client", "Error in http connection"+e.toString());
			return null;
		}
	}
	
	//Verify the login and return user id or 0
	public int getUserID(String Username,String Password)
	{
		//Create an arraylist to store the parameters and values as key value pairs
		ArrayList<NameValuePair> parameters=new ArrayList<NameValuePair>();
		
		//Please make sure the spellings of the keys are correct 
		//Set the method name
		parameters.add(new BasicNameValuePair("method","verifyLogin"));
		//Set the username
		parameters.add(new BasicNameValuePair("username",Username));
		//Set the password
		parameters.add(new BasicNameValuePair("password",Password));
		//Get the result from the server using the getresponse method
		LoginResult o= (LoginResult)getResponseObject(parameters,LoginResult.class);
		return Integer.parseInt(o.result);
	}

	public String getUserINFO(String ID)
	{
		//Create an arraylist to store the parameters and values as key value pairs
		ArrayList<NameValuePair> parameters=new ArrayList<NameValuePair>();

		//Please make sure the spellings of the keys are correct
		//Set the method name
		parameters.add(new BasicNameValuePair("method","getUserINFO"));
		//Set the username
		parameters.add(new BasicNameValuePair("ID",ID));

		//Get the result from the server using the getresponse method
		LoginResult o= (LoginResult)getResponseObject(parameters,LoginResult.class);
		return String.valueOf(o.result);
	}


	public int gravarRegistro(String Username,String Password, String Nome, String Endereco, String IDAdmin)
	{
		//Create an arraylist to store the parameters and values as key value pairs
		ArrayList<NameValuePair> parameters=new ArrayList<NameValuePair>();

		//Please make sure the spellings of the keys are correct
		//Set the method name
		parameters.add(new BasicNameValuePair("method","gravarRegistro"));
		//Set the username
		parameters.add(new BasicNameValuePair("username",Username));
		//Set the password
		parameters.add(new BasicNameValuePair("password",Password));
		//Set the nome
		parameters.add(new BasicNameValuePair("nome",Nome));
		//Set the endereco
		parameters.add(new BasicNameValuePair("endereco",Endereco));
		//Set the IDAdmin
		parameters.add(new BasicNameValuePair("IDAdmin",IDAdmin));

		//Get the result from the server using the getresponse method
		LoginResult o= (LoginResult)getResponseObject(parameters,LoginResult.class);
		return Integer.parseInt(o.result);
	}

	public int excluirRegistro(String ID, String IDAdmin)
	{
		//Create an arraylist to store the parameters and values as key value pairs
		ArrayList<NameValuePair> parameters=new ArrayList<NameValuePair>();

		//Please make sure the spellings of the keys are correct
		//Set the method name
		parameters.add(new BasicNameValuePair("method","excluirRegistro"));
		//Set the username
		parameters.add(new BasicNameValuePair("ID",ID));
		parameters.add(new BasicNameValuePair("IDAdmin",IDAdmin));

		//Get the result from the server using the getresponse method
		LoginResult o= (LoginResult)getResponseObject(parameters,LoginResult.class);
		return Integer.parseInt(o.result);
	}

	//Get the employees 
	public Employee[] getEmployees(int id)
	{
		//Create the arraylist to set the parameters of the post
		ArrayList<NameValuePair> parameters=new ArrayList<NameValuePair>();
		//Set the method name
		parameters.add(new BasicNameValuePair("method","getEmployees"));
		parameters.add(new BasicNameValuePair("id",String.valueOf(id)));
		
		//Get the array of employee arrays as the result
		Employee[] o= (Employee[])getResponseObject(parameters,Employee[].class);
		return o;
	}

	public int gravarLocalizacao(String id,String latitude, String longitude)
	{
		//Create an arraylist to store the parameters and values as key value pairs
		ArrayList<NameValuePair> parameters=new ArrayList<NameValuePair>();

		//Please make sure the spellings of the keys are correct
		//Set the method name
		parameters.add(new BasicNameValuePair("method","gravarLocalizacao"));
		//Set the username
		parameters.add(new BasicNameValuePair("id",id));
		//Set the password
		parameters.add(new BasicNameValuePair("latitude",latitude));
		//Set the nome
		parameters.add(new BasicNameValuePair("longitude",longitude));

		//Get the result from the server using the getresponse method
		LoginResult o= (LoginResult)getResponseObject(parameters,LoginResult.class);
		return Integer.parseInt(o.result);
	}
}
