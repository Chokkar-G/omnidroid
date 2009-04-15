package edu.nyu.cs.omnidroid.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import edu.nyu.cs.omnidroid.R;
import edu.nyu.cs.omnidroid.util.UGParser;

/**
 * Overview is the main UI Launcher for the OmniDroid Application. It presents all the current
 * OmniHandlers as well as a way to add/delete/edit them.
 * 
 */
public class Overview extends Activity implements OnClickListener {
  // Menu options
  private static final int MENU_ADD = 0;
  private static final int MENU_EDIT = 1;
  private static final int MENU_DELETE = 2;
  private static final int MENU_SETTINGS = 3;
  private static final int MENU_HELP = 4;
  private static final int MENU_TESTS = 5;

  // User Config Parser
  private static UGParser ug;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    // Create our Activity
    super.onCreate(savedInstanceState);

    // Get a list of our current OmniHandlers
    ug = new UGParser(getApplicationContext());
    ArrayList<View> rowList = new ArrayList<View>();
    ArrayList<HashMap<String, String>> userConfigRecords = ug.readRecords();
    Iterator<HashMap<String, String>> i = userConfigRecords.iterator();

    // Add current OmniHandlers to our list
    while (i.hasNext()) {
      HashMap<String, String> HM1 = i.next();

      // Build our button
      Button button = new Button(this);
      button.setText(HM1.get(UGParser.KEY_InstanceName));
      button.setCursorVisible(true);

      // Build our checkbox
      Log.i(this.getLocalClassName().toString(), "Adding a checkbox");
      CheckBox checkbox = new CheckBox(this);
      checkbox.setGravity(Gravity.CENTER);
      checkbox.setClickable(true);
      if (HM1.get(UGParser.KEY_EnableInstance).equalsIgnoreCase("True")) {
        Log.d(this.getLocalClassName(), "Enabled=true");
        checkbox.setEnabled(true);
        checkbox.setChecked(true);
      } else {
        Log.d(this.getLocalClassName(), "Enabled=false");
        checkbox.setEnabled(false);
        checkbox.setChecked(false);
      }
      // TODO: Attach a onClick listener

      // Add a context menu for the row
      registerForContextMenu(button);

      // Build our table row
      TableRow row = new TableRow(this);
      row.addView(button);
      row.addView(checkbox);
      rowList.add(row);
    }

    // Build our OmniHandler display table
    TableLayout table_layout = new TableLayout(this);
    table_layout.setColumnStretchable(0, true);
    for (View rows : rowList) {
      rows.setPadding(2, 2, 2, 2);
      table_layout.addView(rows);
    }

