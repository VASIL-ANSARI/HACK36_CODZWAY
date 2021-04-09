package com.tlabs.something.Utils;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;

public class UserDetails {
    private UserDetails(){}
    static FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
   static DocumentReference documentReference= FirebaseFirestore.getInstance().collection("users").document(getUid());




    public static String getUid(){
        return firebaseUser.getUid();
    }
    public static boolean isEmailVerified(){
        return firebaseUser.isEmailVerified();
    }

    public static String getName() {
        return firebaseUser.getDisplayName();
    }

    public static String getMail() {
        return firebaseUser.getEmail();
    }


    public static FirebaseUser getCurrentUser(){
        return firebaseUser;
    }

    public static void setName(String name) {
        HashMap<String,String> nameMap=new HashMap<>();
        nameMap.put("name",name);
        documentReference.set(nameMap, SetOptions.merge());
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name).build();
        firebaseUser.updateProfile(profileUpdates);
    }

    public static void setMail(String mail) {
        HashMap<String,String> mailMap=new HashMap<>();
        mailMap.put("mail",mail);
        documentReference.set(mailMap, SetOptions.merge());
    }

    public static void setImageUrl(String imageUrl) {
        HashMap<String,String> imageMap=new HashMap<>();
        imageMap.put("image url",imageUrl);
        documentReference.set(imageMap, SetOptions.merge());
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse(imageUrl)).build();
        firebaseUser.updateProfile(profileUpdates);
    }

    public static void setPassword(String password) {
        HashMap<String,String> pwd=new HashMap<>();
        pwd.put("password",password);
        documentReference.set(pwd, SetOptions.merge());
    }

    public static void setDeviceToken(String deviceToken) {
        HashMap<String,String> token=new HashMap<>();
        token.put("token",deviceToken);
        documentReference.set(token, SetOptions.merge());
    }

    public static void setPhone(String phone) {
        HashMap<String,String> phoneMap=new HashMap<>();
        phoneMap.put("phone",phone);
        documentReference.set(phoneMap, SetOptions.merge());

    }
    public static void setMailVerified(Boolean status){
        HashMap<String,Boolean> verified=new HashMap<>();
        verified.put("mail Verified",status);
        documentReference.set(verified, SetOptions.merge());
    }
    public static void setLastLogin(String time){
        HashMap<String,String> lastlogin=new HashMap<>();
        lastlogin.put("last login",time);
        documentReference.set(lastlogin, SetOptions.merge());
    }
}
