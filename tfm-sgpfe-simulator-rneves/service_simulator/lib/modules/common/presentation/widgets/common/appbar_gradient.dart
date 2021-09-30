import 'package:flutter/material.dart';

class AppBarGradient extends StatelessWidget {
  const AppBarGradient({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Container(
        decoration: BoxDecoration(
          gradient: LinearGradient(
            begin: Alignment.centerLeft,
            end: Alignment.centerRight,
            colors: <Color>[
              Theme.of(context).accentColor,
              Theme.of(context).primaryColor
            ],
          ),
        ),
      ),
    );
  }
}
