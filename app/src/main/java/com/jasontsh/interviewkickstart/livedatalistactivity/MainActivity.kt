package com.jasontsh.interviewkickstart.livedatalistactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val manager: FragmentManager = supportFragmentManager
        manager.commit {
            add(R.id.list_frame, BetFragment())
        }
        manager.commit {
            add(R.id.result_frame, ResultFragment())
        }
    }
}