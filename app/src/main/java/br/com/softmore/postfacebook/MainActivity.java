package br.com.softmore.postfacebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.FacebookSdkNotInitializedException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.loginFacebook);
        loginButton.setReadPermissions("email");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.i(TAG, "Sucesso");
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("Main", response.toString());
                                configurarProfile(object);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

    }
    private void configurarProfile(JSONObject jsonObject) {
        try {
            TextView name = (TextView) findViewById(R.id.tvNameId);
            TextView email = (TextView) findViewById(R.id.tvEmailId);
            TextView sexo = (TextView) findViewById(R.id.tvGenderId);
            ProfilePictureView profilePictureView = (ProfilePictureView) findViewById(R.id.image);

            name.setText(jsonObject.getString("name"));
            email.setText(jsonObject.getString("email"));
            sexo.setText(jsonObject.getString("gender"));

            profilePictureView.setPresetSize(ProfilePictureView.NORMAL);
            profilePictureView.setProfileId(jsonObject.getString("id"));


        } catch (Exception e) {
            Log.e("android", e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
