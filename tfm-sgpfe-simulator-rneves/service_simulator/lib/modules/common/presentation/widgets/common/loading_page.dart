import 'package:flutter/material.dart';

import 'appbar_gradient.dart';
import 'appbar_title.dart';
import 'loading_indicator.dart';

class LoadingPage extends StatelessWidget {
  const LoadingPage({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
    appBar: AppBar(
      automaticallyImplyLeading: false,
      centerTitle: true,
      titleSpacing: 0,
      title: GestureDetector(
        onTap: () {},
        child: AppBarTitle(),
      ),
      flexibleSpace: AppBarGradient(),
    ),
    body: Container(
      child: LoadingIndicator(color: Color(0xFF1766A6)),
    ),
  );
  }
}

