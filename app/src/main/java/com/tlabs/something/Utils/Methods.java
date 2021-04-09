package com.tlabs.something.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.google.firebase.BuildConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale;

public final class Methods {

    public static final String SAVE_TAG = "sharedPreferences";


    public static String DateHelper() {
        return (new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a", Locale.getDefault()))
                .format(Calendar.getInstance().getTime());
    }


    //Utility method to store UserData if authentication successful and remember me checked
    public static void saveData(Context context, String Email, String Password, boolean setRemember) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVE_TAG, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", Email);
        editor.putString("password", Password);
        editor.putBoolean("switch", setRemember);
        editor.apply();
    }

    // Utility method to retrieve user shared preference data if was stored last time
    // Note :Remember to edit : RemoveSavedData() also while putting extra code in this method

    public static void retrieveData(Context context, EditText email_in, EditText pwd_in, CheckBox checkBox) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVE_TAG, MODE_PRIVATE);
        email_in.setText(sharedPreferences.getString("email", ""));
        pwd_in.setText(sharedPreferences.getString("password", ""));
        checkBox.setChecked(sharedPreferences.getBoolean("switch", false));
    }

    //Utility method to check if user input email is correct input  format
    public static boolean isValidEmail(String mail) {
        if (mail == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }



    // Note: This is linked to retrieveData ()
    public static void RemoveSavedData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVE_TAG, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.contains("switch")) {
            editor.clear().apply();
        }
    }

    // to check if activity is destroyed
    // glide crash problem on activity recreation
    public static boolean isActivitySafe(Activity activity) {
        return activity == null || !activity.isDestroyed();
    }




    //Utility Method to check Internet connectivity
    public static boolean checkInternetAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return (activeNetwork != null && activeNetwork.isConnected());
    }

    public static void dialContactPhone(final String phoneNumber, Activity activity) {
        activity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }



    public static AlertDialog progressDialog(Context context, String message) {

        int padding = 30;
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(padding, padding, padding, padding);
        linearLayout.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        linearLayout.setLayoutParams(params);

        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setIndeterminate(true);
        progressBar.setPadding(0, 0, padding, 0);
        progressBar.setLayoutParams(params);

        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        TextView tvText = new TextView(context);
        tvText.setText(message);
        tvText.setTextColor(Color.parseColor("#000000"));
        tvText.setLayoutParams(params);

        linearLayout.addView(progressBar);
        linearLayout.addView(tvText);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setView(linearLayout);

        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(layoutParams);
        }
        return dialog;
    }

    public static AlertDialog.Builder builder(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(context));
        builder.setTitle(title).setMessage(message);
        return builder;
    }


    public static void shareApp(Activity activity) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareMessage = "\nHi\nFound an awesome application you might be interested in\n\n";
        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" +
                BuildConfig.APPLICATION_ID + "\n\n";
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareMessage);
        activity.startActivity(Intent.createChooser(sharingIntent, "Choose a Sharing Client"));
    }

    public static void rateApp(Context context, Activity activity) {
        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static void openPolicyPage(Activity activity) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://pratyush019.github.io/cycleappprivacy.html"));
        activity.startActivity(i);
    }

    public static void contactDeveloper(Activity activity) {
        Intent mailIntent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:?subject=" + "Regarding Rento application" +
                "&body=" + "## If your mail is about any error then do send us screenshots/screenvideo of error with your device name and model\n\n" + "&to=" + "care4tlabs@gmail.com");
        mailIntent.setData(data);
        activity.startActivity(Intent.createChooser(mailIntent, "Send mail..."));
    }

    public static boolean hasGrantedPermission(final Context context, final String[] permissionString,
                                               final String permissionRationaleMessage, String launchSettingMessage,
                                               final int requestCode,final String preferenceString) {
        int permissionLength=permissionString.length;
        Boolean[] grantResults=new Boolean[permissionLength];
        Boolean[] shouldShowPermissionRationale=new Boolean[permissionLength];
        boolean hasGranted=false,rationale=false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (hasAsked(context, preferenceString)){

                for (int i=0;i<permissionLength;i++) {
                    grantResults[i] = ActivityCompat.checkSelfPermission(
                            context, permissionString[i]) == PackageManager.PERMISSION_GRANTED;
                    if (!grantResults[i]){
                        hasGranted=false;
                        break;
                    }
                    else {
                        hasGranted=true;
                    }
                }
                for (int i=0;i<permissionLength;i++){
                    shouldShowPermissionRationale[i]=shouldShowRequestPermissionRationale((Activity)context,permissionString[i]);
                    if (shouldShowPermissionRationale[i]){
                        rationale=true;
                        break;
                    }
                }
                if (hasGranted)
                    return true;
                else if(rationale)
                    showPermissionRationaleDialog(context,permissionRationaleMessage,permissionString,requestCode);
                else showLaunchSettingsDialog(context, launchSettingMessage);


            } else {
                requestPermissions((Activity)context,permissionString,requestCode);
                savePermissions(context,preferenceString);
            }


        }

        else hasGranted = true;
        return hasGranted;
    }

  /*  public static boolean hasGrantedLocationPermission(Context context){
        boolean granted=false;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(Methods.hasAsked(context,"location")) {


                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    granted = true;
            }
                else if (shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION) ||
                shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_COARSE_LOCATION)){
                    AlertDialog.Builder builder = builder(context, "Grant Permission..", "We need to access this device location." +
                            "Click continue to grant permission");
                    builder.setCancelable(true);
                    builder.setPositiveButton("CONTINUE", (dialog, which) ->
                            ActivityCompat.requestPermissions((Activity)context,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION},30)).
                            setNegativeButton("NOT NOW", (dialog, which) -> dialog.cancel()).show();
                } else {
                    AlertDialog.Builder builder = builder(context, "Launch Settings?", "We need to access this device location." +
                            "You can give location permission from settings");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Yes", (dialog, which) ->
                            context.startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.parse("package:" + BuildConfig.APPLICATION_ID))))
                            .setNegativeButton("No", (dialog, which) -> dialog.cancel()).show();

                }

            }
            else {
                ActivityCompat.requestPermissions((Activity)context,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION},30);
                savePermissions(context,"location");
            }
        }
        else granted=true;

        return granted;

    } */


    public static void savePermissions(Context context,String permission){
        SharedPreferences sharedPreferences = context.getSharedPreferences("permissions", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(permission, true);
        editor.apply();
    }
    public static boolean hasAsked(Context context,String permission) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("permissions", MODE_PRIVATE);
        return sharedPreferences.getBoolean(permission, false);
    }

    // returns saved file after compressing image, pass only content uri, else may not work

    public static void showPermissionRationaleDialog(Context context,String permissionRationaleMessage,String[] permissionString,int requestCode){
        AlertDialog.Builder builder = builder(context, "Grant Permission..", permissionRationaleMessage);
        builder.setCancelable(true);
        builder.setPositiveButton("CONTINUE", (dialog, which) ->
                ActivityCompat.requestPermissions((Activity) context,
                        permissionString, requestCode)).
                setNegativeButton("NOT NOW", (dialog, which) -> dialog.cancel()).show();
    }
    public static void showLaunchSettingsDialog(Context context,String launchSettingMessage){
        AlertDialog.Builder builder = builder(context, "Launch Settings?", launchSettingMessage);
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", (dialog, which) ->
                context.startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + BuildConfig.APPLICATION_ID))))
                .setNegativeButton("No", (dialog, which) -> dialog.cancel()).show();
    }


}






