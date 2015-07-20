/**
 * Copyright © 2015 Ruben Piatnitsky
 * This program is released under the "GNU license".
 * Please see the file COPYING in this distribution for
 * license terms.
 * <p>
 * Created by Ruben Piatnitsky on 7/6/15.
 */

package com.bulletingroupblast.bulletingroupblast;

import com.bulletingroupblast.bulletingroupblast.Organization;
import com.bulletingroupblast.bulletingroupblast.User;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.content.Intent;

import java.util.ArrayList;


public class UserLandingActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.BulletinGroupBlast.BulletinGroupBlast.OrganizationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_landing);

        User testUser;
        Intent intentPrev = getIntent();
        int passedUserId = intentPrev.getIntExtra(MainActivity.USERID_MESSAGE, 0);

        // Test Data created for interface dev
        if (passedUserId == 0) {
            testUser = new User("test.test@gmail.com", "password", "Test", "User");
        } else {
            testUser = new User("John.Doe@gmail.com","password","John", "Doe");
        }

        Organization testOrg1 = new Organization("Portland State University", "A description", testUser);
        Organization testOrg2 = new Organization("Vancouver Lego Guild", "A description", testUser);
        Organization testOrg3 = new Organization("Smashing Car Show", "A description", testUser);
        Organization testOrg4 = new Organization("Biker Gang", "A description", testUser);

        // Creating a list view object that refers to the list view on the page
        ArrayList<String> listItems = new ArrayList<String>();
        listItems.add(testOrg1.getName());
        listItems.add(testOrg2.getName());
        listItems.add(testOrg3.getName());
        listItems.add(testOrg4.getName());


        ListView orgListView = (ListView) findViewById(R.id.lstOrganizations); // ListView reference
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);  // Adapter for the list view which is given the string array

        orgListView.setAdapter(adapter);        // Attach the adapter to the listView
        // Attach an on click event to the list
        orgListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adView, View view, int position, long id) {
                // Show a message
//                Toast.makeText(getBaseContext(), adView.getItemAtPosition(position) + " is selected", Toast.LENGTH_LONG).show();
                onOrgListItemClick(adView, view, position,id);
            }
        });

        TextView textViewToChange = (TextView) findViewById(R.id.lblUserEmail);   // This is a reference to the email text box
        textViewToChange.setText(testUser.getEmail());  // Change the email to what I want

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int itemId = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (itemId == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Organization Listview item click event
     * @param adView
     * @param v
     * @param position
     * @param id
     */
    private void onOrgListItemClick(AdapterView<?> adView, View v, int position, long id) {
        // Move to another activity
        try {
            Intent intent = new Intent(this, com.bulletingroupblast.bulletingroupblast.OrganizationActivity.class);     // Intent is for switching to a different activity
            String message = adView.getItemAtPosition(position).toString();
            intent.putExtra(EXTRA_MESSAGE, message);        // Adds the text value to the intent
            startActivity(intent);
        }
        catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show(); // Show a message in toast
        }
    }

    /**
     * Add a new organization to the user list
     * @param v
     */
    public void onNewOrgBtnClick(View v) {
        Intent intent = new Intent(this, com.bulletingroupblast.bulletingroupblast.CreateOrganizationActivity.class);     // Intent is for switching to a different activity
        startActivity(intent);
    }

    /**
     * When the activity resumes from another activity
     */
    @Override
    public void onResume() {
        super.onResume();  // Call the superclass method

        /*TODO: Refresh the organization list*/
    }

}
