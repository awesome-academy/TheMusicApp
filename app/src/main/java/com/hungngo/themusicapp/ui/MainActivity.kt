package com.hungngo.themusicapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.hungngo.themusicapp.R
import com.hungngo.themusicapp.base.BaseActivity
import com.hungngo.themusicapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding?.apply {
            setupWithNavController(bottomNavigationView, navController)
        }
    }

    companion object {
        fun getIntent(startActivity: Activity) = Intent(startActivity, MainActivity::class.java)
    }
}
