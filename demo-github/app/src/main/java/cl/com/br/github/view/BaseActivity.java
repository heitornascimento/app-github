package cl.com.br.github.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import cl.com.br.github.R;
import cl.com.br.github.utils.Connection;

/**
 * Created by heitornascimento on 8/16/16.
 */
public class BaseActivity extends AppCompatActivity {

    public static final String DEBUG = "github";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(DEBUG, "On Create");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(DEBUG, "On Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(DEBUG, "On Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(DEBUG, "On Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(DEBUG, "On Destroy");
    }

    /**
     * Should call for every data connection.
     *
     * @return
     */
    protected boolean checkInternetConnection() {

        boolean result = Connection.hasValidaConnection(this);

        if (!result) {
            Snackbar snackbar = Snackbar.
                    make(findViewById(R.id.cl),
                            getResources().getString(R.string.no_internet), Snackbar.LENGTH_SHORT)
                    .setAction("Settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                        }
                    });
            snackbar.show();
        }
        return result;
    }
}
