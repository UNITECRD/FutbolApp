package edu.unitec.futbolapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by nivx1 on 09/04/2015.
 */
public class EsquemaActivity extends Activity {
    private ListView Lista;
    List<Esquema> ESQUEMAS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esquema);

        MyDatabaseHandler db = new MyDatabaseHandler(getBaseContext());

        ESQUEMAS = db.getAllEsquema();
        db.close();

        Lista=(ListView)findViewById(R.id.esquemalistview);
        Lista.setAdapter(new MyListViewAdapter(ESQUEMAS,getBaseContext(),this));

        Lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //here you can use the position to determine what checkbox to check
                //this assumes that you have an array of your checkboxes as well. called checkbox
                Toast.makeText(view.getContext(), "TEST", Toast.LENGTH_SHORT);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.club_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.addClub){
            addEsquemaDialog dialog = new addEsquemaDialog(ESQUEMAS,(MyListViewAdapter)Lista.getAdapter(),this);
            dialog.show(getFragmentManager(),"addEsquemaDialog");
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }
}
