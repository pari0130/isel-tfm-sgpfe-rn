import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import '../../../../common/presentation/widgets/common/appbar_gradient.dart';
import '../../../../common/presentation/widgets/common/menu_options.dart';

import 'widget/post_office_widget.dart';

class PostOfficeDetailsPage extends StatefulWidget {
  @override
  State<PostOfficeDetailsPage> createState() => _PostOfficeDetailsPageState();

  static _PostOfficeDetailsPageState of(BuildContext context) {
    final _PostOfficeDetailsPageState navigator =
        context.findAncestorStateOfType();

    assert(() {
      if (navigator == null) {
        throw new FlutterError(
            '_PostOfficeDetailsPageState operation requested with a context that does '
            'not include a PostOfficeDetailsPage.');
      }
      return true;
    }());

    return navigator;
  }
}

class _PostOfficeDetailsPageState extends State<PostOfficeDetailsPage> {
  String changed;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Post Office"),
        flexibleSpace: AppBarGradient(),
        leading: new IconButton(
          icon: new Icon(Icons.arrow_back_ios),
          onPressed: () {
            Navigator.pop(context, changed);
          },
        ),
        actions: [
          menuOptions(context),
        ],
      ),
      body: Container(
        decoration: BoxDecoration(
          gradient: LinearGradient(
            begin: Alignment.topLeft,
            end: Alignment.bottomRight,
            colors: <Color>[
              Color(0xFFF1EDEC),
              Color(0xFFF1EDEC),
              Color(0xFFF1EDEC),
            ],
          ),
        ),
        child: Container(
          margin: EdgeInsets.all(10),
          child: PostOfficeDetailsWidget(),
        ),
      ),
    );
  }
}
