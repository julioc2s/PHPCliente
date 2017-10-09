package com.android.phpclient;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.security.Principal;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class enviolocalizacao extends Activity {


    Button btnLogin;
    EditText txtUsername,txtPassword;
    String id;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enviolocalizacao);

        Intent intent = getIntent();
        Bundle params = intent.getExtras();
        id = params.getString("id");

        final TextView lblLatitude = (TextView) findViewById(R.id.lblLatitude);
        final TextView lblLongitude = (TextView) findViewById(R.id.lblLongitude);

        final EditText editText2 = (EditText) findViewById(R.id.editText2);



        final TextView txtResult = (TextView) findViewById(R.id.txtResult);

        btnLogin=(Button) findViewById(R.id.btnEnviaLocalizacao);
        Button btnOndeEstou=(Button) findViewById(R.id.btnOndeEstou);

        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            public void run() {
                TimerMethod();
            }

        }, 0, 60000);
        final GPSTracker gps = new GPSTracker(this);
        if(gps.canGetLocation())
        {
            //Toast.makeText(getApplicationContext(), "GPS ESTÁ ligado!", Toast.LENGTH_LONG).show();

            GravaLocalizacao();


        }
        else
        {
            int hours = new Time(System.currentTimeMillis()).getHours();
            int minutos = new Time(System.currentTimeMillis()).getMinutes();
            int segundos = new Time(System.currentTimeMillis()).getSeconds();
            String agora = String.valueOf(hours)   + ":" + String.valueOf(minutos) + ":" + String.valueOf(segundos);

            editText2.setText(editText2.getText() + " gps desligado " + agora);

            Toast.makeText(getApplicationContext(), "GPS NÃO está ligado!", Toast.LENGTH_LONG).show();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {

            GravaLocalizacao();

        }
        });

        btnOndeEstou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                String url = "http://pcsystems.com.br/SimplesEcommerce/localizacao.php?pkcodentregador=" + id;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

    }

    private void TimerMethod()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(Timer_Tick);
    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {

            //Este metodo é o tick do relogio

            GravaLocalizacao();

        }
    };

    public void GravaLocalizacao(){

        //Obtem a coordenada
        final GPSTracker gps = new GPSTracker(this);
        gps.getLatitude(); // returns latitude
        gps.getLongitude(); // returns longitude

        final TextView lblLatitude = (TextView) findViewById(R.id.lblLatitude);
        final TextView lblLongitude = (TextView) findViewById(R.id.lblLongitude);

        final EditText editText2 = (EditText) findViewById(R.id.editText2);

        lblLatitude.setText(  String.valueOf( gps.getLatitude()));
        lblLongitude.setText(  String.valueOf( gps.getLongitude()));
        //FIM - Obtem a coordenada

        //Envia para gravar a localizacao
        ServerAccess ss=new ServerAccess();
        Integer ii =ss.gravarLocalizacao(id, lblLatitude.getText().toString(), lblLongitude.getText().toString());
        if(ii==1)
        {
            TextView txtResult = (TextView) findViewById(R.id.txtResult);
            int hours = new Time(System.currentTimeMillis()).getHours();
            int minutos = new Time(System.currentTimeMillis()).getMinutes();
            int segundos = new Time(System.currentTimeMillis()).getSeconds();
            String agora = String.valueOf(hours)   + ":" + String.valueOf(minutos) + ":" + String.valueOf(segundos);

            txtResult.setText("Última atualização "
                    + agora );

            editText2.setText(editText2.getText() + " ok " + agora );

        }
        else
        {

            int hours = new Time(System.currentTimeMillis()).getHours();
            int minutos = new Time(System.currentTimeMillis()).getMinutes();
            int segundos = new Time(System.currentTimeMillis()).getSeconds();
            String agora = String.valueOf(hours)   + ":" + String.valueOf(minutos) + ":" + String.valueOf(segundos);

            editText2.setText(editText2.getText() + " erro " + agora);

            Toast.makeText(getApplicationContext(), "Erro na gravacao ", Toast.LENGTH_LONG).show();
            //Intent employeeListActivity=new Intent(getApplicationContext(),EmployeesListActivity.class);
            //startActivity(employeeListActivity);

            finish();
        }
        //FIM - Envia para gravar a localizacao


    }


}