import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:progress_dialog/progress_dialog.dart';

class ProgressDialogGeneric {
  static ProgressDialog pr;
  static ProgressDialog progressDialog(BuildContext context){
    pr = ProgressDialog(context, type: ProgressDialogType.Normal, isDismissible: true, showLogs: false);

    pr.style(
        message: "Carregando...",
        borderRadius: 5.0,
        backgroundColor: Colors.white,
        progressWidget: _spinkit,
        elevation: 5.0,
        insetAnimCurve: Curves.easeInOut,
        progressWidgetAlignment: Alignment.center,
        progress: 0.0,
        maxProgress: 50.0,
        progressTextStyle: TextStyle(
            color: Colors.black, fontSize: 15.0, fontWeight: FontWeight.w400),
        messageTextStyle: TextStyle(
            color: Colors.black, fontSize: 15.0, fontWeight: FontWeight.w600,  letterSpacing: 3.0)
    );

    return pr;
  }

  static updateProgress(String message){
    if(pr != null && pr.isShowing()) {
      pr.update(
          message: message,
          progressWidget: _spinkit,
          progress: 0.0,
          maxProgress: 50.0,
          progressTextStyle: TextStyle(
              color: Colors.black, fontSize: 15.0, fontWeight: FontWeight.w400),
          messageTextStyle: TextStyle(
              color: Colors.black, fontSize: 15.0, fontWeight: FontWeight.w600,  letterSpacing: 3.0)
      );
    }
  }

  static hideProgress(){
    if(pr != null && pr.isShowing()) {
      pr.hide();
    }
  }

  static final SpinKitRing _spinkit = SpinKitRing(
    color: Colors.red,
  );
}
