package com.mirfanrafif.mygithubapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial
import com.mirfanrafif.mygithubapp.R
import com.mirfanrafif.mygithubapp.jobs.ReminderReceiver
import com.mirfanrafif.mygithubapp.preferences.SettingPreferences

class SettingsActivity : AppCompatActivity() {

    private lateinit var reminderReceiver: ReminderReceiver
    private lateinit var preferences: SettingPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        preferences = SettingPreferences(this)
        reminderReceiver = ReminderReceiver()

        val reminder = preferences.getReminder()

        val swPengingat = findViewById<SwitchMaterial>(R.id.sw_pengingat)
        swPengingat.isChecked = reminder
        swPengingat.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                reminderReceiver.setRepeatingAlarm(this, "Hei. Ada yang baru nih. Coba cek deh.")
            }else{
                reminderReceiver.cancelAlarm(this)
            }
            preferences.setReminder(isChecked)
        }

        val btnSettings = findViewById<Button>(R.id.btnLanguage)
        btnSettings.setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
    }

}