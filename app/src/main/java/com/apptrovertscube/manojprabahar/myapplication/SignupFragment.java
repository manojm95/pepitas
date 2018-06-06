package com.apptrovertscube.manojprabahar.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import android.support.v7.app.AlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public class SignupFragment extends android.support.v4.app.Fragment {

    AutoCompleteTextView signupet;
    EditText signuppwdd;
    Button signupbtn;
    LinearLayout sigupparent;
    AlertDialog ok;
    ProgressBar pb;
    TextView fp;
    final List<String> permissions = Arrays.asList("public_profile", "email");

    //TextView loginforgotpwdtv;

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Log.d("Maanoj", "onDestroy " + message);
        if(ok!=null) {
            if (ok.isShowing()) ok.dismiss();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if(ok!=null) {
            if (ok.isShowing()) ok.dismiss();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d("Maanoj", "onCreate " + message);
    }


    public SignupFragment() {

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
            }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //After Oncreate View you can instantiate here

        sigupparent = (LinearLayout)getView().findViewById(R.id.email_signup_form);
        pb = (ProgressBar)getView().findViewById(R.id.signupprogressbar);
        signupet = (AutoCompleteTextView)getView().findViewById(R.id.email);
        signuppwdd = (EditText) getView().findViewById(R.id.password);
        signupbtn = (Button)getView().findViewById(R.id.email_signup_button);
        fp = (TextView)getView().findViewById(R.id.forgotpwdsignup);
        //loginforgotpwdtv = (TextView)getView().findViewById(R.id.forgotpwdlogintv);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"Trying to login with "+signupet.getText() +" & "+signuppwdd.getText(),Toast.LENGTH_LONG).show();
                boolean cancel = false;
                View focusView = null;
                if(signupbtn.getText().equals("Continue"))
                {
                    signupet.setError(null);
                    String email = signupet.getText().toString();
                    if (TextUtils.isEmpty(email)) {
                        signupet.setError("Please Enter a Email Address to Proceed");
                        focusView = signupet;
                        cancel = true;
                    } else if (!isEmailValid(email)) {
                        signupet.setError("This email address is invalid");
                        focusView = signupet;
                        cancel = true;
                    }

                    if (cancel) {
                        // There was an error; don't attempt login and focus the first
                        // form field with an error.
                        focusView.requestFocus();
                    }
                    else
                    {
                        signupbtn.setText("SignUp");
                        signuppwdd.setVisibility(View.VISIBLE);
                    }

                }
                else
                {
                    signupet.setError(null);
                    signuppwdd.setError(null);
                    String email = signupet.getText().toString();
                    String password = signuppwdd.getText().toString();

                    // Check for a valid password, if the user entered one.
                    if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                        signuppwdd.setError("\"Invalid Password. Should be 6 to 14 char long and no spaces allowed!\"");
                        focusView = signuppwdd;
                        cancel = true;
                    }
                    // Check for a valid email address.
                    if (TextUtils.isEmpty(email)) {
                        signupet.setError("Please Enter a Email Address to Proceed");
                        focusView = signupet;
                        cancel = true;
                    } else if (!isEmailValid(email)) {
                        signupet.setError("This email address is invalid");
                        focusView = signupet;
                        cancel = true;
                    }

                    if (cancel) {
                        // There was an error; don't attempt login and focus the first
                        // form field with an error.
                        focusView.requestFocus();
                    }
                    else
                    {
                        showProgress(true);
                        ParseUser user = new ParseUser();
                        user.setUsername(email);
                        user.setPassword(password);
                        user.setEmail(email);

                        user.signUpInBackground(new SignUpCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    showProgress(false);
                                    ParseUser.getCurrentUser();
                                    setdefault();
                                    final String title = "Account Created Successfully!";
                                    final String message = "Please verify your email before proceeding";
                                    alertDisplayer(title, message);
                                } else {
                                    showProgress(false);
                                    final String title = "Error Account Creation failed";
                                    final String message = "Account could not be created";
                                    alertDisplayer(title, message + " :" + e.getMessage());
                                }
                            }
                        });
                    }
                }
            }
        });

        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(true);
                Log.d("MyApp", "Start");
                ParseFacebookUtils.logInWithReadPermissionsInBackground(getActivity(), permissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            showProgress(false);
                            Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                        } else if (user.isNew()) {
                            Log.d("MyApp", "User signed up and logged in through Facebook!");
                            getUserDetailFromFB();
                            showProgress(false);
                            setdefault();
                            startActivity(new Intent(getActivity(),MainActivity.class));

                        } else {
                            Log.d("MyApp", "User logged in through Facebook!");
                            getUserDetailFromParse();
                            showProgress(false);
                            setdefault();
                            startActivity(new Intent(getActivity(),MainActivity.class));
                            getActivity().finish();
                        }
                    }
                });

                Log.d("MyApp", "Final");

            }


        });

