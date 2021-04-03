package com.mirfanrafif.mygithubapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.switchmaterial.SwitchMaterial
import com.mirfanrafif.mygithubapp.R
import com.mirfanrafif.mygithubapp.jobs.AlarmReceiver
import com.mirfanrafif.mygithubapp.preferences.SettingPreferences

class SettingsActivity : AppCompatActivity() {

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var preferences: SettingPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        preferences = SettingPreferences(this)
        alarmReceiver = AlarmReceiver()

        val reminder = preferences.getReminder()

        val swPengingat = findViewById<SwitchMaterial>(R.id.sw_pengingat)
        swPengingat.isChecked = reminder
        swPengingat.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                alarmReceiver.setRepeatingAlarm(this, "Hei. Ada yang baru nih. Coba cek deh.")
            }else{
                alarmReceiver.cancelAlarm(this)
            }
            preferences.setReminder(isChecked)
        }
    }

}