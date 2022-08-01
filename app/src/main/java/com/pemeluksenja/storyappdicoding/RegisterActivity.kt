package com.pemeluksenja.storyappdicoding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.pemeluksenja.storyappdicoding.databinding.ActivityRegisterBinding
import com.pemeluksenja.storyappdicoding.model.RegisterModel
import com.pemeluksenja.storyappdicoding.retrofitconfig.APIConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var bind: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        bind = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.registerButton.setOnClickListener {
            val name = findViewById<EditText>(R.id.nameEditTextCustom)
            Log.d("EditText: ", name.text.toString())
            val email = findViewById<EditText>(R.id.emailEditTextCustom)
            val password = findViewById<EditText>(R.id.passwordEditTextCustom)
            if (email !== null && name !== null && password !== null) {
                when {
                    email.length() < 6 -> {
                        email.error = "Email harus lebih panjang dari 6 karakter"
                    }
                    password.length() < 6 -> {
                        password.error = "Password harus lebih panjang dari 6 karakter"
                    }
                    name.length() < 6 -> {
                        name.error = "Nama harus lebih panjang dari 6 karakter"
                    }
                    !isValidEmail(email.text.toString()) -> {
                        email.error = "Invalid Email"
                    }
                    else -> {
                        register(
                            name.text.toString(),
                            email.text.toString(),
                            password.text.toString()
                        )
                        showLoadingProcess(true)
                    }
                }
            }
        }
        bind.moveToLoginButton.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }
        showLoadingProcess(false)
    }

    private fun register(name: String, email: String, password: String) {
        val data = RegisterModel(name, email, password)
        val client = APIConfig.getAPIServices().register(data)
        client.enqueue(object : Callback<RegisterModel> {
            override fun onResponse(
                call: Call<RegisterModel>,
                response: Response<RegisterModel>
            ) {
                if (response.isSuccessful) {
                    val resBody = response.body()
                    Log.d("Register: ", resBody.toString())
                    Toast.makeText(this@RegisterActivity, "Register Berhasil", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                }
            }

            override fun onFailure(call: Call<RegisterModel>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showLoadingProcess(isLoading: Boolean) {
        if (isLoading) {
            bind.loading.visibility = View.VISIBLE
        } else {
            bind.loading.visibility = View.GONE
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    companion object {
        private val TAG = "RegisterActivity"
    }
}