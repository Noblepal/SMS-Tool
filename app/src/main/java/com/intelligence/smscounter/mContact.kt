package com.intelligence.smscounter

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.text.TextUtils
import android.util.Log

public class mContact(var name: String, var phone: String) {
    companion object {
        public fun createContact(csvData: String) {
            //name: joseph mwangi kihara , phone: 0748915415

            val name = csvData.split(",")[0]
            val phone = csvData.split(",")[1]

            val fname = try {
                name.split(" ")[0].trim()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val sname = try {
                name.split(" ")[1].trim()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val lname = try {
                name.split(" ")[2].trim()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (!TextUtils.isEmpty(fname.toString())) {
                Log.e("mContact", "No first name")
            } else if (!TextUtils.isEmpty(sname.toString())) {
                Log.e("mContact", "No surname name")
            } else if (!TextUtils.isEmpty(lname.toString())) {
                Log.e("mContact", "No last name")
            }
        }
    }

    fun saveContact(c: Context, contact: mContact) {
        val intent = Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI).apply {
            type = ContactsContract.RawContacts.CONTENT_TYPE
            putExtra(ContactsContract.Intents.Insert.NAME,
                    contact.name)
            putExtra(ContactsContract.Intents.Insert.PHONE,
                    contact.phone)
            putExtra(ContactsContract.Intents.Insert.PHONE_TYPE,
                    ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
        }
        c.startActivity(intent)
    }
}