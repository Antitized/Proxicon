package com.babaheli.proxicon;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignActivity extends Activity {

    private Button signupView;
    private Button loginView;
    private EditText usernameView;
    private EditText passwordView;
    private String username;
    private String password;
    private Intent intent;
    private Intent serviceIntent;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        TODO:
            Convert to XML
            Import MySQL login
            Notifications
            Use own widget library
         */
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;

        try {
            Parse.initialize(this, "eX9xfPfdJ5Tyg0d8zBrQCjKPpwrXQ3i2K7IplbWz", "j0pqXtzT67MaEvqhN2d7EKOhXcJnTWuGuAErdR4N");
        }
        catch (Exception e)
        {
            Log.d("Not an error","Error");
        }
        intent = new Intent(getApplicationContext(), ListUsersActivity.class);
        serviceIntent = new Intent(getApplicationContext(), MessageService.class);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            startActivity(intent);
            startService(serviceIntent);
        }

        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.parseColor("#FAAC58"));
        layout.setGravity(Gravity.CENTER);

        ImageView logo = new ImageView(this);
        int size = (int) Math.round(width / 2.5);
        logo.setLayoutParams(new LinearLayout.LayoutParams(size,size));
        logo.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));

        TextView title = new TextView(this);
        Spannable titleText = new SpannableString("PROXICON");
        titleText.setSpan(new StyleSpan(Typeface.BOLD), titleText.length() - 3, titleText.length(), 0);

        title.setText(titleText);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(36);

        LinearLayout layout2 = new LinearLayout(this);
        LinearLayout.LayoutParams layout2params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout2params.setMargins(50, 50, 50, 50);
        layout2.setLayoutParams(layout2params);
        layout2.setOrientation(LinearLayout.VERTICAL);
        layout2.setBackgroundColor(Color.parseColor("#E28D31"));
        layout2.setGravity(Gravity.CENTER);


        usernameView = new EditText(this);
        usernameView.setGravity(Gravity.CENTER);
        usernameView.setHighlightColor(Color.parseColor("#5D4B3D"));
        usernameView.setBackground(null);
        usernameView.setHint("USERNAME");
        usernameView.setFilters(new InputFilter[]{
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                        if (src.equals("")) {
                            return src;
                        }
                        if (src.toString().matches("[a-zA-Z0-9]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        });
        usernameView.setTextSize(22);
        usernameView.setHintTextColor(Color.WHITE);
        usernameView.setTextColor(Color.parseColor("#BFBFBF"));

        View line = new View(this);
        LinearLayout.LayoutParams lineparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,5);
        lineparams.setMargins(50, 0, 50, 0);
        line.setLayoutParams(lineparams);
        line.setBackgroundColor(Color.parseColor("#D17D37"));

        passwordView = new EditText(this);
        passwordView.setGravity(Gravity.CENTER);
        passwordView.setHighlightColor(Color.parseColor("#5D4B3D"));
        passwordView.setBackground(null);
        passwordView.setHint("PASSWORD");
        passwordView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordView.setFilters(new InputFilter[]{
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                        if (src.equals("")) {
                            return src;
                        }
                        if (src.toString().matches("[a-zA-Z0-9]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        });
        passwordView.setTextSize(22);
        passwordView.setHintTextColor(Color.WHITE);
        passwordView.setTextColor(Color.parseColor("#BFBFBF"));

        loginView = new Button(this);
        loginView.setText("LOGIN");
        LinearLayout.LayoutParams loginViewparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loginViewparams.setMargins(50,0,50,20);
        loginView.setLayoutParams(loginViewparams);
        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius(30);
        shape.setColor(Color.parseColor("#BB545454"));
        loginView.setBackground(shape);
        loginView.setTextSize(20);
        loginView.setTextColor(Color.WHITE);
        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameView.getText().toString();
                password = passwordView.getText().toString();

                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    public void done(ParseUser user, com.parse.ParseException e) {
                        if (user != null) {
                            startActivity(intent);
                            startService(serviceIntent);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "This account is not registered!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        signupView = new Button(this);
        signupView.setText("SIGN UP");
        signupView.setLayoutParams(loginViewparams);
        signupView.setBackground(shape);
        signupView.setTextSize(20);
        signupView.setTextColor(Color.WHITE);
        signupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameView.getText().toString();
                password = passwordView.getText().toString();
                ParseUser user = new ParseUser();
                user.setUsername(username);
                user.setPassword(password);
                user.signUpInBackground(new SignUpCallback() {
                    public void done(com.parse.ParseException e) {
                        if (e == null) {
                            startActivity(intent);
                            startService(serviceIntent);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "There was an error signing up."
                                    , Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        layout.addView(logo);
        layout.addView(title);
        layout2.addView(usernameView);
        layout2.addView(line);
        layout2.addView(passwordView);
        layout.addView(layout2);
        layout.addView(loginView);
        layout.addView(signupView);
        setContentView(layout);
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, MessageService.class));
        super.onDestroy();
    }
}
