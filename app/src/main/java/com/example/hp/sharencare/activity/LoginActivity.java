package com.example.hp.sharencare.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.sharencare.R;
import com.example.hp.sharencare.apiconfiguration.ApiConfiguration;
import com.example.hp.sharencare.httpRequestProcessor.HttpRequestProcessor;
import com.example.hp.sharencare.httpRequestProcessor.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText editName, editPassword;
    private String User, pass;
    private Button signup, login;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlLogin, jsonStringToPost, jsonResponseString;
    private boolean success;
    private String Name, EmailId, Phone, Address, UserName, Password,message;
    private int UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //finding views by id
        signup = (Button) findViewById(R.id.signup);
        login = (Button) findViewById(R.id.login);
        editName = (EditText) findViewById(R.id.editName);
        editPassword = (EditText) findViewById(R.id.editPassword);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting base url
        baseURL = apiConfiguration.getApi();
        urlLogin = baseURL + "AccountAPI/GetLoginUser";

        //On clicking login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Getting usrname and password
                User = editName.getText().toString();
                pass = editPassword.getText().toString();

                new LoginTask().execute(User, pass);

            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegActivity.class);
                startActivity(intent);
            }
        });
    }
    public class LoginTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            User = params[0];
            pass = params[1];

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("UserName", User);
                jsonObject.put("Password", pass);

                jsonStringToPost = jsonObject.toString();
                response = httpRequestProcessor.pOSTRequestProcessor(jsonStringToPost, urlLogin);
                jsonResponseString = response.getJsonResponseString();


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.d("Response String", s);

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
                        UserID = object.getInt("UserId");
                        Log.d("userId", String.valueOf(UserID));
                        Name = object.getString("Name");
                        Log.d("name",Name);
                        Address = object.getString("Address");
                        Log.d("address", Address);
                        EmailId = object.getString("EmailId");
                        Log.d("emailId", EmailId);
                        Phone = object.getString("Phone");
                        Log.d("phone", Phone);
                        UserName = object.getString("UserName");
                        Log.d("userName", UserName);
                        Password = object.getString("Password");
                        Log.d("password", Password);
                    }
                    Intent intent = new Intent(LoginActivity.this, ChooseActivity.class);
                    intent.putExtra("name", User);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}