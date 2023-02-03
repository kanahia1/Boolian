package com.kanahia.boolianapp.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import com.kanahia.boolianapp.databinding.ActivityResultBinding
import com.kanahia.boolianapp.utils.applyClickShrink
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    private lateinit var b : ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        b = ActivityResultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(b.root)
        //STATUS BAR SETUP
        supportActionBar?.hide()
        window.statusBarColor = Color.WHITE
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        //SETUP COMPLETED COURSES
        val sharedPreferences = getSharedPreferences(SHAREDPREFRENCE, MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putInt(COMPLETED,sharedPreferences.getInt(COMPLETED,0)+1)
        editor.commit()

        //On click
        imageButton.applyClickShrink()
        b.imageButton.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java) )
            finish()


        }

    }
    companion object{
        const val SHAREDPREFRENCE = "shared"
        const val COMPLETED = "completed"
    }
}