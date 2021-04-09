package com.tlabs.something.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tlabs.something.Activities.HomeActivity;
import com.tlabs.something.Activities.MailVerification;
import com.tlabs.something.Interfaces.FragmentSwitcher;
import com.tlabs.something.R;
import com.tlabs.something.Utils.Methods;
import com.tlabs.something.Utils.UserDetails;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static com.tlabs.something.Utils.Methods.DateHelper;
import static com.tlabs.something.Utils.Methods.RemoveSavedData;
import static com.tlabs.something.Utils.Methods.SAVE_TAG;
import static com.tlabs.something.Utils.Methods.checkInternetAvailable;
import static com.tlabs.something.Utils.Methods.isValidEmail;
import static com.tlabs.something.Utils.Methods.retrieveData;
import static com.tlabs.something.Utils.Methods.saveData;

public class LoginFragment extends Fragment {

    private FirebaseAuth mFirebaseAuth;
    private EditText email_in;
    private EditText pwd_in;
    private boolean setRemember;
    private CheckBox check;
    private FragmentSwitcher fc;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View frag_view = inflater.inflate(R.layout.fragment_login, container, false);

        //attach objects to all instance variables
        mFirebaseAuth = FirebaseAuth.getInstance();
        email_in = frag_view.findViewById(R.id.email_in);
        pwd_in = frag_view.findViewById(R.id.pwd_in);
        check = frag_view.findViewById(R.id.remember_me_box);
        fc = (FragmentSwitcher) getActivity();

        //get views which require on click listeners and attach them
        ImageView sign_in = frag_view.findViewById(R.id.sign_in);
        TextView forgot = frag_view.findViewById(R.id.forgot);
        ImageButton toggle_password = frag_view.findViewById(R.id.password_toggle);
        TextView moveToSignUp = frag_view.findViewById(R.id.text_sign_up);

        //set progress bar invisible at fragment start



        // for existing unsigned users - verify modifier final for the view
        sign_in.setOnClickListener(v -> {
            fc.lockViewPager(true);
            Sign_in_Helper();
        });

        //call method to reset password
        forgot.setOnClickListener(v -> forgot_password_Dialog());

        //For password toggle on main Screen
        toggle_password.setOnClickListener(new View.OnClickListener() {
            int count = 0;

            @Override
            public void onClick(View v) {
                if (count % 2 == 0) {
                    v.setBackgroundResource(R.drawable.ic_baseline_visibility_24);
                    pwd_in.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    v.setBackgroundResource(R.drawable.ic_baseline_visibility_off_24);
                    pwd_in.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                count++;
            }
        });

        //For Moving to Sign up screen when user click New User ? : Sign Up
        moveToSignUp.setOnClickListener(v -> fc.changeToFragment(1));

        check.setOnClickListener(v -> {
            setRemember = ((CheckBox) v).isChecked();
            if (!(((CheckBox) v).isChecked())) {
                RemoveSavedData(requireActivity());
            }

        });

        return frag_view;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser mFireBaseUser =FirebaseAuth.getInstance().getCurrentUser();


        // checking if user is already signed in and verified or not
        // if yes then directly start home activity else get shared preferences and load them in fields
        if (mFireBaseUser != null) {
            mFireBaseUser.reload();
            // getting time instance and updating this in database
            UserDetails.setLastLogin(DateHelper());
            if (!UserDetails.isEmailVerified()) {
                Intent a = new Intent(getContext(), MailVerification.class);
                //  a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                requireActivity().finish();
            } else {
                Intent a = new Intent(getContext(), HomeActivity.class);
                //  a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                requireActivity().finish();
            }
        } else {
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SAVE_TAG, MODE_PRIVATE);
            // if its checked load and show data
            if (sharedPreferences.getBoolean("switch", false)) {
                retrieveData(requireActivity(), email_in, pwd_in, check);
            }
        }
    }


    //Generate Dialog for password reset and authenticate with database
    private void forgot_password_Dialog() {
        View promptsView = LayoutInflater.from(getContext()).inflate(R.layout.forgot, null);
        final EditText userInput = promptsView.findViewById(R.id.editTextDialogUserInput);

        AlertDialog alertDialog = new AlertDialog.Builder(requireContext())
                .setView(promptsView)
                .setCancelable(false)
                .setPositiveButton("CONTINUE", (dialog, id) -> {
                    String mail = userInput.getText().toString();
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setCancelable(false);
                    if (Methods.checkInternetAvailable(requireContext())){
                        if (isValidEmail(mail)) {

                            FirebaseAuth.getInstance().sendPasswordResetEmail(mail)
                                    .addOnSuccessListener(aVoid -> {
                                        builder.setMessage("Reset mail Sent, also check your spam folder");
                                        builder.setPositiveButton("CLOSE", (dialogInterface, i) -> dialogInterface.dismiss()).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        builder.setMessage(e.getMessage());
                                        builder.setPositiveButton("CLOSE", (dialogInterface, i) -> dialogInterface.dismiss()).show();
                                    });
                        } else {
                            builder.setMessage("Not a valid email id!!");
                            builder.setPositiveButton("CLOSE", (dialogInterface, i) -> dialogInterface.dismiss()).show();


                        }
                    }
                    else {
                        builder.setMessage("Network un-available!");
                        builder.setPositiveButton("CLOSE", (dialogInterface, i) -> dialogInterface.dismiss()).show();
                    }
                    dialog.dismiss();


                })
                .setNegativeButton("Cancel",
                        (dialog, id) -> dialog.cancel())
                .create();

        alertDialog.show();
    }

    //Check user email,password entered  either show error dialogs or sign in
    private void Sign_in_Helper() {
        AlertDialog progressDialog= Methods.progressDialog(getContext(),"Authenticating...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);


        final String Id, Pwd;
        Id = email_in.getText().toString();
        Pwd = pwd_in.getText().toString();

        // Validate
        if (Id.isEmpty()) {
            email_in.setError("Email Required");
            email_in.requestFocus();
            fc.lockViewPager(false);
        } else if (Pwd.isEmpty()) {
            pwd_in.setError("Enter Password!");
            email_in.requestFocus();
            fc.lockViewPager(false);

        } else {
            progressDialog.show();
            mFirebaseAuth.signInWithEmailAndPassword(Id, Pwd)
                    .addOnCompleteListener(requireActivity(), task -> {

                        if (task.isSuccessful()) {
                            if (setRemember) {
                                saveData(requireActivity(), Id, Pwd, setRemember);
                            }

                            UserDetails.setLastLogin(DateHelper());

                            fc.lockViewPager(false);

                            if ((Objects.requireNonNull(mFirebaseAuth.getCurrentUser())).isEmailVerified()) {
                                Intent a = new Intent(getContext(), HomeActivity.class);
                                startActivity(a);
                                requireActivity().finish();
                            } else {
                                Intent a = new Intent(getContext(), MailVerification.class);
                                startActivity(a);
                                requireActivity().finish();
                            }
                        } else {

                            AlertDialog.Builder builder=new AlertDialog.Builder(requireContext());
                            builder.setTitle("Error..");
                            builder.setCancelable(false);

                            if(!checkInternetAvailable(requireContext()))
                            {
                                fc.lockViewPager(false);
                                builder.setMessage("Internet Connection un-available");
                            }
                            else{
                                fc.lockViewPager(false);
                                builder.setMessage(Objects.requireNonNull(task.getException()).getMessage());
                            }
                            builder.setPositiveButton("CLOSE", (dialogInterface, i) -> dialogInterface.dismiss()).show();

                        }
                        progressDialog.dismiss();

                    });
        }
    }

}
