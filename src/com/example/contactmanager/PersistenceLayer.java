/**********************************************************
 * 
 * Class : Persistence Layer.java
 * Description : This class does all the FILE IO operations.
 * 				Main functions include
 * 1) Saving the file
 * 2) Reading the file
 * 3) Deleting the record from the file
 * 				
 * 
 *********************************************************/

package com.example.contactmanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Environment;
import android.widget.Toast;

public class PersistenceLayer {


	//	private List<ContactManagerBean> bean;	
	private ArrayList<ContactManagerBean> beanList = new ArrayList<ContactManagerBean>();

	String fileName = "Records.txt";	//setting the name of the file as Record.txt
	String path = Environment.getExternalStorageDirectory().getAbsolutePath();


	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() 
	{
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	/* Checks if external storage is available to at least read */
	public boolean isExternalStorageReadable() 
	{
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state) ||
				Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * Performs save function by taking object of bean class
	 * and then checking whether the app has permission to write on the 
	 * SD card and also if the SD card is mounted or not
	 * 
	 * It also performs a validation that a contact cannot be saved when there are empty entries.
	 * 
	 */

	public String saveText(ContactManagerBean bean)
	{
		try
		{

			if((isExternalStorageReadable()) && (isExternalStorageWritable()))
			{
				File myFile = new File(path, fileName);
				if(!myFile.exists())
				{
					myFile.createNewFile();
				}
				FileWriter fw = new FileWriter(myFile.getAbsoluteFile(),true);
				BufferedWriter bw = new BufferedWriter(fw);				
				String fname = bean.getFirstName();
				String lastName = bean.getLastName();
				String phoneNumber = bean.getPhoneNumber();
				String email = bean.getEmail();

				if(fname.equalsIgnoreCase(""))
				{
					bw.close();
					fw.close();
					return "empty";

				}
				if (lastName.equalsIgnoreCase(""))
				{
					lastName = "empty";
				}
				if(phoneNumber.equalsIgnoreCase(""))
				{
					phoneNumber = "empty";
				}
				if (email.equalsIgnoreCase(""))
				{
					email = "empty";
				}
				bw.write(fname + "|" + lastName +"|" + phoneNumber + "|" + email);
				bw.newLine();
				bw.close();
				fw.close();

				return "success";
			}
			else
			{
				return "failure";
			}

		}
		catch(Exception e)
		{
			return "exception";
		}
	}
/**
 * Reads the file
 * using delimeter
 * @param Activity
 */
	public ArrayList<ContactManagerBean> callRead(Activity act)
	{
		try {
			File myFile = new File(path, fileName);
			FileReader fr = new FileReader(myFile);
			BufferedReader br = new BufferedReader(fr);
			String s;
			//reading the file by the help of the delimiter that we push during save

			while ((s = br.readLine())!= null)
			{
				String[] delimeter = s.split("\\|");
				ContactManagerBean beanRead = new ContactManagerBean(delimeter[0],delimeter[1],delimeter[2],delimeter[3]);
				beanList.add(beanRead);

			}

			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(act, "Could not read the file. Please add a contact", Toast.LENGTH_SHORT).show();
		}

		return beanList;

	}
	
	/**
	 * Function to delete the contact
	 * @param rowIndex
	 * @param act
	 * @return An arraylist containing contact manager beans
	 */

	public ArrayList<ContactManagerBean> deleteRecord(int rowIndex, Activity act)
	{
		ArrayList<ContactManagerBean> deleteList = new ArrayList<ContactManagerBean>();
		try 
		{
			deleteList = callRead(act);
			deleteList.remove(rowIndex);
			File myFile = new File(path, fileName);
			PrintWriter pw = new PrintWriter(myFile);
			pw.append("");
			pw.flush();
			pw.close();

			for(ContactManagerBean b: deleteList)
			{
				saveText(b);
			}

		} catch (Exception e) {
			Toast.makeText(act, "Contact was not deleted",Toast.LENGTH_SHORT).show(); 
		}
		return deleteList;
	}
}