//        loginforgotpwdtv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(),"Forgot Password",Toast.LENGTH_LONG).show();
//            }
//        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
          }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //INFLATE ROOT LAYOUT HERE
        View v = inflater.inflate(R.layout.signup, container, false);
        return v;
//    }
    }

    //ONLY IF IN ONRESUME  ONLOADFINOIISHED will be called once
    //VERY IMPORTANT

    @Override
    public void onResume() {
        super.onResume();
        //Log.d("MannonResume", "onResume " + message);
    }

    private boolean isEmailValid(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private boolean isPasswordValid(String password) {
        String pattern = "(?=\\S+$).{6,14}";
        return password.matches(pattern);
        /*
                (?=.*[0-9]) a digit must occur at least once
                (?=.*[a-z]) a lower case letter must occur at least once
                (?=.*[A-Z]) an upper case letter must occur at least once
                (?=.*[@#$%^&+=]) a special character must occur at least once
                (?=\\S+$) no whitespace allowed in the entire string
                .{8,} at least 8 characters
                */
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            sigupparent.setVisibility(show ? View.GONE : View.VISIBLE);
            LoginActivity.y.setVisibility(show ? View.GONE : View.VISIBLE);
            sigupparent.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    sigupparent.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            LoginActivity.y.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    LoginActivity.y.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            pb.setVisibility(show ? View.VISIBLE : View.GONE);
            pb.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    pb.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            pb.setVisibility(show ? View.VISIBLE : View.GONE);
            sigupparent.setVisibility(show ? View.GONE : View.VISIBLE);
            LoginActivity.y.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    void alertDisplayer(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        ok = builder.create();
        ok.show();

    }

    public void getUserDetailFromFB(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),new GraphRequest.GraphJSONObjectCallback(){
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try{
                    //t_username.setText(object.getString("name"));
                    Log.d("Faceboook User Name", object.getString("name"));
                }catch(JSONException e){
                    e.printStackTrace();
                }
                try{
                    //t_email.setText(object.getString("email"));
                    Log.d("Faceboook User Name", object.getString("email"));
                }catch(JSONException e){
                    e.printStackTrace();
                }
                try {
                    saveNewUser(object.getString("name"),object.getString("email"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields","name,email");
        request.setParameters(parameters);
        request.executeAsync();
        getActivity().finish();
    }

    void getUserDetailFromParse(){
        ParseUser user = ParseUser.getCurrentUser();
//        t_username.setText(user.getUsername());
//        t_email.setText(user.getEmail());
//        alertDisplayer("Welcome Back","User:"+t_username.getText().toString()+" Login.Email:"+t_email.getText().toString());

        Toast.makeText(getActivity(),"The details are "+user.getUsername() +" & "+user.getEmail(), Toast.LENGTH_SHORT).show();

    }

    void saveNewUser(String un, String email){
        ParseUser user = ParseUser.getCurrentUser();
        user.setUsername(un);
        user.setEmail(email);
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Toast.makeText(getActivity(),"The details are "+un +" & "+email, Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setdefault()
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("DefaultItem");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() == 0) {
                        LoginActivity.defaultitem = "milk";
                        LoginActivity.treshold = 200;
                        LoginActivity.credit = 0;
                        ParseObject SelectItems = new ParseObject("DefaultItem");
                        SelectItems.put("id", LoginActivity.a);
                        SelectItems.put("items", "milk");
                        SelectItems.put("treshold", 200.0);
                        SelectItems.put("credit", 0);
                        SelectItems.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e!=null)
                                {
                                    SelectItems.saveInBackground();
                                }
                            }
                        });
                    }
                    else if(objects.size() > 0 )
                    {
                        LoginActivity.defaultitem = objects.get(0).getString("items");
                        LoginActivity.treshold = objects.get(0).getDouble("treshold");
                        LoginActivity.credit = objects.get(0).getDouble("credit");
                    }
                } else {
                    Log.d("ImageViewScrollTag", "Check error in retrieving trashorarchive");
                }

            }
        });

        ParseQuery<ParseObject> accum = ParseQuery.getQuery("DefaultItem");
        accum.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() == 0) {
                        LoginActivity.defaultitem = "milk";
                        LoginActivity.treshold = 200;
                        LoginActivity.credit = 0;
                        LoginActivity.defaultitem = "milk";
                        ParseObject SelectItems = new ParseObject("DefaultItem");
                        SelectItems.put("id", LoginActivity.a);
                        SelectItems.put("items", "milk");
                        SelectItems.put("treshold", 200);
                        SelectItems.put("credit", 0);
                        SelectItems.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e!=null)
                                {
                                    SelectItems.saveInBackground();
                                }
                            }
                        });
                    }
                    else if(objects.size() > 0 )
                    {
                        LoginActivity.defaultitem = objects.get(0).getString("items");
                        LoginActivity.treshold = objects.get(0).getDouble("treshold");
                        LoginActivity.credit = objects.get(0).getDouble("credit");
                    }
                } else {
                    Log.d("ImageViewScrollTag", "Check error in retrieving trashorarchive");
                }

            }
        });
    }

}
