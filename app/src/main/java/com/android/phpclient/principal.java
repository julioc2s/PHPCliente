package com.android.phpclient;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class principal extends Activity {



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        final TextView lblID = (TextView) findViewById(R.id.lblID);
        final TextView lblNome = (TextView) findViewById(R.id.lblNome);

        ServerAccess ss=new ServerAccess();
        String res =ss.getUserINFO(String.valueOf(Constants.USER_ID));
        String array[] = new String[10];
        array = res.split("-");
        lblID.setText(array[0]);
        lblNome.setText(array[1]);

        //Get the username ans password text boxes 
        final Button btnListar = (Button) findViewById(R.id.btnListar);
        btnListar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent employeeListActivity=new Intent(getApplicationContext(),EmployeesListActivity.class);
                startActivity(employeeListActivity);
            }
        });
        final Button btnAdicionar = (Button) findViewById(R.id.btnAdicionar);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(principal.this, adicionar.class);
                principal.this.startActivity(it);
                finish();
            }
        });
        final Button btnExcluir = (Button) findViewById(R.id.btnExcluir);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(principal.this, excluir.class);
                principal.this.startActivity(it);
                finish();
            }
        });

    }


}