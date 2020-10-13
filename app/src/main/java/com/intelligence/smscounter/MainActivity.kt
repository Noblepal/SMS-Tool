package com.intelligence.smscounter

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val requestSMSPermission: Int = 2
    private var senderNameOrPhone: String = ""
    private var keyword: String = ""
    private val smsList = ArrayList<SMSData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        checkPermissions()

        btn_all_sms.setOnClickListener {
            setSmsMessages("", null)
        }

        btn_single_num_sms.setOnClickListener {
            keyword = edt_keyword.text.toString()
            senderNameOrPhone = edt_sender.text.toString()
            val success = setSmsMessages("", "address LIKE '$senderNameOrPhone'")
            updateUI(success)
        }
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_SMS), requestSMSPermission)
        } else {
            doAsync {
                val success = setSmsMessages("", null)

                uiThread {
                    updateUI(success)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == requestSMSPermission)
            doAsync {
                val success = setSmsMessages("", null)

                uiThread {
                    updateUI(success)
                }
            }
    }

    private fun setSmsMessages(urlString: String, selection: String?): Boolean {
        showProgressBar()
        smsList.clear()
        val isSuccessful: Boolean

        val cursor = contentResolver.query(
                Uri.parse("content://sms/$urlString"),
                null,
                selection,
                null,
                null
        )

        if (cursor!!.moveToFirst()) {
            val nameID = cursor.getColumnIndex("address")
            val messageID = cursor.getColumnIndex("body")
            val dateID = cursor.getColumnIndex("date")

            do {
                if (cursor.getString(messageID).toLowerCase().contains(keyword.toLowerCase())) {
                    val dateString = cursor.getString(dateID)
                    smsList.add(SMSData(cursor.getString(nameID),
                            Date(dateString.toLong()).toString(),
                            cursor.getString(messageID).trim()))
                }
            } while (cursor.moveToNext())
            isSuccessful = true
        } else {
            isSuccessful = false
        }

        cursor.close()

        return isSuccessful
    }

    private fun updateUI(success: Boolean) {
        hideProgresBar()
        if (success) {
            val adapter = ListAdapter(this, smsList)
            tv_count.text = smsList.size.toString()
            listview.adapter = adapter
        } else {
            tv_count.text = "Error!"
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = MenuInflater(this)
        inflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sms_contacts -> {
                startActivity(Intent(this, ContactsSMSActivity::class.java))
            }
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showProgressBar() {
        progress_loading.visibility = View.VISIBLE
        tv_count.visibility = View.INVISIBLE
    }

    private fun hideProgresBar() {
        progress_loading.visibility = View.GONE
        tv_count.visibility = View.VISIBLE
    }

}
