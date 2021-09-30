import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import '../../../../common/domain/entities/queue.dart';
import 'widgets/queue_widget.dart';
import '../../../../common/presentation/widgets/common/appbar_gradient.dart';
import '../../../../common/presentation/widgets/common/menu_options.dart';

class QueueDetailsPage extends StatefulWidget {
  @override
  State<QueueDetailsPage> createState() => _QueueDetailsPageState();

  static _QueueDetailsPageState of(BuildContext context) {
    final _QueueDetailsPageState navigator = context.findAncestorStateOfType();

    assert(() {
      if (navigator == null) {
        throw new FlutterError(
            '_QueueDetailsPageState operation requested with a context that does '
            'not include a QueueDetailsPage.');
      }
      return true;
    }());

    return navigator;
  }
}

class _QueueDetailsPageState extends State<QueueDetailsPage> {
  String changed;

  @override
  Widget build(BuildContext context) {
    final Map arguments = ModalRoute.of(context).settings.arguments as Map;
    Queue queue = arguments["queue"];

    return Scaffold(
      appBar: AppBar(
        title: Text("Queue ${queue.name}"),
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
          child: QueueDetailsWidget(),
        ),
      ),
    );
  }
}
