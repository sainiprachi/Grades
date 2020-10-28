package com.grades.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkDetector {
        private Context _context;

        public NetworkDetector(Context context) {
            this._context = context;
        }
        /**
         * Checking for all possible internet providers
         * **/
        public boolean isConnectingToInternet() {
            ConnectivityManager connectivity = (ConnectivityManager) _context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }

            }
            return false;
        }

//    public static boolean customDialog(final Context dialogContext,
//                                       String message, final boolean flag) {
//        final Dialog dialog;
//        dialog = new Dialog(dialogContext);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawableResource(
//                android.R.color.transparent);
//        // dialog.getWindow().getAttributes().windowAnimations =
//        // R.style.DialogAnimation;
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_condition);
//        TextView btn = ((TextView) dialog.findViewById(R.id.btnDialogOk));
//        btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                dialog.dismiss();
//                if (flag) {
//                    Activity act = (Activity) dialogContext;
//                    act.finish();
//                }
//            }
//        });
//        ((TextView) dialog.findViewById(R.id.txtDialogMessage))
//                .setText(message);
//
//        dialog.show();
//        return true;
//    }
}
