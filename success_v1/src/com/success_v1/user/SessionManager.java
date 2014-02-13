package com.success_v1.user;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
 
public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
     
    // Editor for Shared preferences
    Editor editor;
     
    // Context
    Context _context;
     
    // Shared pref mode
    int PRIVATE_MODE = 0;
     
    // Sharedpref file name
    private static final String PREF_NAME = "SuccessCarPref";
     
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
     
    // User name (make variable public to access from outside)
    public static final String KEY_NOM = "nom";
    // Email address (make variable public to access from outside)
    public static final String KEY_MAIL = "mail";
    public static final String KEY_PRENOM = "prenom";
    public static final String KEY_MDP = "mdp";
    public static final String KEY_ADRESSE = "adresse";
    public static final String KEY_NUMPERMIS = "numPermis";
    public static final String KEY_DATENAIS = "dateNaissance";
    public static final String KEY_DATERETRAITPERMIS = "dateRetraitPermis";
    public static final String KEY_GENRE = "genre";
    public static final String KEY_CODEPOST = "codePost";
    public static final String KEY_VILLE = "ville";
    public static final String KEY_PAYS = "pays";
    public static final String KEY_NUM = "num";
    public static final String KEY_ID = "id";
    
    
    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
   public void createLoginSession(String id,String nom, String prenom, String mdp, String adresse,String numPermis, String dateNais, String dateRetraitPermis, String genre, String codePost, String ville, String pays ,String num, String mail){
       // Storing login value as TRUE
       editor.putBoolean(IS_LOGIN, true);
       editor.putString(KEY_ID, id);
       // Storing name in pref
       editor.putString(KEY_NOM, nom);
       editor.putString(KEY_PRENOM, prenom);
       editor.putString(KEY_MDP, mdp);
       editor.putString(KEY_ADRESSE, adresse);
       editor.putString(KEY_NUMPERMIS, numPermis);
       editor.putString(KEY_DATENAIS, dateNais);
       editor.putString(KEY_DATERETRAITPERMIS, dateRetraitPermis);
       editor.putString(KEY_GENRE, genre);
       editor.putString(KEY_CODEPOST, codePost);
       editor.putString(KEY_VILLE, ville);
       editor.putString(KEY_PAYS, pays);
       editor.putString(KEY_NUM, num);
       // Storing email in pref
       editor.putString(KEY_MAIL, mail);
       // commit changes
       editor.commit();
   }
   /**
    * Check login method wil check user login status
    * If false it will redirect user to login page
    * Else won't do anything
    * */
   public void checkLogin(){
       // Check login status
       if(!this.isLoggedIn()){
           // user is not logged in redirect him to Login Activity
           Intent i = new Intent(_context, LogPage.class);
           // Closing all the Activities
           i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            
           // Add new Flag to start new Activity
           i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            
           // Staring Login Activity
           _context.startActivity(i);
       }
        
   }
    
    
   /**
    * Get stored session data
    * */
   public HashMap<String, String> getUserDetails(){
       HashMap<String, String> user = new HashMap<String, String>();
       user.put(KEY_ID, pref.getString(KEY_ID, null));
       user.put(KEY_NOM, pref.getString(KEY_NOM, null));
       user.put(KEY_PRENOM, pref.getString(KEY_PRENOM, null));
       user.put(KEY_MDP, pref.getString(KEY_MDP, null));
       user.put(KEY_ADRESSE, pref.getString(KEY_ADRESSE, null));
       user.put(KEY_NUMPERMIS, pref.getString(KEY_NUMPERMIS, null));
       user.put(KEY_DATENAIS, pref.getString(KEY_DATENAIS, null));
       user.put(KEY_DATERETRAITPERMIS, pref.getString(KEY_DATERETRAITPERMIS, null));
       user.put(KEY_GENRE, pref.getString(KEY_GENRE, null));
       user.put(KEY_CODEPOST, pref.getString(KEY_CODEPOST, null));
       user.put(KEY_VILLE, pref.getString(KEY_VILLE, null));
       user.put(KEY_PAYS, pref.getString(KEY_PAYS, null));
       user.put(KEY_NUM, pref.getString(KEY_NUM, null));
       user.put(KEY_MAIL, pref.getString(KEY_MAIL, null));
       // return user
       return user;
   }
   
   public String getIdUser(){
	   return pref.getString(KEY_ID, null);
   }
    
   /**
    * Clear session details
    * */
   public void logoutUser(){
       // Clearing all data from Shared Preferences
       editor.clear();
       editor.commit();
        
       // After logout redirect user to Loing Activity
       Intent i = new Intent(_context, LogPage.class);
       // Closing all the Activities
       i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        
       // Add new Flag to start new Activity
       i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        
       // Staring Login Activity
       _context.startActivity(i);
   }
    
   /**
    * Quick check for login
    * **/
   // Get Login State
   public boolean isLoggedIn(){
       return pref.getBoolean(IS_LOGIN, false);
   }
    
}