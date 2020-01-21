package com.example.dreamtunes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import validators.InputValidator;


public class SignInActivity extends AppCompatActivity {


    private ImageView startTitle;
    private Button signIn;
    private Button signUpLink;

    private LoginButton facebook;
    private Button facebookView;
    private CallbackManager callbackManager;
    private Map<Inputs, EditText> inputs = new EnumMap<>(Inputs.class);
    private Map<Inputs, TextView> validators = new EnumMap<>(Inputs.class);
    private Map<Inputs, Boolean> validatorLock = new EnumMap<>(Inputs.class);

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @SuppressLint("ClickableViewAccessibility")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        startTitle = findViewById(R.id.title_start_second);
        signIn = findViewById(R.id.button_sign_in);
        signUpLink = findViewById(R.id.sign_up_link);

        inputs.put(Inputs.EMAIL, (EditText) findViewById(R.id.email_sign_in_input));
        inputs.put(Inputs.PASSWORD, (EditText) findViewById(R.id.password_sign_in_input));

        validators.put(Inputs.EMAIL, (TextView) findViewById(R.id.validator_email_sign_in));
        validators.put(Inputs.PASSWORD, (TextView) findViewById(R.id.validator_password_sign_in));

        validatorLock.put(Inputs.EMAIL, false);
        validatorLock.put(Inputs.PASSWORD, false);

        facebook = findViewById(R.id.sign_up_with_fb_btn);
        facebookView = findViewById(R.id.sign_up_with_fb_view);

        startTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    navigateToMainActivity();
                }
                return true;
            }
        });

        inputs.get(Inputs.EMAIL).addTextChangedListener(new InputValidator((EditText) findViewById(R.id.email_sign_in_input)) {
            @Override
            public void validate(EditText textInput, String text) {
                if (text.length() == 0) {
                    validators.get(Inputs.EMAIL).setText(R.string.email_required);
                    validatorLock.put(Inputs.EMAIL, false);
                } else {
                    if (!text.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                        validators.get(Inputs.EMAIL).setText(R.string.wrong_format);
                        validatorLock.put(Inputs.EMAIL, false);
                    } else {
                        validators.get(Inputs.EMAIL).setText(null);
                        validatorLock.put(Inputs.EMAIL, true);

                    }
                }


            }
        });

        inputs.get(Inputs.PASSWORD).addTextChangedListener(new InputValidator((EditText) findViewById(R.id.password_sign_in_input)) {
            @Override
            public void validate(EditText textInput, String text) {
                if (text.length() == 0) {
                    validators.get(Inputs.PASSWORD).setText(R.string.password_required);
                    validatorLock.put(Inputs.PASSWORD, false);
                } else {
                    validators.get(Inputs.PASSWORD).setText(null);
                    validatorLock.put(Inputs.PASSWORD, true);
                }

            }
        });

        signIn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP: {
                        v.setBackgroundResource(R.drawable.ic_sign_in_button);
                        break;
                    }
                    case MotionEvent.ACTION_DOWN: {
                        v.setBackgroundResource(R.drawable.ic_sign_in_button_clicked);

                        if (isValid()) {
                            db.collection("users")
                                    .whereEqualTo("email", inputs.get(Inputs.EMAIL).getText().toString())
//                                    .whereEqualTo("password", inputs.get(Inputs.PASSWORD).getText().toString())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {

                                                if (task.getResult().getDocuments().isEmpty()) {
                                                    validators.get(Inputs.EMAIL).setText(R.string.wrong_email);
                                                    validatorLock.put(Inputs.EMAIL, false);
                                                } else {
                                                    Map<String, Object> results = task
                                                            .getResult()
                                                            .getDocuments()
                                                            .get(0)
                                                            .getData();

                                                    Log.i("password", results.get("password").toString());

                                                    if (results.get("password").toString().equals(inputs.get(Inputs.PASSWORD).getText().toString())) {

                                                        db.collection("logged")
                                                                .document("logged_user")
                                                                .delete();

                                                        db.collection("logged")
                                                                .document("logged_user")
                                                                .set(results);


                                                        // todo redirect to music activity

                                                    } else {
                                                        validators.get(Inputs.PASSWORD).setText(R.string.wrong_password);
                                                        validatorLock.put(Inputs.PASSWORD, false);
                                                    }
                                                }
                                            }

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("ERROR", "sth went wrong" + e.getMessage());
                                        }
                                    });
                        }
                        break;
                    }
                    default:
                        break;
                }
                return true;
            }
        });


        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSignUpActivity();
            }
        });

        callbackManager = CallbackManager.Factory.create();
        facebook.setPermissions(Arrays.asList("email"));


        facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.i("LOG_RES_BUTTON_CLICKED", "zalogowano");
                Log.i("ACCES_TOKEN TIME", loginResult.getAccessToken().getDataAccessExpirationTime().toString());
                LoadUserProfile(loginResult.getAccessToken());


            }

            @Override
            public void onCancel() {
                Log.i("CANCELED_BUTTON_CLICKED", "fb cancel");

            }

            @Override
            public void onError(FacebookException error) {
                Log.i("FB_ERROR_BUTTON_CLICKED", error.toString());

            }
        });


        facebookView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.setBackgroundResource(R.drawable.ic_facebook_ico_clicked);
                        facebook.performClick();
                        // TODO log out
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.setBackgroundResource(R.drawable.ic_facebook_ico);
                        openLoginManager();
                        break;
                    }
                    default:
                        break;
                }
                return true;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void LoadUserProfile(final AccessToken accessToken) {
        GraphRequest req = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.i("response", response.toString());
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
//                    String email = object.getString("email");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com" + id + "/picture?type=normal";


                    Log.i("FIRST_NAME", first_name);
                    Log.i("SURNAME", last_name);
//                    Log.i("EMAIL", email);
                    Log.i("ID", id);


                    // todo save data in db

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        req.setParameters(parameters);
        req.executeAsync();

    }

    public void openLoginManager() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    public void navigateToMainActivity() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    public void navigateToSignUpActivity() {
        Intent signUpActivity = new Intent(this, SignUpActivity.class);
        startActivity(signUpActivity);
        finish();
    }

    public boolean isValid() {
        for (Map.Entry<Inputs, Boolean> entry : validatorLock.entrySet()) {
            if (!entry.getValue()) return false;
        }
        return true;
    }
}
