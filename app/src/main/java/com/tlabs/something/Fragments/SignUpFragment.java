package com.tlabs.something.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.tlabs.something.Activities.MailVerification;
import com.tlabs.something.Interfaces.FragmentSwitcher;
import com.tlabs.something.R;
import com.tlabs.something.Utils.Methods;
import com.tlabs.something.Utils.UserDetails;

import java.util.Objects;

import static com.tlabs.something.Utils.Methods.checkInternetAvailable;
import static com.tlabs.something.Utils.Methods.isValidEmail;

public class SignUpFragment extends Fragment {


    private FirebaseAuth mFirebaseAuth;
    private EditText mName,mEmail,mPwd,mRePwd;
    private FragmentSwitcher fc;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frag_view = inflater.inflate(R.layout.fragment_signup, container, false);

        //Hook up objects to all global variables
        mFirebaseAuth= FirebaseAuth.getInstance();
        mName = frag_view.findViewById(R.id.name);
        mPwd =frag_view.findViewById(R.id.sign_up_pwd_in);
        mRePwd =frag_view.findViewById(R.id.sign_up_confirm_pwd_in);
        mEmail=frag_view.findViewById(R.id.sign_up_email);
        fc=(FragmentSwitcher)getActivity();

        //Hook Objects to local variables
        ImageView signup = frag_view.findViewById(R.id.sign_up);
        ImageButton mPwdToggle = frag_view.findViewById(R.id.sign_up_password_toggle);
        ImageButton mCPwdToggle = frag_view.findViewById(R.id.sign_up_confirm_password_toggle);
        TextView moveToLogin = frag_view.findViewById(R.id.text_login);
        TextView privacy=frag_view.findViewById(R.id.privacyPolicy);

        privacy.setOnClickListener(v -> Methods.openPolicyPage(Objects.requireNonNull(getActivity())));

        //For arrow that triggers a sign up event
        signup.setOnClickListener(v -> {
            fc.lockViewPager(true);
            registerHelper();
        });




        //For password toggle on SignUp Screen
        mPwdToggle.setOnClickListener(new View.OnClickListener() {
            int count = 0;
            @Override
            public void onClick(View v) {
                if (count % 2 == 0) {
                    v.setBackgroundResource(R.drawable.ic_baseline_visibility_24);
                    mPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    v.setBackgroundResource(R.drawable.ic_baseline_visibility_off_24);
                    mPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                count++;
            }
        });

        //For confirm password toggle on SignUp Screen
        mCPwdToggle.setOnClickListener(new View.OnClickListener() {
            int count = 0;
            @Override
            public void onClick(View v) {
                if (count % 2 == 0) {
                    v.setBackgroundResource(R.drawable.ic_baseline_visibility_24);
                    mRePwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    v.setBackgroundResource(R.drawable.ic_baseline_visibility_off_24);
                    mRePwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                count++;
            }
        });



        //For moving to Login screen when user clicks Already a user? : login
        moveToLogin.setOnClickListener(v -> {
            FragmentSwitcher fc= (FragmentSwitcher) getActivity();
            assert fc != null;
            fc.changeToFragment(0);
        });

        return frag_view;
    }


    //  This Method checks the user SignUp information for Typos and shows errors otherwise  will add
    //   user to the data base
    private void registerHelper(){
        final String Name,Email,Password,RePassword;

        Name=mName.getText().toString();
        Email = mEmail.getText().toString();
        Password = mPwd.getText().toString();
        RePassword = mRePwd.getText().toString();

        if(Name.isEmpty()){
            mName.setError("Enter Name");
            mName.requestFocus();
            fc.lockViewPager(false);

        }
        else if (!isValidEmail(Email)) {
            mEmail.setError("Provide Valid Mail");
            mEmail.requestFocus();
            fc.lockViewPager(false);
        }
        else if (Password.length()<8) {
            mPwd.setError("Minimum password length should be 8");
            mPwd.requestFocus();
            fc.lockViewPager(false);
        }
        else    if ((!Password.equals(RePassword))) {
            mPwd.setError("Passwords did not matched");
            mPwd.requestFocus();
            mRePwd.setError("Passwords did not matched");
            mRePwd.requestFocus();
        }
        else{
            AlertDialog progressDialog= Methods.progressDialog(getContext(),"Creating Your Account..");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mFirebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(requireActivity()
                    , task -> {
                        if (!task.isSuccessful()) {
                            AlertDialog.Builder builder=new AlertDialog.Builder(requireContext());
                            builder.setTitle("Error..");
                            builder.setCancelable(false);

                            fc.lockViewPager(false);
                            if(checkInternetAvailable(requireContext())) {
                                builder.setMessage(Objects.requireNonNull(task.getException()).getMessage());
                            }
                            else{
                                builder.setMessage("Network un-available");
                            }
                            builder.setPositiveButton("CLOSE", (dialogInterface, i) -> dialogInterface.dismiss()).show();

                        } else {
                            UserDetails.setName(Name);
                            UserDetails.setMail(Email);
                            UserDetails.setPassword(Password);
                            UserDetails.setMailVerified(false);
                            startActivity(new Intent(getContext(), MailVerification.class));
                            requireActivity().finish();
                        }
                        progressDialog.dismiss();
                    });

        }
    }


}