package com.github.shingyx.lockwidget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ShortcutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = getIntent().getAction();
        if (Intent.ACTION_CREATE_SHORTCUT.equals(action)) {
            createShortcut();
        } else if (LockService.ACTION_LOCK.equals(action)) {
            lockScreen();
        }

        finish();
    }

    @SuppressWarnings("deprecation") // Use deprecated approach for no icon badge
    private void createShortcut() {
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(LockService.ACTION_LOCK, null, this, getClass()));
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.lock));
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(this, R.mipmap.ic_launcher));
        setResult(Activity.RESULT_OK, intent);
    }

    private void lockScreen() {
        if (LockService.isEnabled(this)) {
            Intent intent = new Intent(LockService.ACTION_LOCK, null, this, LockService.class);
            startService(intent);
        } else {
            Toast.makeText(this, getString(R.string.toast_help), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
