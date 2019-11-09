package com.github.shingyx.lockwidget;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

public class LockService extends AccessibilityService {
    public static String ACTION_LOCK = "com.github.shingyx.lockwidget.ACTION_LOCK";

    public static boolean isEnabled(Context context) {
        AccessibilityManager accessibilityManager = context.getSystemService(AccessibilityManager.class);
        String serviceId = context.getPackageName() + "/." + LockService.class.getSimpleName();

        return accessibilityManager != null && accessibilityManager
                .getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC)
                .stream()
                .anyMatch(s -> s.getId().equals(serviceId));
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (ACTION_LOCK.equals(intent.getAction())) {
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
