package com.kanahia.boolianapp.activities

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnScrollChangeListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kanahia.boolianapp.R
import com.kanahia.boolianapp.databinding.ActivityHomeBinding
import com.kanahia.boolianapp.utils.ClickShrinkEffectDebug
import com.kanahia.boolianapp.utils.applyClickShrink
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var b: ActivityHomeBinding
    private lateinit var alertDialog: AlertDialog
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(b.root)

        //Status bar setup
        supportActionBar?.hide()
        window.statusBarColor = Color.WHITE
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        auth = Firebase.auth

        //SETUP SCREEN
        sharedPreferences = getSharedPreferences(SHAREDPREFRENCE, MODE_PRIVATE)
        getCompletedCourses()


        //Checks for user and updates data
        if (auth.currentUser != null) {
            val name = auth.currentUser?.displayName.toString()
            val words = name.split("\\s".toRegex()).toTypedArray()
            b.tvName.setText(words[0])
            Glide.with(this).load(auth.currentUser!!.photoUrl.toString()).into(b.profileImage)
        }


        //Apply shrink effect
        b.introductionLl.applyClickShrink()
        b.profileImage.applyClickShrink()
        b.challengeFrameLayout.applyClickShrink()

        if (b.v2Lock.visibility != View.VISIBLE) {
            b.basicsLl.applyClickShrink()
        }

        if (b.v3Lock.visibility != View.VISIBLE) {
            b.sellLl.applyClickShrink()
        }

        //OnClick Listeners
        b.profileImage.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Do you want to Log Out?")
            builder.setCancelable(false)
            builder.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    if (auth.currentUser != null) {
                        auth.signOut()
                        Toast.makeText(this@HomeActivity, "LOG OUT SUCCESSFUL", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this@HomeActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@HomeActivity, "ERROR", Toast.LENGTH_SHORT).show()
                    }
                }
            })
            builder.setNegativeButton(
                "Cancel"
            ) { dialog, which -> alertDialog.dismiss() }

            alertDialog = builder.create()
            alertDialog.show()
        }

        b.introductionLl.setOnClickListener {
            itemOnClick(1)
        }
        b.basicsLl.setOnClickListener {
            itemOnClick(2)
        }
        b.sellLl.setOnClickListener {
            itemOnClick(3)
        }
        b.buyLl.setOnClickListener {
            itemOnClick(4)
        }
        b.advLl.setOnClickListener {
            itemOnClick(5)
        }
        b.challengeFrameLayout.setOnClickListener {
            Toast.makeText(this@HomeActivity, "Currently challenges are locked", Toast.LENGTH_SHORT)
                .show()
        }

    }

    fun itemOnClick(index: Int) {
        val intent = Intent(this, QuestionActivity::class.java)
        intent.putExtra(KEY, index)
        when (index) {
            1 -> {
                startActivity(intent)
            }
            2 -> {
                if (b.v2Lock.visibility == View.VISIBLE) {
                    Toast.makeText(this, "Content is locked", Toast.LENGTH_SHORT).show()
                } else {
                    startActivity(intent)
                }

            }
            3 -> {
                if (b.v3Lock.visibility == View.VISIBLE) {
                    Toast.makeText(this, "Content is locked", Toast.LENGTH_SHORT).show()
                } else {
                    startActivity(intent)
                }
            }
            else -> {
                Toast.makeText(this, "Content is locked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val TAG = "PRINT"
        const val KEY = "EXTRA"
        const val SHAREDPREFRENCE = "shared"
        const val COMPLETED = "completed"
    }

    fun getCompletedCourses() {
        if (sharedPreferences.getInt(COMPLETED, 0) < 4){
            progressBar.progress = sharedPreferences.getInt(COMPLETED, 0)
        }
        if (sharedPreferences.getInt(COMPLETED, 0) == 1) {
            v2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.v2_c))
            b.v2Lock.visibility = View.INVISIBLE
            v2Text.setTextColor(Color.BLACK)
        } else if (sharedPreferences.getInt(COMPLETED, 0) >= 2) {
            v2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.v2_c))
            b.v2Lock.visibility = View.INVISIBLE
            v2Text.setTextColor(Color.BLACK)

            v3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.v3_c))
            b.v3Lock.visibility = View.INVISIBLE
            v3Text.setTextColor(Color.BLACK)
        }


    }

}