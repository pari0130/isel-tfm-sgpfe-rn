import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';

// ignore: must_be_immutable
class LoadingIndicator extends StatelessWidget {
  var color = Colors.white;
  double size;

  LoadingIndicator({this.color, this.size = 40});
   
  @override
  Widget build(BuildContext context) =>
      Center(
        child: SpinKitCircle(color: color, size: size,),
      );
}
