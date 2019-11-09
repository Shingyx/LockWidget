package com.github.shingyx.lockwidget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends Activity {
    private Switch enabledSwitch;
    private Button lockButton;
    private CompoundButton.OnCheckedChangeListener onEnabledSwitched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enabledSwitch = findViewById(R.id.enabled_switch);
        lockButton = findViewById(R.id.lock_button);

        onEnabledSwitched = (v, isChecked) -> {
            silentlyCheckEnabledSwitch(!isChecked);
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        };
        enabledSwitch.setOnCheckedChangeListener(onEnabledSwitched);

        lockButton.setOnClickListener(v -> {
            Intent intent = new Intent(LockService.ACTION_LOCK, null, this, LockService.class);
            startService(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean enabled = LockService.isEnabled(this);
        silentlyCheckEnabledSwitch(enabled);
        lockButton.setEnabled(enabled);
    }

    private void silentlyCheckEnabledSwitch(boolean check) {
        enabledSwitch.setOnCheckedChangeListener(null);
        enabledSwitch.setChecked(check);
        enabledSwitch.setOnCheckedChangeListener(onEnabledSwitched);
    }
}
