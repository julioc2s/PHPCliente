package com.android.phpclient;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class adicionar extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar);
        //Get the username ans password text boxes 
        setTitle("Adição de novo empregado");
        final Button btnVoltar2 = (Button) findViewById(R.id.btnVoltar2);

        final EditText edtUsername = (EditText) findViewById(R.id.edtUsername);
        final EditText edtPassword = (EditText) findViewById(R.id.edtPassword);
        final EditText edtName = (EditText) findViewById(R.id.edtName);
        final EditText edtAddress = (EditText) findViewById(R.id.edtAddress);
        final EditText edtIDAdmin = (EditText) findViewById(R.id.edtIDAdmin);

        edtIDAdmin.setText( Integer.toString(Constants.USER_ID));
        edtIDAdmin.setEnabled(false);

        btnVoltar2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(adicionar.this, principal.class);
                adicionar.this.startActivity(it);
                finish();
            }
        });
        final Button btnGravar = (Button) findViewById(R.id.btnGravar);
        btnGravar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Grava as novas informações
                ServerAccess ss=new ServerAccess();
                int ii=ss.gravarRegistro(edtUsername.getText().toString(), edtPassword.getText().toString(), edtName.getText().toString(), edtAddress.getText().toString(), edtIDAdmin.getText().toString() );
                //FIM - Grava as novas informações
                Intent it = new Intent(adicionar.this, principal.class);
                adicionar.this.startActivity(it);
                finish();

            }
        });
    }


}