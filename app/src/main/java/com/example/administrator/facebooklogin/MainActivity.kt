package com.example.administrator.facebooklogin

import android.accounts.AuthenticatorException
import android.content.Intent
import android.content.pm.PackageInstaller
import android.databinding.DataBindingUtil
import android.media.MediaCas
import android.media.tv.TvInputService
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.service.textservice.SpellCheckerService
import android.util.Log
import android.view.View
import android.widget.Toast

import com.facebook.*

import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.Authenticator
import java.util.*

var callbackmanager: CallbackManager? = null

class MainActivity : AppCompatActivity() {

    var switch: Boolean = false
    var data: MutableMap<String, String>? = mutableMapOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext())
        login_button.setReadPermissions(Arrays.asList(
                "public_profile", "email", ""))

        // LoginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_ONLY);
        callbackmanager = CallbackManager.Factory.create()


        login_button.registerCallback(callbackmanager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {

                        var request: GraphRequest = GraphRequest.newMeRequest(loginResult.accessToken, object : GraphRequest.GraphJSONObjectCallback {
                            override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {

                                if (`object`!!.has("email")) {

                                    data!!.put("email", `object`!!.getString("email"))
                                }

                                if (`object`!!.has("id")) {

                                    data!!.put("id", `object`!!.getString("id"))
                                }


                                if (`object`!!.has("name")) {

                                    data!!.put("name", `object`!!.getString("name"))
                                }


                                if (`object`!!.has("first_name")) {

                                    data!!.put("first_name", `object`!!.getString("first_name"))
                                }


                                if (`object`!!.has("last_name")) {

                                    data!!.put("last_name", `object`!!.getString("last_name"))
                                }

                                var datastr = ""
                                for (i in data!!) {
                                    datastr += i.key + " :" + i.value+"\n"
                                }
                                conditiontext.text = datastr


                            }
                        })
                        val parameters = Bundle()
                        //        parameters.putString("fields", "id,name,email,first_name,last_name");
                        parameters.putString("fields", "id,name,email,gender,birthday,first_name,last_name")
                        request.parameters = parameters
                        request.executeAsync()


                    }

                    override fun onCancel() {

                    }

                    override fun onError(exception: FacebookException) {

                    }
                })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackmanager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}
