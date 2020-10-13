package com.intelligence.smscounter

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.intelligence.smscounter.Splitter.Companion.splitMessage
import kotlinx.android.synthetic.main.activity_contacts_s_m_s.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*
import kotlin.collections.ArrayList

class ContactsSMSActivity : AppCompatActivity() {

    private val smsList = ArrayList<SMSData>()
    private var senderNameOrPhone: String = "MPESA"
    private var keyword: String = ""
    private lateinit var adapter: MessagesAdapter
    private val tag = "ContactsSMSActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_s_m_s)

        val mContact = mContact("Joseph Ndung'u Noblepal", "0726266668")
        mContact.saveContact(this, mContact)

        adapter = MessagesAdapter(smsList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        doAsync {
            val success = setSmsMessages("", "address LIKE '$senderNameOrPhone'")

            uiThread {
                updateUI(success)
            }
        }

    }

    private fun setSmsMessages(urlString: String, selection: String?): Boolean {
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
                if (cursor.getString(messageID).toLowerCase().contains(keyword.toLowerCase()) && cursor.getString(messageID).toLowerCase().contains(Splitter.sent_to)) {
                    val dateString = cursor.getString(dateID)
                    smsList.add(SMSData(cursor.getString(nameID),
                            Date(dateString.toLong()).toString(),
                            cursor.getString(messageID).trim())
                    )

                    Log.e("setSmsMessages", "NAME: " + splitMessage(cursor.getString(messageID).trim()))

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
        if (success) {
            adapter.notifyDataSetChanged()
        } else {
            Log.e(tag, "updateUI: false")
        }

    }
}