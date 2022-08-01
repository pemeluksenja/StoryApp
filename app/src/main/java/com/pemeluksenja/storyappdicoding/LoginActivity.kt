package com.pemeluksenja.storyappdicoding

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.pemeluksenja.storyappdicoding.databinding.ActivityLoginBinding
import com.pemeluksenja.storyappdicoding.model.LoginModel
import com.pemeluksenja.storyappdicoding.model.LoginResponse
import com.pemeluksenja.storyappdicoding.model.LoginResult
import com.pemeluksenja.storyappdicoding.retrofitconfig.APIConfig
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var bind: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.moveToRegisterButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        bind.loginButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.emailEditTextCustom)
            val password = findViewById<EditText>(R.id.passwordEditTextCustom)
            if (email !== null && password !== null) {
                when {
                    email.length() < 6 -> {
                        email.error = "Email harus lebih panjang dari 6 karakter"
                        showLoadingProcess(false)
                    }
                    password.length() < 6 -> {
                        password.error = "Password harus lebih panjang dari 6 karakter"
                        showLoadingProcess(false)
                    }
                    !isValidEmail(email.text.toString()) -> {
                        email.error = "Invalid Email"
                        showLoadingProcess(false)
                    }
                    else -> {
                        login(email.text.toString(), password.text.toString())
                        var context = application
                        var sharedPref = context.getSharedPreferences(
                            R.string.token_pref.toString(),
                            Context.MODE_PRIVATE
                        )
                        val editor = sharedPref.edit()
                        editor.putString(getString(R.string.email), email.text.toString())
                        editor.putString(getString(R.string.password), password.text.toString())
                        editor.apply()
                        showLoadingProcess(true)
                    }
                }
            }
        }
        showLoadingProcess(false)
    }

    private fun login(email: String, password: String) {
        val data = LoginModel(email, password)
        val client = APIConfig.getAPIServices().login(data)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    showLoadingProcess(false)
                    val resBody = response.body()!!
                    Log.d("Login: ", resBody.toString())
                    val token = getToken(resBody.loginResult)
                    Log.d("Token: ", token)
                    val context = application
                    val sharedPref = context.getSharedPreferences(
                        R.string.token_pref.toString(),
                        Context.MODE_PRIVATE
                    )
                    val editor = sharedPref.edit()
                    editor.putString(R.string.token.toString(), token)
                    TOKEN = token
                    Log.d("TokenLogin: ", TOKEN)
                    editor.apply()
                    Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else if (!response.isSuccessful) {
                    showLoadingProcess(false)
                    val resBody = JSONObject(response.errorBody()!!.string())
                    val msg = resBody.getString("message")
                    Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_SHORT).show()
                    Log.d("MSGOBJ", msg)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun getToken(loginResult: LoginResult): String = loginResult.token
    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showLoadingProcess(isLoading: Boolean) {
        if (isLoading) {
            bind.loading.visibility = View.VISIBLE
        } else {
            bind.loading.visibility = View.GONE
        }
    }

    companion object {
        private val TAG = "LoginActivity"
        var TOKEN = ""
    }
}