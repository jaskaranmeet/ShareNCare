package com.example.hp.sharencare.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.sharencare.R;
import com.example.hp.sharencare.httpRequestProcessor.*;
import com.example.hp.sharencare.apiconfiguration.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RegActivity extends AppCompatActivity {
    private Button submit;
    private EditText editName, editAddress, editEmailId, editMobNo, editusrname, editPwd;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlRegister;
    private String Name, EmailId, Phone, Address, UserName, Password;
    private String jsonPostString, jsonResponseString;

    private String message;
    private boolean success;
    private int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        submit = (Button) findViewById(R.id.submit);
        editName = (EditText) findViewById(R.id.editName);
        editAddress = (EditText) findViewById(R.id.editAddress);
        editEmailId = (EditText) findViewById(R.id.editEmailId);
        editMobNo = (EditText) findViewById(R.id.editMobNo);
        editusrname = (EditText) findViewById(R.id.editusrname);
        editPwd = (EditText) findViewById(R.id.editPwd);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting BaseURL
        baseURL = apiConfiguration.getApi();
        urlRegister = baseURL + "AccountAPI/SaveApplicationUser";


        submit.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Name = editName.getText().toString();
                                          EmailId = editEmailId.getText().toString();
                                          Phone = editMobNo.getText().toString();
                                          Address = editAddress.getText().toString();
                                          UserName = editusrname.getText().toString();
                                          Password = editPwd.getText().toString();
                                          new RegistrationTask().execute(Name, Address, EmailId, Phone, UserName, Password);


                                      }

                                  }
        );
    }

    private class RegistrationTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            Name = params[0];
            Log.e("Name", Name);
            Address = params[1];
            Log.e("Address", Address);
            EmailId = params[2];
            Log.e("EmailId", EmailId);
            Phone = params[3];
            Log.e("Phone", Phone);
            UserName = params[4];
            Log.e("UserName", UserName);
            Password = params[5];
            Log.e("Password", Password);


        JSONObject jsonObject = new JSONObject();
            try {
            jsonObject.put("Name", Name);
            jsonObject.put("Address", Address);
            jsonObject.put("EmailId", EmailId);
            jsonObject.put("Phone", Phone);
            jsonObject.put("UserName", UserName);
            jsonObject.put("Password", Password);

            jsonPostString = jsonObject.toString();
            response = httpRequestProcessor.pOSTRequestProcessor(jsonPostString, urlRegister);
            jsonResponseString = response.getJsonResponseString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
            return jsonResponseString;
    }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Response String", s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getBoolean("success");
                Log.d("Success", String.valueOf(success));
                message = jsonObject.getString("message");
                Log.d("message", message);

                if (success) {

                    JSONArray responseData = jsonObject.getJSONArray("responseData");
                    for (int i = 0; i < responseData.length(); i++) {
                        JSONObject object = responseData.getJSONObject(i);
                        userID = object.getInt("UserId");
                        Log.d("userId", String.valueOf(userID));
                        Name = object.getString("Name");
                        Log.d("name", Name);
                        Address = object.getString("Address");
                        Log.d("address", Address);
                        EmailId = object.getString("EmailId");
                        Log.d("emailId",EmailId);
                        Phone = object.getString("Phone");
                        Log.d("phone", Phone);
                        UserName = object.getString("UserName");
                        Log.d("userName", UserName);
                        Password = object.getString("Password");
                        Log.d("password", Password);
                    }

                    startActivity(new Intent(RegActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(RegActivity.this, message, Toast.LENGTH_LONG).show();
                }


                /*if (success) {
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                }*/

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}



