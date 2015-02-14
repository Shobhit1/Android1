/**********************************************************
 * 
 * Class : AddContact.java
 * Description : This class contains methods and functions to add the contact to the text file
 * 				 and provides the following options to the user:
 * 	 1) Add a new contact by clicking on the button on action bar.
 * 
 * 	 2) To enable user to fill all the details and then press save on the top of the screen (action bar). 
 * 
 * 
 *********************************************************/

package com.example.contactmanager;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddContact extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_add_contact);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contact, menu);
		//			return true;
		return super.onCreateOptionsMenu(menu);

	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		/*if (id == R.id.action_settings)
		{
			return true;
		}*/
		/**
		 * This is called when the user press Save from the acion bar.
		 * takes edit text from the view and then passes the data to the contact manager bean object
		 * which is then saved using function in the persistence layer.
		 */
		if (id==R.id.action_save)
		{
			ContactManagerBean beanToSave = new ContactManagerBean();
			beanToSave.setFirstName(((TextView)findViewById(R.id.editFName)).getText().toString());
			beanToSave.setLastName(((TextView)findViewById(R.id.editLName)).getText().toString());
			beanToSave.setPhoneNumber(((TextView)findViewById(R.id.editPhoneNumber)).getText().toString());
			beanToSave.setEmail(((TextView)findViewById(R.id.editEmail)).getText().toString());
			String result = new PersistenceLayer().saveText(beanToSave);
			switch (result) {
			case "empty":
				Toast.makeText(this, "First Name cannot be empty", Toast.LENGTH_SHORT).show();
				break;
			case "failure":
				Toast.makeText(this, "Could not save contact. Please try Again", Toast.LENGTH_SHORT).show();
				break;
			case "exception":
				Toast.makeText(this, "Could not save contact. Please try Again.", Toast.LENGTH_SHORT).show();
			default:
				Toast.makeText(this, "Contact Saved Successully", Toast.LENGTH_SHORT).show();
				this.finish();
				break;
			}
			clearForm();
			
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_add_contact,
					container, false);
			return rootView;
		}
	}

	/**
	 * Function to clear the form after the task is completed.
	 */
	public void clearForm()
	{
		EditText editText1 = (EditText)findViewById(R.id.editFName);
		editText1.setText("");

		EditText editText2 = (EditText)findViewById(R.id.editLName);
		editText2.setText("");

		EditText editText3= (EditText)findViewById(R.id.editPhoneNumber);
		editText3.setText("");

		EditText editText4 = (EditText)findViewById(R.id.editEmail);
		editText4.setText("");
	}

}
