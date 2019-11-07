package com.eesec.hijack;

import android.app.Application;

public class HijackingApplication extends Application {
    boolean hasHijackStart = false;

    public boolean isHasHijackStart() {
        return this.hasHijackStart;
    }

    public void setHasHijackStart(boolean hasHijackStart2) {
        this.hasHijackStart = hasHijackStart2;
    }
}