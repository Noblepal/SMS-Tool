package com.intelligence.smscounter.model

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log

data class Contact(val contactName: String?, var phone: String, var isSaved: Boolean?) {
    companion object

    fun saveContact(activity: Activity, contact: Contact) {

        /*// Check the Group is available or not
        // Check the Group is available or not
        var groupCursor: Cursor? = null
        val groupProjection = arrayOf(ContactsContract.Groups._ID, ContactsContract.Groups.TITLE)
        groupCursor = activity.contentResolver.query(ContactsContract.Groups.CONTENT_URI, groupProjection, ContactsContract.Groups.TITLE + "=?", arrayOf<String>("FROM M-PESA"), ContactsContract.Groups.TITLE + " ASC")
        if (groupCursor != null) {
            Log.d("saveContact", "** " + groupCursor.getCount())
        }

        if (groupCursor!!.getCount() > 0) {
            Log.e("saveContact", "Group is already available")
        } else {
            Log.e("saveContact", "Group not vailable")

            //Here we create a new Group
            try {
                var groupValues: ContentValues? = null
                val cr: ContentResolver = activity.getContentResolver()
                groupValues = ContentValues()
                groupValues.put(ContactsContract.Groups.TITLE, "FROM M-PESA")
                cr.insert(ContactsContract.Groups.CONTENT_URI, groupValues)
                Log.e("saveContact", "Group Creation Finished")
            } catch (e: java.lang.Exception) {
                Log.e("saveContact :", "" + e.message)
            } finally {
                groupCursor.close()
                groupCursor = null
            }
            Log.e("saveContact", "Group Creation Success")
        }


        var groupID: String? = null
        var getGroupIDCursor: Cursor? = null
        getGroupIDCursor = activity.contentResolver.query(ContactsContract.Groups.CONTENT_URI, groupProjection, ContactsContract.Groups.TITLE + "=?", arrayOf<String>("FROM M-PESA"), null)
        Log.e("saveContact", "** " + getGroupIDCursor!!.count)
        getGroupIDCursor.moveToFirst()
        groupID = getGroupIDCursor.getString(getGroupIDCursor.getColumnIndex("_id"))
        Log.e("saveContact", "** $groupID")

        getGroupIDCursor.close()
        getGroupIDCursor = null*/

        val intent = Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI).apply {
            type = ContactsContract.RawContacts.CONTENT_TYPE
            putExtra(ContactsContract.Intents.Insert.NAME,
                    contact.contactName)
            putExtra(ContactsContract.Intents.Insert.PHONE,
                    contact.phone)/*
            putExtra(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID,
                    groupID)*/
            putExtra(ContactsContract.Intents.Insert.PHONE_TYPE,
                    ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
            putExtra(ContactsContract.Intents.Insert.NOTES, "Saved from M-PESA")
        }
        activity.startActivity(intent)
    }
}