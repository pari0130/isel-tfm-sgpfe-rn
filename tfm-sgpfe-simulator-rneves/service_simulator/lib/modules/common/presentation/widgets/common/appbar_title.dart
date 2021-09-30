import 'package:flutter/material.dart';

class AppBarTitle extends StatelessWidget {
  const AppBarTitle({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.only(left: 20.0),
      alignment: Alignment.centerLeft,
      
      child: Image.asset('assets/Logo&PORTUGALEXPORTA_white.png',
          fit: BoxFit.fitHeight, height: 43),
    );
  }
}
