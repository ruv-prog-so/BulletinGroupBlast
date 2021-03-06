/**
 * Copyright © 2015 Ruben Piatnitsky
 * This program is released under the "MIT license".
 * Please see the file LICENSE in this distribution for
 * license terms.
 *
 * @Author Ruben Piatnitsky
 *
 */

package com.bulletingroupblast.bulletingroupblast.Entities;

import android.content.ContentValues;
import android.util.Log;

import com.bulletingroupblast.bulletingroupblast.DatabaseEntity;
import com.bulletingroupblast.bulletingroupblast.DatabaseHandler;
import com.bulletingroupblast.bulletingroupblast.Entities.Group;
import com.bulletingroupblast.bulletingroupblast.Entities.Organization;
import com.bulletingroupblast.bulletingroupblast.SecurityUtil;

import java.util.Date;
import java.util.ArrayList;
import java.util.Map;

public class User extends DatabaseEntity {
    protected String email;
    protected String password;
    protected String salt;
    protected String firstName;
    protected String lastName;
    protected boolean isActive;
    protected boolean autoLogin;
    protected Date dateCreated;
    protected String avatarFileName;
    protected Integer avatar;

    protected final String TABLE_NAME = "tblUser";
    protected final String[] TABLE_COL_NAMES = {
            "id",
            "email",
            "password",
            "salt",
            "firstName",
            "lastName",
            "isActive",
            "autoLogin",
            "dateCreated",
            "avatarFileName"
    };
    protected final String[] TABLE_COL_TYPES = {
            "INT PRIMARY KEY",
            "VARCHAR(100)",
            "VARCHAR(300)",
            "VARCHAR(300)",
            "VARCHAR(50)",
            "VARCHAR(50)",
            "BOOLEAN DEFAULT 1",
            "BOOLEAN DEFAULT 1",
            "DATETIME",
            "TEXT"
    };

    private final int PASSWORD_MIN_LENGTH = 8;
    private final int PASSWORD_MAX_LENGTH = 64;

    // List of organizations this user belongs to
    private ArrayList<Organization> organizationList = new ArrayList<Organization>();
    // List of Groups that this user belongs to
    private ArrayList<Group> groupList = new ArrayList<Group>();

    // Used to create the table in the database
    protected Map<String, String> tableMap;     // The mapping of columns to their types

    /** User Constructor
     */
    public User() {super();}

    /**@apiNote Default Constructor
     * @param user_email - String
     * @param user_password - String
     * @param fName - String
     * @param lName - String
     */
    public User(String user_email, String user_password, String fName, String lName) {
        super();

        // Set the DB variable values
        this.mTableName = TABLE_NAME;
        this.mTableColNames = TABLE_COL_NAMES;
        this.mTableColTypes = TABLE_COL_TYPES;

        email = user_email;

        if (checkStringValue(fName)) {
            this.firstName = fName;
        }

        if (checkStringValue(lName)) {
            this.lastName = lName;
        }

        this.isActive = true;
        /*TODO: populate avatar image*/
        this.dateCreated = new Date();

        if (!setPassword(user_password)) {
            /*TODO: Cast an exception if the password setting fails*/
        }

    }

    /**@apiNote Constructor
     * @param userId - int
     * @param user_email - String
     * @param user_password - String
     * @param fName - String
     * @param lName - String
     */
    public User(int userId, String user_email, String user_password, String fName, String lName) {
        super();

        // Set the DB variable values
        this.mTableName = TABLE_NAME;
        this.mTableColNames = TABLE_COL_NAMES;
        this.mTableColTypes = TABLE_COL_TYPES;

        email = user_email;

        if (checkStringValue(fName)) {
            this.firstName = fName;
        }

        if (checkStringValue(lName)) {
            this.lastName = lName;
        }

        this.id = userId;
        this.isActive = true;
        /*TODO: populate avatar image*/
        this.dateCreated = new Date();
        this.mTableName = TABLE_NAME; // Set the table name

        if (!setPassword(user_password)) {
            /*TODO: Cast an exception if the password setting fails*/
        }

    }

    /**@apiNote Constructor
     * @param userId - int
     * @param user_email - String
     * @param user_password - String
     * @param fName - String
     * @param lName - String
     */
    public User(int userId, String user_email, String user_password, String fName, String lName, int avatar) {
        super();

        // Set the DB variable values
        this.mTableName = TABLE_NAME;
        this.mTableColNames = TABLE_COL_NAMES;
        this.mTableColTypes = TABLE_COL_TYPES;

        email = user_email;

        if (checkStringValue(fName)) {
            this.firstName = fName;
        }

        if (checkStringValue(lName)) {
            this.lastName = lName;
        }

        this.id = userId;
        this.isActive = true;
        /*TODO: populate avatar image*/
        this.dateCreated = new Date();
        this.avatar = avatar;

        if (!setPassword(user_password)) {
            /*TODO: Cast an exception if the password setting fails*/
        }

    }

    /**@apiNote Creates a user object from an existing user id.
     * @param uId
     */
    public User(int uId) {
        if (uId > 0 ) {
            /*TODO: Search database for this record and load it into an array*/

        }
    }

