package tr.xip.horriblesubsschedule;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT)
            getActionBar().setIcon(android.R.color.transparent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
