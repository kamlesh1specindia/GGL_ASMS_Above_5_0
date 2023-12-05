package com.spec.asms.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.spec.asms.view.SplashActivity;

import java.util.ArrayList;

/**
 * @author Pankit Mistry
 * @version 1.0
 *
 * This class handles the permissions model integration.
 *
 */
public class PermissionHandler {
    // ==============================================================
    // Static Fields
    // ==============================================================
    private final static int ALL_PERMISSIONS_RESULT = 107;
    // ===============================================================
    // Fields
    // ===============================================================
    private ArrayList<String> permissionsToRequest = new ArrayList<>();
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    private Activity activity;

    public PermissionHandler(Activity activity) {
        this.activity = activity;
        permissions.clear();
        permissionsToRequest.clear();
        permissionsRejected.clear();
    }

    /**
     * Checks whether to as permission or not.
     * And return true, if ask permission is required otherwise false.
     *
     * @param args
     * @return
     */
    public boolean isPermissionRequiredToAsk(String...args) {
        boolean result = false;

        for(int i=0;i<args.length;i++) {
            permissions.add(args[i]);
        }
        permissionsToRequest = findUnAskedPermissions(activity, permissions);
        if (permissionsToRequest.size() > 0)
            result =  true;
        else
            result = false;

        return result;
    }

    /**
     * Asks permissions that are required.
     */
    public void askPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            activity.requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
    }

    public ArrayList<String> findUnAskedPermissions(Context context, ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(context, perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    public static boolean hasPermission(Context context, String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    public static boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }
    /**
     * Handles the response of user's interaction with the permission model.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        {
            switch (requestCode) {
                case ALL_PERMISSIONS_RESULT:
                    for (String perms : permissionsToRequest) {
                        if (hasPermission(activity, perms)) {
                            if(activity instanceof SplashActivity) {
                                ((SplashActivity)(activity)).doAction();
                            }
                        } else {
                            permissionsRejected.add(perms);
                        }
                    }

                    if (permissionsRejected.size() > 0) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (activity.shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                                DialogController.showInfo(activity, "These permissions are mandatory for the application. Please allow access.", "OK", new SimpleDialogActionListener() {
                                    @Override
                                    public void onPositiveClick() {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                            askPermissions();
                                    }
                                });
                                return;
                            }
                        }
                    }
                    break;
            }

        }
    }
}
