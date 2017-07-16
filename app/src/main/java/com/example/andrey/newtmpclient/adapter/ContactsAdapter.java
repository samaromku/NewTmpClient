package com.example.andrey.newtmpclient.adapter;

import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutCompat.LayoutParams;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.entities.ContactOnAddress;
import com.example.andrey.newtmpclient.storage.OnListItemClickListener;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
private List<ContactOnAddress> contacts;
private OnListItemClickListener clickListener;

public ContactsAdapter(List<ContactOnAddress> contacts, OnListItemClickListener clickListener) {
        this.contacts = contacts;
        this.clickListener = clickListener;
        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_item, parent, false);
        return new ViewHolder(v);
        }

@Override
public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(contacts.get(position));
     }

@Override
public int getItemCount() {
        return contacts.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView name;
    TextView post;
    TextView apartments;
    LinearLayout linearPhones;
    LinearLayout linearEmails;


    public ViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.contact_name);
        post = (TextView) itemView.findViewById(R.id.contact_post);
        apartments = (TextView) itemView.findViewById(R.id.contact_apartment);
        linearPhones = (LinearLayout) itemView.findViewById(R.id.linear_phones);
        linearEmails = (LinearLayout) itemView.findViewById(R.id.linear_emails);

        itemView.setOnClickListener(this);
    }

    public void bind(ContactOnAddress contact) {
        if(contact.getName()!=null && !contact.getName().isEmpty()) {
            name.setText("Имя: " + contact.getName());
        }else {
            name.setVisibility(View.GONE);
        }
        if(contact.getPost()!=null && !contact.getPost().isEmpty()){
            post.setText("Должность: " + contact.getPost());
        }else {
            post.setVisibility(View.GONE);
        }
        if(contact.getEmails().size()>0){
            createSimpleText("Email:", linearEmails);
            for(String email : contact.getEmails()){
                if(!email.isEmpty()) {
                    createEmailTextView(email, linearEmails);
                }
            }
        }else {
            linearEmails.setVisibility(View.GONE);
        }
        if(contact.getPhones().size()>0){
            createSimpleText("Тел.:", linearPhones);
            for(String phone : contact.getPhones()){
                if(!phone.isEmpty()){
                    createPhoneTextView(phone, linearPhones);
                }
            }
        }else {
            linearPhones.setVisibility(View.GONE);
        }
        if(contact.getApartments()!=null && !contact.getApartments().isEmpty()){
            System.out.println(contact.getApartments() + " apart from adapter");
            apartments.setText("Квартира: " + contact.getApartments());
        }else {
            apartments.setVisibility(View.GONE);
        }
    }

    private void createPhoneTextView(String phone, LinearLayout view){
        LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        TextView phoneTextView = new TextView(view.getContext());
        phoneTextView.setLayoutParams(lparams);
        phoneTextView.setText(phone + ", ");
        Linkify.addLinks(phoneTextView, Linkify.PHONE_NUMBERS);
        phoneTextView.setLinksClickable(true);
        view.addView(phoneTextView);
    }

    private void createSimpleText(String text, LinearLayout view){
        LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        TextView simpleTextView = new TextView(view.getContext());
        simpleTextView.setLayoutParams(lparams);
        simpleTextView.setText(text);
        view.addView(simpleTextView);
    }

    private void createEmailTextView(String email, LinearLayout view){
        LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        TextView emailTextView = new TextView(view.getContext());
        emailTextView.setLayoutParams(lparams);
        emailTextView.setText(email + ", ");
        Linkify.addLinks(emailTextView, Linkify.EMAIL_ADDRESSES);
        emailTextView.setLinksClickable(true);
        view.addView(emailTextView);
    }

    @Override
    public void onClick(View v) {
        clickListener.onClick(v, getAdapterPosition());
    }
}
}