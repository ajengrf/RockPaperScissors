package com.example.rockpaperscissors.slider

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.slider.inputname.InputNameFragment
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
            getString(R.string.text_string_description_1),
            R.drawable.ic_vs_player
        )
        buildSliderFragment(
            getString(R.string.text_slider_title),
            getString(R.string.text_string_description_2),
            R.drawable.ic_vs_computer
        )
        addSlide(InputNameFragment())

    }

    private fun buildSliderFragment(textTitle: String, textDescription: String, image: Int) {
        addSlide(
            AppIntroFragment.createInstance(
                title = textTitle,
                description = textDescription,
                titleColorRes = R.color.white,
                descriptionColorRes = R.color.white,
                imageDrawable = image,
                backgroundColorRes = R.color.purple_200
            )
        )
    }
}

interface OnFinishNavigateListener {
    fun onFinishNavigateListener()
}