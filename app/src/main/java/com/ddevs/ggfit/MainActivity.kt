package com.ddevs.ggfit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment:NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        NavigationUI.setupWithNavController(
                bottomNavigationView,
                navHostFragment.navController
            )
    }
        /*val historyClient:HistoryClient= Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this)!!)
        val textView:TextView=findViewById(R.id.data)
        historyClient.readDailyTotal(DataType.TYPE_MOVE_MINUTES).addOnCompleteListener { result->
            val ans=result.result?.dataPoints?.firstOrNull()?.getValue(Field.FIELD_STEPS)?.asInt() ?: 0
            textView.text = ans.toString()
        }*/
    }
