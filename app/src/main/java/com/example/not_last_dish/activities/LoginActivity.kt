package com.example.not_last_dish.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.not_last_dish.R
import com.example.not_last_dish.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private var isUserNameValid: Boolean = false
    private var isPasswordValid: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username : EditText = binding.username
        val password : EditText = binding.password
        val loginBtn : Button = binding.login
        val signUpBtn : Button = binding.registerStart
        val loadingAnim : ProgressBar = binding.loading

        Log.v("TAGG", "start 1")
        username.afterTextChanged {
            if (!isUserNameValid(username.text.toString())) {
                //TODO: To show error message with the red icons within field
                username.error = getString(R.string.invalid_username)
                isUserNameValid = false
            } else {
                //TODO: To change login button state
                isUserNameValid = true
                //loginBtn.isEnabled = isUserNameValid and isPasswordValid
            }
        }

        password.afterTextChanged {
            if (!isPasswordValid(password.text.toString())) {
                //TODO: To show error message with the red icons within field
                password.error = getString(R.string.invalid_password)
                isPasswordValid = false
            } else {
                //TODO: To change login button state
                isPasswordValid = true
                //loginBtn.isEnabled = isUserNameValid and isPasswordValid
            }
        }


        loginBtn.setOnClickListener {
            loadingAnim.visibility = View.VISIBLE
            Log.v("TAGG", "AA1")
            /*TODO: Should think about how to wait until result gets true value
            because when we put name and password it will validate success but
            the value is returned by verificationUser will be false because
            variable result get value true only after returning value
             */
            if (isUserNameValid and isPasswordValid) {
                verificationUser(username.text.toString(), password.text.toString())
            } else {
                loadingAnim.visibility = View.GONE
                //Toast.makeText(applicationContext, "Пустые поля!", Toast.LENGTH_LONG).show()
            }
        }

        signUpBtn.setOnClickListener {
            Log.v("TAGG", "start 1")
            val i = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    //TODO: need to change validation conditions
    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    //TODO: need to change validation conditions
    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return (password.length > 8) and !password.contains(" ")
    }

    /**
     * Extension function to simplify setting an afterTextChanged action to EditText components.
     */
    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun verificationUser(username: String, password: String) : Boolean {
        var result = false
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.0.14:80/Config/index.php"
        // Request a string response from the provided URL.
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, url, Response.Listener { response ->
                when (response) {
                    "Login Success" -> {
                        result = true; Log.v("TAGG", "AA1$result")
                        val i = Intent(applicationContext, MainActivity::class.java)
                        startActivity(i)
                        finish()
                    }
                    else -> {
                        Toast.makeText(
                            applicationContext,
                            "Can not log in!",
                            Toast.LENGTH_LONG
                        ).show()
                        binding.login.isEnabled = false
                        binding.loading.visibility = View.GONE
                    }
                }
            }, Response.ErrorListener {
                Toast.makeText(applicationContext,"That didn't work!", Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["name"] = username
                params["password"] = password

                return params
            }
        }
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
        Log.v("TAGG", "AA1$result")
        return result
    }

    //TODO: the first implementation of function verificationUser()
//    private fun verificationUser(username: String, password: String) : Boolean {
//        var result = false
//        // Instantiate the RequestQueue.
//        val queue = Volley.newRequestQueue(this)
//        val url = "http://192.168.0.14:80/Config/index.php"
//        // Request a string response from the provided URL.
//        val stringRequest: StringRequest = object : StringRequest(
//            Method.POST, url, Response.Listener { response ->
//                when (response) {
//                    "Login Success" -> {
//                        result = true; Log.v("TAGG", "AA1$result");
//                    }
//                    else -> {
//                        Toast.makeText(
//                            applicationContext,
//                            "Can not log in!$response",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                }
//            }, Response.ErrorListener {
//                Toast.makeText(applicationContext,"That didn't work!", Toast.LENGTH_LONG).show()
//            }
//        ) {
//            override fun getParams(): Map<String, String>? {
//                val params: MutableMap<String, String> = HashMap()
//                params["name"] = username
//                params["password"] = password
//
//                return params
//            }
//        }
//            // Add the request to the RequestQueue.
//            queue.add(stringRequest)
//        Log.v("TAGG", "AA1$result")
//        return result
//    }
    //TODO: The first implementation of loginBtn.setOnClickListener()
//    loginBtn.setOnClickListener {
//        loadingAnim.visibility = View.VISIBLE
//        Log.v("TAGG", "AA1")
//        /*TODO: Should think about how to wait until result gets true value
//        because when we put name and password it will validate success but
//        the value is returned by verificationUser will be false because
//        variable result get value true only after returning value
//         */
//        if (verificationUser(username.text.toString(), password.text.toString())) {
//            Log.v("TAGG", "AA2")
//            val i = Intent(applicationContext, MainActivity::class.java)
//            startActivity(i)
//            finish()
//        } else {
//            Log.v("TAGG", "AA3")
//            loginBtn.isEnabled = false
//            loadingAnim.visibility = View.GONE
//        }
//    }
}