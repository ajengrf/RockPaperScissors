package com.example.rockpaperscissors.slider

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.rockpaperscissors.R
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment

class IntroSlider : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpSliderFragment()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        if (currentFragment is OnFinishNavigateListener) {
            currentFragment.onFinishNavigateListener()
        }
    }

    private fun setUpSliderFragment() {
        isSkipButtonEnabled = false
        buildSliderFragment(
            getString(R.string.text_slider_title),
            getString(R.string.text_string_description_1)
        )
        buildSliderFragment(
            getString(R.string.text_slider_title),
            getString(R.string.text_string_description_2)
        )

    }

    private fun buildSliderFragment(textTitle: String, textDescription: String) {
        addSlide(
            AppIntroFragment.createInstance(
                title = textTitle,
                description = textDescription,
                titleColorRes = R.color.white,
                descriptionColorRes = R.color.white,
                imageDrawable = R.drawable.ic_splash_screen,
                backgroundColorRes = R.color.purple_200
            )
        )
    }
}

interface OnFinishNavigateListener {
    fun onFinishNavigateListener()
}