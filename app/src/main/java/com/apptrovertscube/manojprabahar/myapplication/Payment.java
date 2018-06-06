package com.apptrovertscube.manojprabahar.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Payment extends AppCompatActivity implements PaymentResultListener {

    double temp = 0;
    String total = "";
    private boolean addcredit = false;
    private double amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_payment);
        total = String.format("%.02f", (LoginActivity.treshold - LoginActivity.credit));
        Log.d("jhjhjghhh", total + "");
        TextView paymentamount = (TextView) findViewById(R.id.paymentactivityamount);
        paymentamount.setText(getResources().getString(R.string.Rs) + ". " + total);
        TextView paynow = (TextView) findViewById(R.id.paymentactivitypaynow);
        paynow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                if (LoginActivity.credit != 0) {
                    temp = Double.parseDouble(total);
                    temp = Math.round(temp * 100);
                    Checkout checkout = new Checkout();
                    checkout.setImage(R.drawable.cloudsanelogohome);
                    final Activity activity = Payment.this;
                    JSONObject options = new JSONObject();
                    try {
                        options.put("name", "Pepitas");
                        options.put("description", "Order #123456");
                        options.put("currency", "INR");
                        JSONObject theme = new JSONObject();
                        theme.put("color", "#000000");
                        options.put("theme", theme);
                        Log.d("jhjhjghhh", temp + "");
                        options.put("amount", String.valueOf(temp));
                        checkout.open(activity, options);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "There is nothing to pay. Thank you for the support!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("kkkkhhhh", "" + LoginActivity.credit);
                AlertDialog.Builder alert = new AlertDialog.Builder(Payment.this);

                alert.setTitle("Buy Credit");
                alert.setMessage("How much credit you wanna buy?");

// Set an EditText view to get user input
                final EditText input = new EditText(Payment.this);
                input.setBackgroundColor(Color.parseColor("#F2F3F4"));
                input.setCursorVisible(false);
                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                buttonLayoutParams.setMargins(10, 0, 100, 0);
                input.setLayoutParams(buttonLayoutParams);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(input);

                alert.setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        // Do something with value!
                        amount = Double.parseDouble(input.getText().toString());
                        if (amount > 0) {
                            addcredit = true;
                            //amount = amount * 100;
                            Checkout checkout = new Checkout();
                            checkout.setImage(R.drawable.cloudsanelogohome);
                            final Activity activity = Payment.this;
                            JSONObject options = new JSONObject();
                            try {
                                options.put("name", "Pepitas");
                                options.put("description", "Order #123456");
                                options.put("currency", "INR");
                                JSONObject theme = new JSONObject();
                                theme.put("color", "#000000");
                                options.put("theme", theme);
                                Log.d("jhjhjghhh", amount + "");
                                options.put("amount", String.valueOf(amount*100));
                                checkout.open(activity, options);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "No amount/zero entered", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                alert.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
                dialog.setCanceledOnTouchOutside(true);
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);

            }
        });
        TextView goback = (TextView) findViewById(R.id.paymentactivitygoback);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Payment.this, MainActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onPaymentSuccess(String s) {
        //Log.d("DeResponse", s+"    "+temp);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("DefaultItem");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        if (addcredit) {
                            double a = Double.parseDouble(objects.get(0).get("treshold").toString());
                            a = a + amount;
                            objects.get(0).put("treshold", a);
                            LoginActivity.treshold = a;
                        } else {
                            objects.get(0).put("credit", 0);
                            LoginActivity.credit = 0;
                        }
                        objects.get(0).saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    LoginActivity.credit = 0;
                                    Toast.makeText(getApplicationContext(), "Payment Successful. Received sum of amount " + total, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Payment.this, MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Payment Successful. Received sum of amount " + total + " but internal error happened. Please contact customer care.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Payment.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Payment Successful. Received sum of amount " + total + " but internal error happened. Please contact customer care.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Payment.this, MainActivity.class));
                    finish();
                }

            }

        });
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(), "Payment Not Successful. Please check your payment method and internet before proceeding", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Payment.this, MainActivity.class));
        finish();
    }

}
