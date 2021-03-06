/**
 * Copyright © 2015 Ruben Piatnitsky
 * This program is released under the "MIT license".
 * Please see the file LICENSE in this distribution for
 * license terms.
 */

package com.bulletingroupblast.bulletingroupblast.adapter;


import com.bulletingroupblast.bulletingroupblast.R;
import com.bulletingroupblast.bulletingroupblast.customListItems.*;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/** This class is for a custom navigation pane list items to have navListItem views
 *
 */
public class NavListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NavListItemWithCounter> navListItems;

    public NavListAdapter() {}

    /** Constructor with context and ArrayList of navListItems
     *
     * @param context context of the current activity
     * @param navItems Are the {@link:ArrayList} of {@link:navListItem}
     */
    public NavListAdapter(Context context, ArrayList<NavListItemWithCounter> navItems) {
        this.context = context;
        this.navListItems = navItems;
    }

    /** Gets the context of the the adapter
     *
     * @return context
     */
    public Context getContext() {
        return context;
    }

    /** set the context
     *
     * @param context context to set to
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /** Gets the size of the list of nav items
     *
     * @return int of size of the {@link:ArrayList}
     */
    @Override
    public int getCount() {
        return this.navListItems.size();
    }

    /** Get the whole navigation item list
     *
     * @return ArrayList of navigation items
     */
    public ArrayList<NavListItemWithCounter> getNavListItems() {
        return navListItems;
    }

    /** Sets the navlistItems ArrayList
     *
     * @param navListItems {@link:ArrayList} of {@link:navLinkItem}
     */
    public void setNavListItems(ArrayList<NavListItemWithCounter> navListItems) {
        this.navListItems = navListItems;
    }

    /** Gets the list items position
     *
     * @param position the position of the given item in the list
     * @return long of position
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /** Gets the item at specified position
     *
     * @param position position of list item in ArrayList
     * @return object navListItem
     */
    @Override
    public Object getItem(int position) {
        return this.navListItems.get(position);
    }

    /** Gets the view of the navigation list
     *
     * @param position the position of the list item
     * @param view the view of the list item
     * @param parent the parent of the list item
     * @return A view of the list item
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.nav_list_item_with_count, null);
        }

        // Link to actual objects in layout
        ImageView icon = (ImageView) view.findViewById(R.id.nav_item_icon);
        TextView txtTitle = (TextView) view.findViewById(R.id.nav_item_title);
        TextView txtCount = (TextView) view.findViewById(R.id.nav_item_counter);

        // Get the current list item
        NavListItemWithCounter listItem = navListItems.get(position);

        // Set the values for icon and title
        icon.setImageResource(listItem.getIcon());    // Set the icon
        txtTitle.setText(listItem.getTitle());        // Set the title

        // Set the counter list item visibility
        if(listItem.isCounterVisible()) {     // Check visibility
            txtCount.setText(listItem.getCount());      // Set the text if visible
        } else {
            txtCount.setVisibility(View.GONE);          // Hide the counter otherwise
        }

        // Check if the item is light or dark and set the color
        if (listItem.isTextLight()) {

            // Check if values are empty
            if (listItem.getDarkTextColor() != 0) {
                txtTitle.setTextColor(listItem.getDarkTextColor());
            }
            if (listItem.getLightTextColor() != 0) {
                txtCount.setTextColor(listItem.getLightTextColor());
            }
        }

        return view;
    }

}
