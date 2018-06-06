package com.apptrovertscube.manojprabahar.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;


public class AddressForm extends AppCompatActivity {

    private EditText name;
    private EditText st1;
    private EditText st2;
    private EditText landmark;
    private EditText state;
    private EditText city;
    private EditText phno;
    private EditText zip;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_form);
        name = (EditText)findViewById(R.id.nameedit);
        st1 = (EditText)findViewById(R.id.Street1edit);
        st2 = (EditText)findViewById(R.id.Street2edit);
        landmark = (EditText)findViewById(R.id.landmarkedit);
        state = (EditText)findViewById(R.id.stateet);
        city = (EditText)findViewById(R.id.cityet);
        phno = (EditText)findViewById(R.id.phoneet);
        zip = (EditText)findViewById(R.id.zipet);
        save = (Button) findViewById(R.id.saveaddress);

        if(LoginActivity.check == true)
        {
            name.setText(LoginActivity.name);
            st1.setText(LoginActivity.st1);
            st2.setText(LoginActivity.st2);
            landmark.setText(LoginActivity.landmark);
            state.setText(LoginActivity.state);
            city.setText(LoginActivity.city);
            phno.setText(LoginActivity.phno);
            zip.setText(LoginActivity.zip);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean cancel = false;
                View focusView = null;
                name.setError(null);
                st1.setError(null);
                st2.setError(null);
                landmark.setError(null);
                state.setError(null);
                city.setError(null);
                phno.setError(null);
                zip.setError(null);
                save.setError(null);
                String names = name.getText().toString();
                String st1s = st1.getText().toString();
                String st2s = st2.getText().toString();
                String lands = landmark.getText().toString();
                String states = state.getText().toString();
                String citys = city.getText().toString();
                String zips = zip.getText().toString();
                String phnos = phno.getText().toString();
                if(TextUtils.isEmpty(st1s))
                {
                    st1.setError("Please Enter a Valid Address");
                    cancel = true;
                    focusView = st1;
                }

                if(TextUtils.isEmpty(lands))
                {
                    landmark.setError("Please Enter a Valid Address");
                    cancel = true;
                    focusView = landmark;
                }

                if(TextUtils.isEmpty(names) && names.length() < 3)
                {
                    name.setError("Please Enter a Valid Name");
                    cancel = true;
                    focusView = name;
                }

                if(TextUtils.isEmpty(states))
                {
                    state.setError("Please leave it as TN");
                    cancel = true;
                    focusView = state;
                }
                if(TextUtils.isEmpty(citys))
                {
                    city.setError("Please leave it as Chennai");
                    cancel = true;
                    focusView = city;
                }
                if(TextUtils.isEmpty(phnos) || !isPhoneValid(phnos))
                {
                    phno.setError("Accepts 10 digit numeric Only");
                    cancel = true;
                    focusView = phno;
                }
                if(TextUtils.isEmpty(zips) || !isZipValid(zips))
                {
                    zip.setError("Accepts 6 digit valid zip");
                    cancel = true;
                    focusView = zip;
                }
                if (cancel) {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                }
                else
                {
                    if(LoginActivity.check == true)
                    {
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Address");
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if(e==null) {
                                    if(objects.size()>0) {
                                        objects.get(0).put("name",names);
                                        objects.get(0).put("street1",st1s);
                                        objects.get(0).put("street2",st2s);
                                        objects.get(0).put("landmark",lands);
                                        objects.get(0).put("state",states);
                                        objects.get(0).put("city",citys);
                                        objects.get(0).put("phone",phnos);
                                        objects.get(0).put("zip",zips);
                                        objects.get(0).saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if(e!=null)
                                                {
                                                    objects.get(0).saveEventually();
                                                    startActivity(new Intent(AddressForm.this, MainActivity.class));
                                                    finish();
                                                }
                                                else
                                                {
                                                    LoginActivity.check = true;
                                                    LoginActivity.name = names;
                                                    LoginActivity.st1 = st1s;
                                                    LoginActivity.st2 = st2s;
                                                    LoginActivity.landmark = lands;
                                                    LoginActivity.state = states;
                                                    LoginActivity.city = citys;
                                                    LoginActivity.phno = phnos;
                                                    LoginActivity.zip = zips;
                                                    startActivity(new Intent(AddressForm.this, MainActivity.class));
                                                    finish();
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }
                    else {
                        //Toast.makeText(getApplicationContext(),"The Input is Good", Toast.LENGTH_SHORT).show();
                        ParseObject SelectItems = new ParseObject("Address");
                        SelectItems.put("name", names);
                        SelectItems.put("street1", st1s);
                        SelectItems.put("street2", st2s);
                        SelectItems.put("landmark", lands);
                        SelectItems.put("state", states);
                        SelectItems.put("city", citys);
                        SelectItems.put("zip", zips);
                        SelectItems.put("phone", phnos);
                        SelectItems.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e != null) {
                                    Log.d("nbnbnbnb",e.getMessage());
                                    SelectItems.saveEventually();
                                    startActivity(new Intent(AddressForm.this, MainActivity.class));
                                    finish();
                                } else {
                                    LoginActivity.check = true;
                                    LoginActivity.check = true;
                                    LoginActivity.name = names;
                                    LoginActivity.st1 = st1s;
                                    LoginActivity.st2 = st2s;
                                    LoginActivity.landmark = lands;
                                    LoginActivity.state = states;
                                    LoginActivity.city = citys;
                                    LoginActivity.phno = phnos;
                                    LoginActivity.zip = zips;
                                    startActivity(new Intent(AddressForm.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });
                    }

                }
            }
        });
    }

    private boolean isEmailValid(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private boolean isPasswordValid(String password) {
        String pattern = "(?=\\S+$).{6,14}.^[0-9]*$";
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

    private boolean isPhoneValid(String password) {
        String pattern = "\\d{10}";
        return password.matches(pattern);
    }

    private boolean isZipValid(String password) {
        String pattern = "\\d{6}";
        return password.matches(pattern);
    }
}
