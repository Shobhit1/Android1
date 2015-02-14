/**********************************************************
 * 
 * Class : EditActivity.java
 * Description : This class contains methods and functions to view and then edit the existing contact
 * 
 * 	 1) View the details about the selected contact.
 * 	 2) Enable the screen to edit the details on the screen. 
 * 
 * 
 *********************************************************/
package com.example.contactmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_edit);
		/*if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/

		Toast.makeText(this, "Click on edit button in the action bar to modify", Toast.LENGTH_SHORT).show();

		ContactManagerBean beanToEdit = (ContactManagerBean) getIntent().getSerializableExtra("Bean");

		//		System.out.println("dasdasdsadsadsd----------"+ lcmb.getFirstName()+".........."+lcmb.getPhoneNumber());
		
		

		EditText fname = (EditText) findViewById(R.id.editActFName);
		fname.setText(beanToEdit.getFirstName().toString());
		//		fname.setActivated(false);
		fname.setEnabled(false);
//		fname.setInputType(InputType.TYPE_NULL);
		//				fname.setFocusable(false);

		EditText lname = (EditText) findViewById(R.id.editActLName);
		if (beanToEdit.getLastName().toString().equalsIgnoreCase("empty"))
		{
			lname.setText("");
		}
		else
		{
			lname.setText(beanToEdit.getLastName().toString());
		}
		
		//		lname.setActivated(false);
		lname.setEnabled(false);
//		lname.setInputType(InputType.TYPE_NULL);
		//				lname.setFocusable(false);

		EditText phoneNumberEdit = (EditText) findViewById(R.id.editActPhoneNumber);
		
		if (beanToEdit.getPhoneNumber().toString().equalsIgnoreCase("empty"))
		{
			phoneNumberEdit.setText("");
		}
		else
		{
			phoneNumberEdit.setText(beanToEdit.getPhoneNumber().toString());
		}
		//		phoneNumberEdit.setActivated(false);
		phoneNumberEdit.setEnabled(false);
//		phoneNumberEdit.setInputType(InputType.TYPE_NULL);
		//				phoneNumberEdit.setFocusable(false);

		EditText emailEdit = (EditText) findViewById(R.id.editActEmail);
		if (beanToEdit.getEmail().toString().equalsIgnoreCase("empty"))
		{
			phoneNumberEdit.setText("");
		}
		else
		{
			emailEdit.setText(beanToEdit.getEmail().toString());			
		}
		
		
		//		emailEdit.setActivated(false);
		emailEdit.setEnabled(false);
//		emailEdit.setInputType(InputType.TYPE_NULL);
		//		emailEdit.setFocusable(false);

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}
	
	/**
	 * Function to enable all the text fields.
	 */

	public void enableFields()
	{
//		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		
		EditText fname = (EditText) findViewById(R.id.editActFName);
		EditText lname = (EditText) findViewById(R.id.editActLName);
		EditText phoneNum = (EditText) findViewById(R.id.editActPhoneNumber);
		EditText emailEdit = (EditText) findViewById(R.id.editActEmail);

		fname.setEnabled(true);
//		fname.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
		lname.setEnabled(true);
//		lname.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
		phoneNum.setEnabled(true);
//		phoneNum.setInputType(InputType.TYPE_CLASS_PHONE);
		emailEdit.setEnabled(true);
//		emailEdit.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
//		fname.requestFocus();
		fname.setFocusable(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
	/*	if (id == R.id.action_settings) {
			return true;
		}*/
		/**
		 * Function enables the text fields when clicked on edit button
		 */

		if(id==R.id.action_edit_edit)
		{
			System.out.println("edit");
			EditText fname = (EditText) findViewById(R.id.editActFName);
			
			enableFields();
			fname.requestFocus();

		}
		
		/**
		 * Saves the updated information.
		 */

		if(id==R.id.action_edit_save)
		{
			System.out.println("save");
			int position = (Integer) getIntent().getSerializableExtra("Position");
			PersistenceLayer pl = new PersistenceLayer();
			pl.deleteRecord(position,this);
			ContactManagerBean beanToEdit = new ContactManagerBean();
			beanToEdit.setFirstName(((TextView)findViewById(R.id.editActFName)).getText().toString());
			beanToEdit.setLastName(((TextView)findViewById(R.id.editActLName)).getText().toString());
			beanToEdit.setPhoneNumber(((TextView)findViewById(R.id.editActPhoneNumber)).getText().toString());
			beanToEdit.setEmail(((TextView)findViewById(R.id.editActEmail)).getText().toString());
			String result = new PersistenceLayer().saveText(beanToEdit);
			switch (result) {
			case "empty":
				Toast.makeText(this, "Fileds cannot be empty", Toast.LENGTH_SHORT).show();
				break;
			case "failure":
				Toast.makeText(this, "Could not save contact", Toast.LENGTH_SHORT).show();
				break;
			case "exception":
				Toast.makeText(this, "Some Exception in saving the contact", Toast.LENGTH_SHORT).show();
			default:
				Toast.makeText(this, "Contact Saved Successully", Toast.LENGTH_SHORT).show();
				break;
			}
			clearForm();
			new MyAdapter().notifyDataSetChanged();
			this.finish();

		}
		
		/**
		 * Deletes the user after asking for confirmation from the user.
		 */
		if(id == R.id.action_edit_delete)
		{
			/*int position = (Integer) getIntent().getSerializableExtra("Position");
			PersistenceLayer pl = new PersistenceLayer();
			pl.deleteRecord(position);
			new MyAdapter().notifyDataSetChanged();(getActivity()
			clearForm();
			this.finish();*/
			final Vibrator vibe = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.deleteDialog);
			builder.setPositiveButton(R.string.confirmDelete, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					// FIRE ZE MISSILES!
					//					vibe.vibrate(10);
					delete();
				}
			});
			builder.setNegativeButton(R.string.cancelDelete, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					//					vibe.vibrate(10);
					dialog.cancel();
				}
			});
			builder.create();
			vibe.vibrate(15);
			builder.show();

		}
		return super.onOptionsItemSelected(item);
	}


	/**
	 * Function to delete any record from the file - to be triggered when user clicks on Confirm dialog box
	 */
	public void delete()
	{
		int position = (Integer) getIntent().getSerializableExtra("Position");
		PersistenceLayer pl = new PersistenceLayer();
		pl.deleteRecord(position,this);
		new MyAdapter().notifyDataSetChanged();
		clearForm();
		this.finish();
		Toast.makeText(this,"Contact Deleted", Toast.LENGTH_SHORT).show();
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
			View rootView = inflater.inflate(R.layout.fragment_edit, container,
					false);
			return rootView;
		}
	}
	
	/**
	 * Function to clear the form after the task is finished.
	 */

	public void clearForm()
	{
		EditText editText1 = (EditText)findViewById(R.id.editActFName);
		editText1.setText("");

		EditText editText2 = (EditText)findViewById(R.id.editActLName);
		editText2.setText("");

		EditText editText3= (EditText)findViewById(R.id.editActPhoneNumber);
		editText3.setText("");

		EditText editText4 = (EditText)findViewById(R.id.editActEmail);
		editText4.setText("");
	}
}

/**
 * Class to create a dialog box extending dialog fragment class that has 
 * dialog box implementation which are being overriden here.
 *
 */

class DeleteDialog extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.deleteDialog)
		.setPositiveButton(R.string.confirmDelete, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// FIRE ZE MISSILES!
				new EditActivity().delete();
			}
		})
		.setNegativeButton(R.string.cancelDelete, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		// Create the AlertDialog object and return it
		return builder.create();
	}
}
