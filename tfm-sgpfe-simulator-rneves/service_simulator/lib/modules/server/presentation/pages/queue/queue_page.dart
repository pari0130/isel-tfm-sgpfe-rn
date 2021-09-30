import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

import '../../../../common/presentation/widgets/common/appbar_gradient.dart';
import '../../../../common/presentation/widgets/common/menu_options.dart';
import 'widgets/queue_widget.dart';

class QueuePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Queues"),
        flexibleSpace: AppBarGradient(),
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
          child: QueueWidget(),
        ),
      ),
    );
  }
}
