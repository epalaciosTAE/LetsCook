package com.tae.letscook.model.google;

/**
 * Created by Eduardo on 22/01/2016.
 */
public class GoogleUser {


    private String name;
    private String email;
    private String userId; // Use this value as a key to identify a user.
    private boolean emailVerified;
    private String pictureUrl;
    private String locale;
    private String familyName;
    private String givenName;

    public GoogleUser (String name, String email, String userId, boolean emailVerified, String pictureUrl, String locale, String familyName, String givenName) {
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.emailVerified = emailVerified;
        this.pictureUrl = pictureUrl;
        this.locale = locale;
        this.familyName = familyName;
        this.givenName = givenName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getLocale() {
        return locale;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGivenName() {
        return givenName;
    }
}
