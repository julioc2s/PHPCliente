package com.android.phpclient;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class excluir extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.excluir);
        //Get the username ans password text boxes
        final EditText edtID = (EditText) findViewById(R.id.edtID);
        final Button btnExcluir = (Button) findViewById(R.id.btnExcluir);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Exclui as novas informações
                ServerAccess ss=new ServerAccess();
                int ii=ss.excluirRegistro(edtID.getText().toString(), Integer.toString(Constants.USER_ID));

                Intent it = new Intent(excluir.this, principal.class);
                excluir.this.startActivity(it);
                finish();

                //FIM - Exclui as novas informações
            }
        });

        final Button btnVoltar = (Button) findViewById(R.id.btnVoltar3);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Exclui as novas informações
                Intent it = new Intent(excluir.this, principal.class);
                excluir.this.startActivity(it);
                finish();
                //FIM - Exclui as novas informações
            }
        });

    }


}