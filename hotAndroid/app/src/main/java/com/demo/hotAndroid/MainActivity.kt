package com.demo.hotAndroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.demo.hotAndroid.ui.main.PostsListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PostsListFragment.newInstance())
                    .commitNow()
        }
    }
}