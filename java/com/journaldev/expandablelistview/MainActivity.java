package com.journaldev.expandablelistview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String IP_Server = "";
    private String url_consulta = "http://iesayala.ddns.net/adolfopalma/php.php";

    private JSONArray jSONArray;
    private DevuelveJSON devuelveJSON;
    ArrayList<String> cabeceras;
    HashMap<String, List<String>> eld;
    JSONObject j;
    static public SharedPreferences prefe;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    HashMap<String, List<String>> expandableListDetail;
    Intent i;

    public MainActivity() throws JSONException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        devuelveJSON = new DevuelveJSON();
        new ListaJugadores().execute();
        prefe = getSharedPreferences("com.journaldev.expandablelistview_preferences", MODE_PRIVATE);
        url_consulta = MainActivity.prefe.getString("cambiarIp","3");
    }

    class ListaJugadores extends AsyncTask<String, String, JSONArray> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Cargando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected JSONArray doInBackground(String... args) {
            try {
                HashMap<String, String> parametrosPost = new HashMap<>();
                parametrosPost.put("ins_sql","SELECT * FROM EQUIPOS,JUGADORES WHERE EQUIPOS.CODEQUIPO = JUGADORES.CODEQUIPOS ORDER BY NOMEQUIPO");
                jSONArray = devuelveJSON.sendRequest(url_consulta, parametrosPost);
                if (jSONArray != null) {
                    return jSONArray;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(JSONArray json) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (json != null) {
                ArrayList<Jugador> jugadores= new ArrayList<Jugador>();
                for(int i=0;i<json.length();i++) {
                    Jugador e = new Jugador();
                    try {
                        j = json.getJSONObject(i);
                        e.setNombreEquipo(j.getString("NomEquipo"));
                        e.setNombreJugador(j.getString("NomJugador"));
                        e.setDorsal(j.getInt("Dorsal"));
                        jugadores.add(e);

                        System.out.println(e.getNombreJugador()+" "+e.getNombreEquipo());
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }

                cabeceras = new ArrayList<String>();
                eld = new HashMap<String, List<String>>();
                for(int i=0;i<jugadores.size();i++){
                    if(eld.containsKey(jugadores.get(i).getNombreEquipo())){
                        eld.get(jugadores.get(i).getNombreEquipo()).add(jugadores.get(i).getNombreJugador()+"   DORSAL: "+ jugadores.get(i).getDorsal());
                    }else{
                        cabeceras.add(jugadores.get(i).getNombreEquipo());
                        eld.put(jugadores.get(i).getNombreEquipo(), new ArrayList<String>());
                        eld.get(jugadores.get(i).getNombreEquipo()).add(jugadores.get(i).getNombreJugador()+"   DORSAL: "+ jugadores.get(i).getDorsal());
                    }
                }
                rellena();

            } else {
                Toast.makeText(MainActivity.this, "JSON Array nulo",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.pref){
            Intent prefe = new Intent(this, Preferencias.class);
            startActivity(prefe);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void rellena(){
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListAdapter = new CustomExpandableListAdapter(this, cabeceras, eld);
        expandableListView.setAdapter(expandableListAdapter);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        eld.clear();
        cabeceras.clear();
        devuelveJSON = new DevuelveJSON();
        new ListaJugadores().execute();
        prefe = getSharedPreferences("com.journaldev.expandablelistview_preferences", MODE_PRIVATE);
        url_consulta = MainActivity.prefe.getString("cambiarIp","3");
        rellena();
    }

}




