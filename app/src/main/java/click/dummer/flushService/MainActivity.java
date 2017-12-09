package click.dummer.flushService;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private EditText mStringEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStringEdit = (EditText) findViewById(R.id.string_edit);
    }

    public void storeNow(View sender) {
        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String theString = mStringEdit.getText().toString();
        mPreferences.edit().putString("sensitiveMsg", theString).apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Set<String> packs = NotificationManagerCompat.getEnabledListenerPackages(getApplicationContext());
        boolean readNotiPermissions = packs.contains(getPackageName());
        if (readNotiPermissions == false) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alertDialogBuilder.setMessage(getString(R.string.sorry, getString(R.string.app_name)));
            alertDialogBuilder.show();
        }

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mStringEdit.setText(mPreferences.getString("sensitiveMsg", getString(R.string.defaultChars)));
    }
}
