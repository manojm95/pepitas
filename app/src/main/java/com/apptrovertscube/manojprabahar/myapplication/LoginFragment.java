package com.apptrovertscube.manojprabahar.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.facebook.login.widget.LoginButton;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public class LoginFragment extends android.support.v4.app.Fragment {

    AutoCompleteTextView loginet;
    EditText loginpwdd;
    Button loginbtn;
    TextView loginforgotpwdtv;
    LoginButton fblogin;
    final List<String> permissions = Arrays.asList("public_profile", "email");
    CallbackManager cm;
    LinearLayout loginparent;
    AlertDialog ok;
    ProgressBar pb;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d("Maanoj", "onCreate " + message);


    }


    public LoginFragment() {

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
            }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        ParseFacebookUtils.initialize(getActivity());
        //After Oncreate View you can instantiate here

        loginparent = (LinearLayout)getView().findViewById(R.id.email_login_form);
        pb = (ProgressBar)getView().findViewById(R.id.loginprogressbar);
        loginet = (AutoCompleteTextView)getView().findViewById(R.id.emailloginet);
        loginpwdd = (EditText) getView().findViewById(R.id.passwordloginet);
        loginbtn = (Button)getView().findViewById(R.id.email_login_button);
        fblogin = (LoginButton) getView().findViewById(R.id.fb_login_button);
        loginforgotpwdtv = (TextView)getView().findViewById(R.id.forgotpwdlogintv);

        cm = CallbackManager.Factory.create();
        fblogin.setFragment(this);
        fblogin.registerCallback(cm, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Facebooook","Facebookk Login Success");
            }

            @Override
            public void onCancel() {
                Log.d("Facebooook","Facebookk Login Cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Facebooook","Facebookk Login Error");

            }
        });



        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"Trying to login with "+loginet.getText() +" & "+loginpwdd.getText(),Toast.LENGTH_LONG).show();

                boolean cancel = false;
                View focusView = null;
                if(loginbtn.getText().equals("Continue"))
                {
                    loginet.setError(null);
                    String email = loginet.getText().toString();
                    if (TextUtils.isEmpty(email)) {
                        loginet.setError("Please Enter a Email Address to Proceed");
                        focusView = loginet;
                        cancel = true;
                    } else if (!isEmailValid(email)) {
                        loginet.setError("This email address is invalid");
                        focusView = loginet;
                        cancel = true;
                    }

                    if (cancel) {
                        // There was an error; don't attempt login and focus the first
                        // form field with an error.
                        focusView.requestFocus();
                    }
                    else
                    {
                        loginbtn.setText("Login");
                        loginpwdd.setVisibility(View.VISIBLE);
                    }

                }
                else
                {
                    loginet.setError(null);
                    loginpwdd.setError(null);
                    String email = loginet.getText().toString();
                    String password = loginpwdd.getText().toString();

                    // Check for a valid password, if the user entered one.
                    if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                        loginpwdd.setError("\"Invalid Password. Should be 6 to 14 char long and no spaces allowed!\"");
                        focusView = loginpwdd;
                        cancel = true;
                    }
                    // Check for a valid email address.
                    if (TextUtils.isEmpty(email)) {
                        loginet.setError("Please Enter a Email Address to Proceed");
                        focusView = loginet;
                        cancel = true;
                    } else if (!isEmailValid(email)) {
                        loginet.setError("This email address is invalid");
                        focusView = loginet;
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
//                        ParseUser user = new ParseUser();
//                        user.setUsername(email);
//                        user.setPassword(password);
//                        user.setEmail(email);

                        ParseUser.logInInBackground(email, password, new LogInCallback() {
                            @Override
                            public void done(final ParseUser parseUser, ParseException e) {


                                if (parseUser != null) {
                                    if(parseUser.getBoolean("emailVerified")) {

                                        Log.d("PARSELOGINN", "EMAIL VERIFIED");
                                        setdefault();
                                        startActivity(new Intent(getActivity(),MainActivity.class));
                                        getActivity().finish();
                                    }
                                    else {
                                        boolean linkedWithFacebook = ParseFacebookUtils.isLinked(parseUser);
                                        if(linkedWithFacebook)
                                        {
                                            Log.d("PARSELOGINN", "EMAIL NOT VERIFIED but facebook");
                                        }
                                        else {
                                            Toast.makeText(getActivity(), "Please Verify your Email before trying to Login", Toast.LENGTH_SHORT).show();
                                            Log.d("PARSELOGINN", "EMAIL NOT VERIFIED");
                                        }
                                        //[PFFacebookUtils isLinkedWithUser:user];


//                                        ParseObject gameScore = new ParseObject("GameScore");
//                                        gameScore.put("score", 1337);
//                                        gameScore.put("playerName", "Sean Plott");
//                                        gameScore.put("cheatMode", false);
//                                        gameScore.saveInBackground(new SaveCallback() {
//                                            @Override
//                                            public void done(ParseException e) {
//                                                if (e != null) {
//                                                    gameScore.saveEventually();
//                                                }
//                                            }
//                                        });
                                        showProgress(false);
                                    }
                                }
                                    else
                                {
                                    showProgress(false);
                                    Log.d("PARSELOGINN","USER NULL");
                                    Toast.makeText(getActivity(),"Please SignUp before trying to Login", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }

            }
        });

        loginforgotpwdtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        fblogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        //ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);

        cm.onActivityResult(requestCode,resultCode,data);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
          }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //INFLATE ROOT LAYOUT HERE
        View v = inflater.inflate(R.layout.login, container, false);
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginparent.setVisibility(show ? View.GONE : View.VISIBLE);
            LoginActivity.y.setVisibility(show ? View.GONE : View.VISIBLE);
            loginparent.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginparent.setVisibility(show ? View.GONE : View.VISIBLE);
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
            loginparent.setVisibility(show ? View.GONE : View.VISIBLE);
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

    void setdefault()
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("DefaultItem");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.d("ImageViewScrollTag", objects.size()+"");
                    if (objects.size() == 0) {
                        LoginActivity.defaultitem = "milkk";
                        LoginActivity.treshold = 200;
                        LoginActivity.credit = 0;
                        ParseObject SelectItems = new ParseObject("DefaultItem");
                        SelectItems.put("id", LoginActivity.a);
                        SelectItems.put("items", "milkkk");
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
                    Log.d("ImageViewScrollTag", e.getMessage());
                }

            }
        });
    }
}
