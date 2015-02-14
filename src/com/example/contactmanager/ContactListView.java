/**********************************************************
 * 
 * Class : ContactListView.java
 * Description : This class contains methods and functions for creating the screen which displays all the contacts
 * 				 and provides the following options to the user:
 * 	 1) Open a new contact addition screen by clicking on the button on action bar.
 * 	 2) Click on the contact to see details about the contact from where the user can edit the contact.
 * 
 * 
 *********************************************************/


package com.example.contactmanager;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;



public class ContactListView extends ActionBarActivity {

	MyAdapter adapter;
	
	/**
	 * Overriding the on Create function to call the custom adapter, to display the file
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_list_view);
		//		if (savedInstanceState == null) {
		//			getSupportFragmentManager().beginTransaction()
		//			.add(R.id.container, new PlaceholderFragment()).commit();
		//		}

		final ListView listView = (ListView) findViewById(R.id.contactList);

		ArrayList<ContactManagerBean> listForDisplayInView = new ArrayList<ContactManagerBean>();

		listForDisplayInView = getDataForListView();
		adapter = new MyAdapter(this,listForDisplayInView);
		listView.setAdapter(adapter);
		getActionBar().show();

	}

	/**
	 * Function gets the data from the file
	 */
	public ArrayList<ContactManagerBean> getDataForListView()
	{
		ArrayList<ContactManagerBean> displayBean = new ArrayList<ContactManagerBean>();

		ArrayList<ContactManagerBean> listForDisplay = new ArrayList<ContactManagerBean>();
		
			displayBean = new PersistenceLayer().callRead(this);
			Iterator<ContactManagerBean> it = displayBean.iterator();
			while(it.hasNext())
			{
				ContactManagerBean cb = it.next();
				listForDisplay.add(cb);
			}

		return listForDisplay;
	}
	
	/**
	 * Inflate the menu; this adds items to the action bar if it is present.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_view, menu);
		//		return true;
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Using the function to display the data when the activity resumes.
	 * 
	 */
	@Override
	public void onResume()
	{
		super.onResume();
		final ListView listView = (ListView) findViewById(R.id.contactList);

		ArrayList<ContactManagerBean> listForDisplayInView = new ArrayList<ContactManagerBean>();

		listForDisplayInView = getDataForListView();
		MyAdapter adapter = new MyAdapter(this,listForDisplayInView);
		listView.setAdapter(adapter);
		getActionBar().show();
	}
	/**
	 * This function is used to get the id from the action
	 * bar and then act accordingly.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
	/*	if (id == R.id.action_settings) {
			return true;
		}*/
		if (id == R.id.action_add)
		{
			Intent intent = new Intent(this, AddContact.class);
			startActivity(intent);
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
			View rootView = inflater.inflate(R.layout.fragment_list_view,
					container, false);
			return rootView;
		}

	}
}

/**
 * Creating a custom adapter to display Name,Image and phone Number in custom way.
 * 
 * The XML used for list items is list_item.xml
 */

class MyAdapter extends BaseAdapter 
{
	private Context mContext;
	ArrayList<ContactManagerBean> dataForDisplay;

	public MyAdapter()
	{

	}
	public MyAdapter(Context context, ArrayList<ContactManagerBean> dataForDisplay)
	{
		super();
		mContext = context;
		this.dataForDisplay = dataForDisplay;
	}
	@Override
	public int getCount() 
	{
		return dataForDisplay.size();
	}

	@Override
	public ContactManagerBean getItem(int position) 
	{
		return dataForDisplay.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) 
	{
		final Vibrator vibe = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE) ;
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item,parent, false);
		}
				

		TextView txtViewName = (TextView) convertView.findViewById(R.id.textViewInListViewName);
		TextView txtViewNumber = (TextView) convertView.findViewById(R.id.textViewInListViewPhone);
				
		ContactManagerBean cont = dataForDisplay.get(position);
		if(cont.getLastName().equalsIgnoreCase("empty"))
		{
			txtViewName.setText(cont.getFirstName());
		}
		else
		{
			txtViewName.setText(cont.getFirstName() + " " + cont.getLastName());
		}
		if (cont.getPhoneNumber().equalsIgnoreCase("empty"))
		{
			txtViewNumber.setText("");
		}
		else
		{
			txtViewNumber.setText(cont.getPhoneNumber());
		}
				
		convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				vibe.vibrate(10);
				ContactManagerBean cb = dataForDisplay.get(position);
				Intent intent = new Intent(mContext, EditActivity.class);
				intent.putExtra("Bean", cb);
				intent.putExtra("Position", position);
				mContext.startActivity(intent);
				
			}
		});
		return convertView;
		
	}


}