    /**@apiNote Sets the password by hashing it
     * @param newPassword - String
     * @return boolean - true if success, false otherwise
     */
    public boolean setPassword(String newPassword) {
        boolean success = false;

        if (super.checkStringValue(newPassword)) {
            if (newPassword.length() >= PASSWORD_MIN_LENGTH || newPassword.length() <= PASSWORD_MAX_LENGTH) {
                /*TODO: Hash the password string*/
                this.password = newPassword;
                success = true;
            }
        }

        return success;
    }


    /**@apiNote Adds an organization to the user's organization list
     * @param newOrganization - Organization Class Object
     * @return boolean - true if item added, false otherwise
     */
    public boolean addOrganization(Organization newOrganization) {
        boolean success = false;

        if (newOrganization != null) {
            newOrganization.save();                         // Save the organization to the database
            this.organizationList.add(newOrganization);     // Add the organization to the users list
            success = true;
        }

        return success;
    }

    /** Gets the organization by the index number
     * @param index of the organization in the list
     * @return Organization object
     */
    public Organization getOrganizationByIndex(int index) {

        // Check if any organizations exist
        if (organizationList.size() > 0) {
            // If the index is in range then return it
            if (index >= 0 && index < organizationList.size()) {
                return organizationList.get(index);
            }
        }
        return null;
    }

    /** Get the organization by id
     * @param id of the organization
     * @return organization if exists or null otherwise
     */
    public Organization getOrganizationById(int id) {

        // Check if any organizations exist
        if (organizationList.size() > 0) {
            //Loop through all organizations
            for (int i = 0; i < organizationList.size(); i++) {
                Organization org = organizationList.get(i);
                // Check if the id matches
                if (org.getId() == id) {
                    return org;     // match
                }
            }
        }
        return null;
    }

    /** Gets the list of organizations that the user is part of
     * @return ArrayList of organizations
     */
    public ArrayList<Organization> getOrganizationList() {
        return organizationList;
    }

    /**@apiNote Adds a group to the user's group list
     * @param newGroup - Group Class Object
     * @return boolean - true if item added, false otherwise
     */
    public boolean addGroup(Group newGroup) {
        boolean success = false;

        if (newGroup != null) {
            this.groupList.add(newGroup);
            success = true;
        }

        return success;
    }

    /** Gets First Name field
     * @return First Name - String
     */
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }


    /** Gets First Name field
     * @return First Name - String
     */
    public String getFirstName() {
        return this.firstName;
    }

    /** Sets First Name field
     * @param firstName - String
     * @return null
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /** Gets email field
     * @return email - String
     */
    public String getLastName() {
        return this.lastName;
    }

    /** Sets Last Name field
     * @param lastName - String
     * @return null
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /** Gets the autologin field
     * @return boolean
     */
    public boolean getAutoLogin() {
        return autoLogin;
    }

    /** Sets the autologin field
     * @param autoLogin - boolean
     */
    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    /** Gets email field
     * @return email - String
     */
    public String getEmail() {
        return this.email;
    }

    /** Sets email field
     * @param email - String
     * @return null
     */
    public void setEmail(String email) {
        /*TODO: Send user an email to verify that the email was changed*/
        this.email = email;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    /** Get the avatar file name
     * @return string file name of avatar
     */
    public String getAvatarFileName() {
        return avatarFileName;
    }

    /** Sets the avatar file name
     * @param avatarFileName file name with directory
     */
    public void setAvatarFileName(String avatarFileName) {
        this.avatarFileName = avatarFileName;
    }

    /** Set isActive
     *
     * @param isActive true if user is ative, false otherwise
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /** Generate a value list to save into the database
     * @return ContentValues of fields in TABLE_COL_NAMES
     */
    public ContentValues getInsertFieldValues() {
        /* TABLE COLUMN NAMES
            "id",
            "email",
            "password",
            "salt",
            "firstName",
            "lastName",
            "isActive",
            "autoLogon",
            "dateCreated",
            "avatarFileName"
        */
        ContentValues values = new ContentValues();

        // Set the values
        values.put(TABLE_COL_NAMES[1], this.email);
        values.put(TABLE_COL_NAMES[2], this.password);
        values.put(TABLE_COL_NAMES[3], this.salt);
        values.put(TABLE_COL_NAMES[4], this.firstName);
        values.put(TABLE_COL_NAMES[5], this.lastName);
        values.put(TABLE_COL_NAMES[6], this.isActive);
        values.put(TABLE_COL_NAMES[7], this.autoLogin);
        values.put(TABLE_COL_NAMES[8], this.dateCreated.toString());
        values.put(TABLE_COL_NAMES[9], this.avatarFileName);

        return values;
    }

    /** Get the table name
     * @return string table name
     */
    public String getTableName() {
        return TABLE_NAME;
    }

    /** Get the table column names
     * @return string array
     */
    public String[] getTableColumnNames() {
        return TABLE_COL_NAMES;
    }

    /** Get the table column types
     * @return string array
     */
    public String[] getTableColumnTypes() {
        return TABLE_COL_TYPES;
    }
}
