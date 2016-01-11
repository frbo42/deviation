package org.fb.deviation.common;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.prefs.Preferences;

@Component
public class Pref {

    private static final String LEFT_ROOT = "LeftRoot";
    private static final String RIGHT_ROOT = "RightRoot";
    private final Preferences preferences;

    Pref() {
        preferences = Preferences.userNodeForPackage(getClass());
    }

    public File lastLeftRoot() {
        return new File(preferences.get(LEFT_ROOT, System.getProperty("user.home")));
    }

    public void setLeftRoot(File folder) {
        preferences.put(LEFT_ROOT, folder.getAbsolutePath());
    }

    public File lastRightRoot() {
        return new File(preferences.get(RIGHT_ROOT, System.getProperty("user.home")));
    }

    public void setRightRoot(File folder) {
        preferences.put(RIGHT_ROOT, folder.getAbsolutePath());
    }
}
