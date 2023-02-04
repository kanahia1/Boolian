package com.kanahia.boolianapp.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.kanahia.boolianapp.R
import com.kanahia.boolianapp.databinding.ActivityQuestionBinding
import com.kanahia.boolianapp.models.*
import com.kanahia.boolianapp.utils.Constants
import com.kanahia.boolianapp.utils.applyClickShrink
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.activity_question.*


class QuestionActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var b: ActivityQuestionBinding
    private lateinit var alertDialog: AlertDialog
    private lateinit var dialog: Dialog
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var youTubePlayerView: YouTubePlayerView
    private var selectedId = 0
    private var selectedItem = 0
    private var items = 2
    private var data: Data? = null
    private var l1C = false
    private var l2C = false
    private var d1C = false
    private var d2C = false
    private var ans = ""
    private var objList = ArrayList<Any>()


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        b = ActivityQuestionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(b.root)
        //Setup screen
        setupScreen(intent.getIntExtra(KEY, 10))

        dialog = Dialog(this)
        dialog.setContentView(R.layout.screen_demo)
        //STATUS BAR SETUP
        supportActionBar?.hide()
        window.statusBarColor = Color.WHITE
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        //SETS IMAGE VIEW's WIDTH AND HEIGHT
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val height = width * heightWidthRatio
        b.introImage.layoutParams = FrameLayout.LayoutParams(width, height.toInt())
        //SETUP YOUTUBE PLAYER
        youTubePlayerView = findViewById<YouTubePlayerView>(R.id.youtube_player_view)
        lifecycle.addObserver(youTubePlayerView)

        //OnClick
        o1Btn.setOnClickListener(this)
        o2Btn.setOnClickListener(this)
        o3Btn.setOnClickListener(this)
        o4Btn.setOnClickListener(this)

        b.backLinearLayoutIntro.setOnClickListener {
            prevous()
        }
        b.nextLinearLayoutIntro.setOnClickListener {
            next()
        }
        b.backLinearLayoutRead.setOnClickListener {
            prevous()
        }
        b.nextLinearLayoutRead.setOnClickListener {
            next()
        }
        b.backLinearLayoutVideo.setOnClickListener {
            prevous()
        }
        b.nextLinearLayoutVideo.setOnClickListener {
            next()
        }
        b.backLinearLQuiz.setOnClickListener {
            prevous()
        }
        b.nextBtn.applyClickShrink()

        b.likeBtn.setOnClickListener {
            doLike(it)
        }
        b.likeBtn2.setOnClickListener {
            doLike(it)
        }
        b.dislikeBtn.setOnClickListener {
            doDisLike(it)
        }
        b.dislikeBtn2.setOnClickListener {
            doDisLike(it)
        }

        b.nextBtn.setOnClickListener {

            ans = data!!.quizList[0].ans
            if (b.textNext.text == "Next") {
                b.textNext.text = "Submit"
                when (ans) {
                    o1.text -> {
                        b.o1.setTextColor(Color.WHITE)
                        b.o1Btn.background =
                            ContextCompat.getDrawable(this, R.drawable.question_r)

                    }
                    o2.text -> {
                        b.o2.setTextColor(Color.WHITE)
                        b.o2Btn.background =
                            ContextCompat.getDrawable(this, R.drawable.question_r)

                    }
                    o3.text -> {
                        b.o3.setTextColor(Color.WHITE)
                        b.o3Btn.background =
                            ContextCompat.getDrawable(this, R.drawable.question_r)

                    }
                    o4.text -> {
                        b.o4.setTextColor(Color.WHITE)
                        b.o4Btn.background =
                            ContextCompat.getDrawable(this, R.drawable.question_r)

                    }
                }
                when (selectedId) {
                    R.id.o1Btn -> {
                        if (ans == b.o1.text) {
                            b.o1.setTextColor(Color.WHITE)
                            b.o1Btn.background =
                                ContextCompat.getDrawable(this, R.drawable.question_r)
                        } else {
                            b.o1.setTextColor(Color.WHITE)
                            b.o1Btn.background =
                                ContextCompat.getDrawable(this, R.drawable.question_w)

                        }

                    }
                    R.id.o2Btn -> {
                        if (ans == b.o2.text) {
                            b.o2.setTextColor(Color.WHITE)
                            b.o2Btn.background =
                                ContextCompat.getDrawable(this, R.drawable.question_r)
                        } else {
                            b.o2.setTextColor(Color.WHITE)
                            b.o2Btn.background =
                                ContextCompat.getDrawable(this, R.drawable.question_w)

                        }

                    }
                    R.id.o3Btn -> {
                        if (ans == b.o3.text) {
                            b.o3.setTextColor(Color.WHITE)
                            b.o3Btn.background =
                                ContextCompat.getDrawable(this, R.drawable.question_r)
                        } else {
                            b.o3.setTextColor(Color.WHITE)
                            b.o3Btn.background =
                                ContextCompat.getDrawable(this, R.drawable.question_w)

                        }

                    }
                    R.id.o4Btn -> {
                        if (ans == b.o4.text) {
                            b.o4.setTextColor(Color.WHITE)
                            b.o4Btn.background =
                                ContextCompat.getDrawable(this, R.drawable.question_r)
                        } else {
                            b.o4.setTextColor(Color.WHITE)
                            b.o4Btn.background =
                                ContextCompat.getDrawable(this, R.drawable.question_w)

                        }

                    }
                }
            } else {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(KEY, intent.getIntExtra(KEY, 10))
                startActivity(intent)
                finish()
            }
        }

        //Checks for first time
        sharedPreferences = getSharedPreferences(SHAREDPREFRENCE, MODE_PRIVATE)
        if (!sharedPreferences.getBoolean(TAG, false)) {
            showDialog()
        }

        //Apply shrink effect
        b.likeBtn.applyClickShrink()
        b.likeBtn2.applyClickShrink()
        b.dislikeBtn.applyClickShrink()
        b.dislikeBtn2.applyClickShrink()
        b.nextBtn.applyClickShrink()
        b.o1Btn.applyClickShrink()
        b.o2Btn.applyClickShrink()
        b.o3Btn.applyClickShrink()
        b.o4Btn.applyClickShrink()
    }


    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you want to exit ?")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                finish()
            }
        })
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which -> alertDialog.dismiss() }

        alertDialog = builder.create()
        alertDialog.show()
    }

    fun showDialog() {
        dialog.show()
        val sharedPreferencesEditor = sharedPreferences.edit()
        sharedPreferencesEditor.putBoolean(TAG, true)
        sharedPreferencesEditor.commit()
        val handler = Handler()
        handler.postDelayed({ dismissDialog() }, 3000)
    }

    fun dismissDialog() {
        dialog.dismiss()
    }


    fun doLike(view: View) {
        if (view.id == R.id.likeBtn) {
            if (l1C
            ) {
                (view as ImageButton).background =
                    ContextCompat.getDrawable(this, R.drawable.like_btn_bg)
                (view as ImageButton).setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.like
                    )
                )
                l1C = false
            } else {
                (view as ImageButton).background =
                    ContextCompat.getDrawable(this, R.drawable.like_done_bg)
                (view as ImageButton).setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.like_done
                    )
                )

                b.dislikeBtn.background = ContextCompat.getDrawable(this, R.drawable.like_btn_bg)
                b.dislikeBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dislike))
                l1C = true
            }
        } else if (view.id == R.id.likeBtn2) {
            if (l2C
            ) {
                (view as ImageButton).background =
                    ContextCompat.getDrawable(this, R.drawable.like_btn_bg)
                (view as ImageButton).setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.like
                    )
                )
                l2C = false
            } else {
                (view as ImageButton).background =
                    ContextCompat.getDrawable(this, R.drawable.like_done_bg)
                (view as ImageButton).setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.like_done
                    )
                )

                b.dislikeBtn2.background = ContextCompat.getDrawable(this, R.drawable.like_btn_bg)
                b.dislikeBtn2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dislike))
                l2C = true
            }
        }
    }

    fun doDisLike(view: View) {
        if (view.id == R.id.dislikeBtn) {
            if (d1C
            ) {
                (view as ImageButton).background =
                    ContextCompat.getDrawable(this, R.drawable.like_btn_bg)
                (view as ImageButton).setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.dislike
                    )
                )
                d1C = false
            } else {
                (view as ImageButton).background =
                    ContextCompat.getDrawable(this, R.drawable.dislike_done_bg)
                (view as ImageButton).setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.dislike_done
                    )
                )

                b.likeBtn.background = ContextCompat.getDrawable(this, R.drawable.like_btn_bg)
                b.likeBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.like))
                d1C = true
            }
        } else if (view.id == R.id.dislikeBtn2) {
            if (d2C
            ) {
                (view as ImageButton).background =
                    ContextCompat.getDrawable(this, R.drawable.like_btn_bg)
                (view as ImageButton).setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.dislike
                    )
                )
                d2C = false
            } else {
                (view as ImageButton).background =
                    ContextCompat.getDrawable(this, R.drawable.dislike_done_bg)
                (view as ImageButton).setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.dislike_done
                    )
                )

                b.likeBtn2.background = ContextCompat.getDrawable(this, R.drawable.like_btn_bg)
                b.likeBtn2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.like))
                d2C = true
            }
        }
    }

    private fun setupScreen(index: Int) {
        if (index == 10) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
            b.parentLl.visibility = View.INVISIBLE
            b.customToolbar.visibility = View.INVISIBLE
        } else {
            val obj = Gson().fromJson(Constants.jsonData, Datam::class.java)
            val da = obj.data[index - 1]
            this.items = da.total.toInt() + 1
            data = da
            setupIntroLayout(da)

            for (i in da.readList.indices) {
                objList.add(da.readList[i])
                if (i == da.readList.size - 1) {
                    for (p in da.videoList.indices) {
                        objList.add(da.videoList[p])
                        if (p == da.videoList.size - 1) {
                            for (r in da.quizList.indices) {
                                objList.add(da.quizList[r])
                            }
                        }
                    }
                }
            }

        }
    }

    fun setupIntroLayout(data: Data) {
        b.introLayout.visibility = View.VISIBLE
        b.videoPlayerLayout.visibility = View.GONE
        b.readingLayout.visibility = View.GONE
        b.questionLayout.visibility = View.GONE

        Glide.with(this).load(data.image).into(b.introImage)
        b.textView4.setText(data.name)
        b.textView7.setText("${data.reads} Reads • ${data.videos} Videos")
        b.customToolbar.setNumberOfItems(data.total.toInt() + 1)
        b.customToolbar.setSelectedItemIn(0)
    }

    private fun next() {
        stopYT()
        youTubePlayerView = YouTubePlayerView(this)
        b.ytPlayerCard.addView(youTubePlayerView)
        if (selectedItem < items - 1) {
            selectedItem++
            b.customToolbar.setSelectedItemIn(selectedItem)
            setupLayouts()
        } else if (selectedItem == items - 1) {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra(KEY, intent.getIntExtra(TAG, 10))
            startActivity(intent)
        }
    }

    private fun prevous() {
        stopYT()
        youTubePlayerView = YouTubePlayerView(this)
        b.ytPlayerCard.addView(youTubePlayerView)
        if (selectedItem > 0) {
            selectedItem--
            b.customToolbar.setSelectedItemIn(selectedItem)
            setupLayouts()
        } else if (selectedItem == 0) {
            setupIntroLayout(data!!)
        }
    }

    companion object {
        const val heightWidthRatio = 1.508333
        const val SHAREDPREFRENCE = "shared"
        const val TAG = "PRINT"
        const val KEY = "EXTRA"

    }

    private fun stopYT() {
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                youTubePlayer.pause()
                super.onCurrentSecond(youTubePlayer, second)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        youTubePlayerView.release()
    }

    private fun setupLayouts() {

        if (selectedItem > 0) {
            var obj = objList[selectedItem - 1]

            if (obj is Read) {
                b.introLayout.visibility = View.GONE
                b.videoPlayerLayout.visibility = View.GONE
                b.questionLayout.visibility = View.GONE
                b.readingLayout.visibility = View.VISIBLE

                b.textViewToolbar.setText(obj.name)
                b.readTvToolbar.setText("${obj.minRead} min read")
                b.t1Tv.setText(obj.t1)
                Glide.with(this).load(obj.image).into(imageRv)
                b.t2Tv.setText(obj.t2)

            } else if (obj is Video) {
                b.introLayout.visibility = View.GONE
                b.videoPlayerLayout.visibility = View.VISIBLE
                b.questionLayout.visibility = View.GONE
                b.readingLayout.visibility = View.GONE
                vTV.setText(obj.name)
                var videoId = obj.url
                youTubePlayerView.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(videoId, 0f)
                        youTubePlayer.play()
                    }
                })

            } else if (obj is Quiz) {
                b.introLayout.visibility = View.GONE
                b.videoPlayerLayout.visibility = View.GONE
                b.questionLayout.visibility = View.VISIBLE
                b.readingLayout.visibility = View.GONE
                ans = obj.ans

                b.textView8.setText(obj.q)

                b.o1.text = obj.o1
                b.o2.text = obj.o2
                b.o3.text = obj.o3
                b.o4.text = obj.o4
            }
        } else if (selectedItem == 0) {
            setupIntroLayout(data!!)
        }


    }

    override fun onClick(v: View?) {
        selectedId = v!!.id
        when (v!!.id) {
            R.id.o1Btn -> {
                o1Btn.background = ContextCompat.getDrawable(this, R.drawable.question_s)
                o2Btn.background = ContextCompat.getDrawable(this, R.drawable.question_ns)
                o3Btn.background = ContextCompat.getDrawable(this, R.drawable.question_ns)
                o4Btn.background = ContextCompat.getDrawable(this, R.drawable.question_ns)

                o1.setTextColor(Color.BLACK)
                o2.setTextColor(Color.parseColor("#B6B6B6"))
                o3.setTextColor(Color.parseColor("#B6B6B6"))
                o4.setTextColor(Color.parseColor("#B6B6B6"))
            }
            R.id.o2Btn -> {
                o2Btn.background = ContextCompat.getDrawable(this, R.drawable.question_s)
                o1Btn.background = ContextCompat.getDrawable(this, R.drawable.question_ns)
                o3Btn.background = ContextCompat.getDrawable(this, R.drawable.question_ns)
                o4Btn.background = ContextCompat.getDrawable(this, R.drawable.question_ns)

                o2.setTextColor(Color.BLACK)
                o1.setTextColor(Color.parseColor("#B6B6B6"))
                o3.setTextColor(Color.parseColor("#B6B6B6"))
                o4.setTextColor(Color.parseColor("#B6B6B6"))
            }
            R.id.o3Btn -> {
                o3Btn.background = ContextCompat.getDrawable(this, R.drawable.question_s)
                o1Btn.background = ContextCompat.getDrawable(this, R.drawable.question_ns)
                o2Btn.background = ContextCompat.getDrawable(this, R.drawable.question_ns)
                o4Btn.background = ContextCompat.getDrawable(this, R.drawable.question_ns)

                o3.setTextColor(Color.BLACK)
                o1.setTextColor(Color.parseColor("#B6B6B6"))
                o2.setTextColor(Color.parseColor("#B6B6B6"))
                o4.setTextColor(Color.parseColor("#B6B6B6"))
            }
            R.id.o4Btn -> {
                o4Btn.background = ContextCompat.getDrawable(this, R.drawable.question_s)
                o1Btn.background = ContextCompat.getDrawable(this, R.drawable.question_ns)
                o2Btn.background = ContextCompat.getDrawable(this, R.drawable.question_ns)
                o3Btn.background = ContextCompat.getDrawable(this, R.drawable.question_ns)

                o4.setTextColor(Color.BLACK)
                o1.setTextColor(Color.parseColor("#B6B6B6"))
                o2.setTextColor(Color.parseColor("#B6B6B6"))
                o3.setTextColor(Color.parseColor("#B6B6B6"))
            }

        }
    }


}