    // Add our table to a scrollpane
    ScrollView scrollPane = new ScrollView(this);
    scrollPane.addView(table_layout);
    setContentView(scrollPane);

  }

  // Create a context menu options
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    menu.add(0, MENU_EDIT, 0, R.string.edit);
    menu.add(0, MENU_DELETE, 0, R.string.del);
  }

  /* Context Menu Actions */
  public boolean onContextItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case MENU_EDIT:
      editHandler(item);
      return true;
    case MENU_DELETE:
      deleteHandler(item);
      return true;
    default:
      return super.onContextItemSelected(item);
    }
  }

  /**
   * @param item
   *          of the menu item
   */
  private void deleteHandler(MenuItem item) {
    // TODO (acase): Delete confirmation dialog
    Toast.makeText(this.getBaseContext(), "Deleting " + item.getIntent() + "OmniHandler", 5).show();
    // TODO (acase): Delete the OmniHandler
    // Button selected = (Button) view;
    // ug.deleteRecord(selected.getText());
  }

  /**
   * @param item
   *          of the menu item
   */
  private void editHandler(MenuItem item) {
    // TODO (acase): Call next activity
    Toast.makeText(this.getBaseContext(), "Edit OmniHandler Selected", 5).show();
  }

  /**
   * Creates the options menu items
   * @param menu - the options menu to create
   */
  public boolean onCreateOptionsMenu(Menu menu) {
    menu.add(0, MENU_ADD, 0, R.string.add).setIcon(android.R.drawable.ic_menu_add);
    menu.add(0, MENU_SETTINGS, 0, R.string.settings)
        .setIcon(android.R.drawable.ic_menu_preferences);
    menu.add(0, MENU_HELP, 0, R.string.help).setIcon(android.R.drawable.ic_menu_help);
    menu.add(0, MENU_TESTS, 0, R.string.tests).setIcon(android.R.drawable.ic_menu_manage);
    ;
    return true;
  }

  /**
   * Handles menu item selections
   */
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case MENU_ADD:
      AddOmniHandler();
      return true;
    case MENU_SETTINGS:
      Settings();
      return true;
    case MENU_HELP:
      Help();
      return true;
    case MENU_TESTS:
      Intent i = new Intent();
      i.setClass(this.getApplicationContext(), edu.nyu.cs.omnidroid.tests.TestApp.class);
      startActivity(i);
      return true;
    }
    return false;
  }

  /**
   * Add a new OmniHandler to OmniDroid
   */
  private void AddOmniHandler() {
    Intent i = new Intent();
    i.setClass(this.getApplicationContext(), edu.nyu.cs.omnidroid.ui.EventCatcher.class);
    startActivity(i);
  }

  /**
   * Call our Settings Activity
   */
  private void Settings() {
    Intent i = new Intent();
    i.setClass(this.getApplicationContext(), edu.nyu.cs.omnidroid.ui.Settings.class);
    startActivity(i);
  }

  /**
   * Call our Help dialog
   */
  private void Help() {
    // TODO (acase): Create a help dialog for this activity
  }

  /*
   * (non-Javadoc)
   * Add OmniHandler to OmniDroid if appropriate
   *  
   * @see android.view.View.OnClickListener#onClick(android.view.View)
   */
  public void onClick(View v) {
    // TODO (acase): Handle "Edit", "Delete" and "Enable/Disable"

    // TODO (acase): Call next activity
    Toast.makeText(this.getBaseContext(), "Edit OmniHandler Selected", 5).show();
/*
    // Get data from our user
    String iName = instanceName.getText().toString();
    String aData = appData.getText().toString();

    // Add OmniHandler to OmniDroid
    if (iName.length() > 0 && aData.length() > 0) {

      // Add OmniHandler to the CP
      ContentValues values = new ContentValues();
      values.put("i_name", iName);
      values.put("a_data", aData);
      Uri uri = getContentResolver().insert(
          Uri.parse("content://edu.nyu.cs.omnidroid.core.maincp/CP"), values);

      // Add OmniHandler to the UGConfig
      UGParser ug = new UGParser(getApplicationContext());
      HashMap<String, String> HM = new HashMap<String, String>();
      HM.put(UGParser.KEY_InstanceName, iName);
      HM.put(UGParser.KEY_EventName, eventName);
      HM.put(UGParser.KEY_EventApp, eventApp);
      HM.put(UGParser.KEY_ActionName, throwerName);
      HM.put(UGParser.KEY_ActionApp, throwerApp);
      HM.put(UGParser.KEY_ActionData, uri.toString());
      HM.put(UGParser.KEY_EnableInstance, "True");
      HM.put(UGParser.KEY_ActionData, uri.toString());
      // TODO: get these from the user
      HM.put("FilterType", "S_PhoneNum");
      HM.put("FilterData", "212-555-1234");
      ug.writeRecord(HM);

      // Go back to our start page
      Intent i = new Intent();
      i.setClass(this.getApplicationContext(), edu.nyu.cs.omnidroid.ui.Overview.class);
      startActivity(i);
      finish();
    } else {
      Toast.makeText(getBaseContext(), "Please enter both an ", Toast.LENGTH_SHORT).show();
    }
*/
  }
}