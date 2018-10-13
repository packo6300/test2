package com.packo.iptv.helpers;

import android.content.Context;
import android.content.pm.PackageManager;

public class AppExist {
    public AppExist(){

    }
    public boolean estaInstaladaAplicacion(String nombrePaquete, Context context) {

        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(nombrePaquete, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
