package com.android.phpclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class EmployeesListActivity extends Activity {

	ListView listEmployeeDetails;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.employeeslist);
		
		listEmployeeDetails=(ListView)findViewById(R.id.listViewEmployees);
		//Get the employee array from the server access class
		ServerAccess ss=new ServerAccess();
		Employee[] employeesArray= ss.getEmployees(Constants.USER_ID);
		//Set the adapter with our custom view so that the Employee name and his/her address will be shown

		if (employeesArray != null)
		{
			listEmployeeDetails.setAdapter(new EmployeeAdapter(this, R.layout.employeeitem, employeesArray));
		}

		final Button btnVoltar = (Button) findViewById(R.id.btnVoltar);
		btnVoltar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent it = new Intent(EmployeesListActivity.this, principal.class);
				EmployeesListActivity.this.startActivity(it);
				finish();
			}
		});

	}

	private class EmployeeAdapter extends ArrayAdapter<Employee>
	{
		//Array to have the objects
		private Employee[] array;
		
		public EmployeeAdapter(Context context, int textViewResourceId,
				Employee[] objects) {
			super(context, textViewResourceId, objects);
			array=objects;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//Set the view for each item in the list view
			View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.employeeitem, null);
            }
            //Get the Textviews from the row view and set the appropriate values for them
            TextView labelName=(TextView) v.findViewById(R.id.labelName);
            TextView labelAddress=(TextView)v.findViewById(R.id.labelAddress);
			TextView labelID=(TextView)v.findViewById(R.id.lblID);
            labelName.setText(array[position].name);
            labelAddress.setText(array[position].address);
			labelID.setText(array[position].ID + " - ");

			return v;
		}
	}
}
