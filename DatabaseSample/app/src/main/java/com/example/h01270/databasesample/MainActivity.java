package com.example.h01270.databasesample;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.h01270.databasesample.data.Contact;
import com.example.h01270.databasesample.db.ContactDatasource;

public class MainActivity extends ListActivity {
    private ContactDatasource datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new ContactDatasource(this);
        datasource.open();

        List<Contact> values = datasource.getAllContacts();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    /**
     * Called from onClick attribute - activity_main.xml
     *
     * @param view
     */
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Contact> adapter = (ArrayAdapter<Contact>) getListAdapter();
        Contact contact = null;
        switch (view.getId()) {
            case R.id.add:
                String rowDesc = "Contact " + getListAdapter().getCount();
                contact = datasource.createContact(rowDesc);
                adapter.add(contact);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    contact = (Contact) getListAdapter().getItem(getListAdapter().getCount()-1);
                    datasource.deleteContact(contact);
                    adapter.remove(contact);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}
