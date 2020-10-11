package ru.trinitydigital.youtube.data.ui.main

import ru.trinitydigital.youtube.data.LifeCycle

interface MainContract {

    interface View {
        fun showMessage(text: String)
    }

    interface Presenter : LifeCycle<View> {
        fun loadVideos()

    }
}