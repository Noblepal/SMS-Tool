package com.intelligence.smscounter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.intelligence.smscounter.R;
import com.intelligence.smscounter.model.Contact;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    List<Contact> list;
    Context context;
    public ContactAddCallback contactAddCallback;

    public ContactsAdapter(List<Contact> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public interface ContactAddCallback {
        void onContactAddClicked(Contact c);
    }

    public void addContactClickCallback(ContactAddCallback contactAddCallback) {
        this.contactAddCallback = contactAddCallback;
    }

    @NonNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int position) {
        Contact s = list.get(position);
        holder.tv_contact_name.setText(s.getContactName());
        holder.tv_contact_phone.setText(!s.getPhone().equals("") ? s.getPhone() : "~");

        if (s.isSaved()) {
            holder.btn_add_contact.setImageResource(R.drawable.ic_saved);
        } else {
            holder.btn_add_contact.setImageResource(R.drawable.ic_add_contact);
            holder.btn_add_contact.setOnClickListener(v -> {
                contactAddCallback.onContactAddClicked(s);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_contact_name, tv_contact_phone;
        ImageView btn_add_contact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_contact_name = itemView.findViewById(R.id.tv_contact_name);
            tv_contact_phone = itemView.findViewById(R.id.tv_contact_phone);
            btn_add_contact = itemView.findViewById(R.id.btn_add_contact);
        }
    }
}
