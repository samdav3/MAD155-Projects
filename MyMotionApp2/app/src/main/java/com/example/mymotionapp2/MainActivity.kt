package com.example.mymotionapp2

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var magicBtn: Button
    lateinit var imgView: ImageView
    lateinit var undoBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        magicBtn = findViewById(R.id.magic)
        imgView = findViewById(R.id.imageView)
        undoBtn = findViewById(R.id.undo)

        magicBtn.setOnClickListener{
            magicBtnFunction()
        }

        undoBtn.setOnClickListener {
            undoBtnFunction()
        }

    }

    private fun undoBtnFunction() {
        val containerScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)
        val containerScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(imgView, containerScaleX, containerScaleY)

        val animator2 = ObjectAnimator.ofArgb(imgView.parent, "backgroundColor", Color.BLACK, Color.WHITE)

        val set = AnimatorSet()
        set.playTogether(animator, animator2)

        animator.disableViewDuringAnimation(magicBtn)
        animator2.disableViewDuringAnimation(magicBtn)
        set.start()
    }

    private fun magicBtnFunction() {

        val containerScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
        val containerScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(imgView, containerScaleX, containerScaleY)

        val animator2 = ObjectAnimator.ofArgb(imgView.parent, "backgroundColor", Color.WHITE, Color.BLACK)

        val set = AnimatorSet()
        set.playTogether(animator, animator2)

        animator.disableViewDuringAnimation(magicBtn)
        animator2.disableViewDuringAnimation(magicBtn)
        set.start()
    }



    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {
        addListener(object: AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator) {
                view.isEnabled = false
            }
            override fun onAnimationEnd(animation: Animator) {
                view.isEnabled = true
            }
        })
    }
}