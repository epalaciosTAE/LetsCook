package com.tae.letscook.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.tae.letscook.R;
import com.tae.letscook.Utils.ModelConverter;
import com.tae.letscook.app.LetsCookApp;
import com.tae.letscook.constants.ActionConstants;
import com.tae.letscook.constants.AnalyticsConstants;
import com.tae.letscook.constants.Constants;
import com.tae.letscook.model.Chef;
import com.tae.letscook.model.facebook.FacebookUser;
import com.tae.letscook.push_notification.GcmRegistrationAsyncTask;
import com.tae.letscook.service.LetsCookService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.Reader;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Eduardo on 21/01/2016.
 */
public class ActivityLogin extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener{

    @Bind(R.id.sign_in_button) protected SignInButton btnSingIn;
    @Bind(R.id.btn_login_facebook) protected LoginButton btnFacebookSignIn;
    @Bind(R.id.status) protected TextView tvStatus;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private static final int RC_GET_AUTH_CODE = 9003;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private CallbackManager callbackManager;
    private LoginReceiver loginReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        new GcmRegistrationAsyncTask(this).execute();
        LetsCookApp.getInstance().trackScreenView(AnalyticsConstants.LOGIN_SCREEN);
        setFacebookLoginButton();

        loginReceiver = new LoginReceiver();
        validateServerClientID();
        //Config sign in from device to google server
        configureSimpleSignInOptions();
        // Configure sign in from DEVICE TO SERVER
        GoogleSignInOptions gso = getGoogleSignInOptionsAuthCode();
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        buildGoogleApiClient(gso);
        setSignInButton(gso);


    }

    /**
     * Set a callback in the facebook button to retrieve a response from Facebook with the user data
     */
    private void setFacebookLoginButton() {
        Log.i(TAG, "setFacebookLoginButton: ");
        callbackManager = CallbackManager.Factory.create();
        btnFacebookSignIn.setReadPermissions(Arrays.asList("public_profile", "user_friends", "user_birthday", "user_about_me", "email", "user_status"));
        btnFacebookSignIn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i(TAG, "onSuccess: Facebook sign in done");
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        FacebookUser facebookUser = ModelConverter.convertFacebookModelToChef(object);
                        startSplashActivity(new Chef(facebookUser.getName(),facebookUser.getEmail(), facebookUser.getPicture().getData().getUrl()));
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name , link, birthday, picture, email, gender");
                graphRequest.setParameters(parameters);
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "onError: Error login in Facebook", error);
                LetsCookApp.getInstance().trackException(error);
            }
        });
    }



    private void startSplashActivity(final Chef chef) {
        startActivity(new Intent(ActivityLogin.this, ActivitySplash.class).putExtra(Constants.EXTRA_CHEF, chef)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    /**
     *
     Customize sign-in button. The sign-in button can be displayed in
     multiple sizes and color schemes. It can also be contextually
     rendered based on the requested scopes. For example. a red button may
     be displayed when Google+ scopes are requested, but a white button
     may be displayed when only basic profile is requested. Try adding the
     Scopes.PLUS_LOGIN scope to the GoogleSignInOptions to see the
     difference.
     * @param gso
     */
    private void setSignInButton(GoogleSignInOptions gso) {
        Log.i(TAG, "setSignInButton: ");
        btnSingIn = (SignInButton) findViewById(R.id.sign_in_button);
        btnSingIn.setSize(SignInButton.SIZE_STANDARD);
        btnSingIn.setScopes(gso.getScopeArray());
    }

    /**
     * Create the Google api client to add the auth api
     * @param gso
     */
    private void buildGoogleApiClient(GoogleSignInOptions gso) {
        Log.i(TAG, "buildGoogleApiClient: ");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    /**
     * Create the sign in options wiht the server client id
     * RequestScopes for different Google apis
     * Request auth code, email ,etc
     * @return GoogleSignInOptions
     */
    @NonNull
    private GoogleSignInOptions getGoogleSignInOptionsAuthCode() {
        Log.i(TAG, "getGoogleSignInOptionsAuthCode: ");
        String serverClientId = getString(R.string.server_client_id);
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER)) // TODO this is just an example, no use for google drive at the moment
                .requestServerAuthCode(serverClientId)
                .requestEmail()
                .build();
    }

    /**
     * Configure sign-in to request the user's ID, email address, and basic
         profile. ID and basic profile are included in DEFAULT_SIGN_IN
     */
    private void configureSimpleSignInOptions() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);

        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            Log.d(TAG, "User is not sign-in");
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: ");
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
//
//        }
        //Handle the auth code to server
        if (requestCode == RC_GET_AUTH_CODE) {
            authorizeGoogleSignInInServer(data);
        } else { // TODO facebook result. Without this the callback will never get the response
            callbackManager.onActivityResult(requestCode, resultCode, data);

        }
    }

    private void authorizeGoogleSignInInServer(Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        Log.d(TAG, "onActivityResult:GET_AUTH_CODE:success:" + result.getStatus().isSuccess());
        if (result.isSuccess()) {
            // [START get_auth_code]
            GoogleSignInAccount acct = result.getSignInAccount();
            String authCode = acct.getServerAuthCode();
            Log.d(TAG, "onActivityResult: authCode: " + authCode);
            // Show signed-in UI.
            tvStatus.setText(acct.getDisplayName()); // TODO do this after get good response from server
            updateUI(true);

            // TODO(user): send code to server and exchange for access/refresh/ID tokens.
            startService(LetsCookService.makeIntentLogin(ActivityLogin.this, authCode));
        }
    }


    /**
     * Validates that there is a reasonable server client ID in strings.xml, this is only needed
     * to make sure users of this sample follow the README.
     */
    private void validateServerClientID() {
        Log.i(TAG, "validateServerClientID: ");     
        String serverClientId = getString(R.string.server_client_id);
        String suffix = ".apps.googleusercontent.com";
        if (!serverClientId.trim().endsWith(suffix)) {
            String message = "Invalid server client ID in strings.xml, must end with " + suffix;

            Log.w(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
//            GoogleSignInAccount acct = result.getSignInAccount();
//            String authCode = acct.getServerAuthCode();
            tvStatus.setText(getString(R.string.signed_in_fmt, result.getSignInAccount().getDisplayName()));
            updateUI(true); // TODO delete not need it things-->REFACTOR
            startSplashActivity(new Chef(
                    result.getSignInAccount().getDisplayName(),
                    result.getSignInAccount().getEmail(),
                    String.valueOf(result.getSignInAccount().getPhotoUrl())));
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    /**
     * Start activity for result to sign in the user with the google api
     */
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * Sign out
     */
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.i(TAG, "onResult: sign out " + status.getStatusMessage());
                        updateUI(false);
                    }
                });
    }

    /**
     * Revoke access
     */
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.i(TAG, "onResult: revoke access" + status.getStatusMessage());
                        updateUI(false);
                    }
                });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            btnSingIn.setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            tvStatus.setText(R.string.signed_out);
            btnSingIn.setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    /**
     * Init Activity for result to get the authorization code from google api:
      Start the retrieval process for a server auth code.  If requested, ask for a refresh
      token.  Otherwise, only get an access token if a refresh token has been previously
      retrieved.  Getting a new access token for an existing grant does not require
      user consent.
     */
    private void getAuthCode() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GET_AUTH_CODE);
    }

    @OnClick({R.id.sign_in_button, R.id.sign_out_button, R.id.disconnect_button, R.id.btn_login_facebook})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
//                signIn();
                getAuthCode();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;

            case R.id.btn_login_facebook:
                LetsCookApp.getInstance().trackEvent(AnalyticsConstants.EVENT_LOGIN_FACEBOOK, AnalyticsConstants.ACTION_LOGIN_FACEBOOK, AnalyticsConstants.CONNECTING_FACEBOOK);
                Log.i(TAG, "onClick: loggin facebook");
                break;
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(loginReceiver, new IntentFilter(ActionConstants.ACTION_SIGN_IN_SUCCESS));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(loginReceiver);
    }

    /**
     * Inner Broadcast Receiver class to handle the response from the server
     * with the user information
     */
    public class LoginReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ActionConstants.ACTION_SIGN_IN_SUCCESS)) {
                Log.i(TAG, "onReceive: user sign in successfully");
                startSplashActivity((Chef) intent.getParcelableExtra(Constants.EXTRA_CHEF));
            }
        }
    }
}
