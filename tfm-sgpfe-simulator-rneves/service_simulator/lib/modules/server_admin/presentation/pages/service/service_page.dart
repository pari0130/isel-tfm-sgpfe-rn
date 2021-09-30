import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:service_simulator/modules/server_admin/presentation/pages/service/widgets/service_widget.dart';
import '../../../../common/presentation/widgets/common/appbar_gradient.dart';
import '../../../../common/presentation/widgets/common/menu_options.dart';

class ServiceAdminPage extends StatefulWidget {
  @override
  State<ServiceAdminPage> createState() => _ServiceDetailsPageState();

  static _ServiceDetailsPageState of(BuildContext context) {
    final _ServiceDetailsPageState navigator =
        context.findAncestorStateOfType();

    assert(() {
      if (navigator == null) {
        throw new FlutterError(
            '_ServiceDetailsPageState operation requested with a context that does '
            'not include a ServiceDetailsPage.');
      }
      return true;
    }());

    return navigator;
  }
}

class _ServiceDetailsPageState extends State<ServiceAdminPage> {
  String changed;

  @override
  Widget build(BuildContext context) {
    final Map arguments = ModalRoute.of(context).settings.arguments as Map;

    return Scaffold(
      appBar: AppBar(
        title: Text("Service ${arguments["service_name"]}"),
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
          child: ServiceAdminDetailsWidget(),
        ),
      ),
    );
  }
}
