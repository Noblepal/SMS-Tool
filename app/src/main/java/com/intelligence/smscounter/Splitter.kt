package com.intelligence.smscounter

import android.util.Log

public class Splitter {

    companion object {
        const val sent_to = "sent to "
        fun splitMessage(msg: String): Array<String> {
            if (!msg.contains(sent_to)) return arrayOf(msg)
            var full_name = ""
            var phone = ""
            var splitted: List<String> = msg.split(sent_to)
            val fname = splitted[1].split(" ")[0].trim()
            val sname = splitted[1].split(" ")[1].trim()
            val lname = splitted[1].split(" ")[2].trim()
            val phone1 = splitted[1].split(" ")[3].trim()
            val phone2 = splitted[1].split(" ")[4].trim()

            full_name += "${fname.trim()} "

            if (sname.startsWith("0") || sname.startsWith("+") || lname.startsWith("for")) {
                Log.e("split", "This is not a person's middle name")
            } else {
                full_name += "${sname.trim()} "
            }
            if (lname.startsWith("0") || lname.startsWith("+") || lname.startsWith("for")) {
                Log.e("split", "This is not a person's last name")
            } else {
                full_name += "${lname.trim()} "
            }

            if (sname.startsWith("0")) {
                phone = sname
            } else if (lname.startsWith("0")) {
                phone = lname
            } else if (phone1.startsWith("0")) {
                phone = phone1
            } else if (phone2.startsWith("0")) {
                phone = phone2
            }

            Log.e("split", "Full name: $full_name")
            return arrayOf(full_name, phone)
        }
    }
}