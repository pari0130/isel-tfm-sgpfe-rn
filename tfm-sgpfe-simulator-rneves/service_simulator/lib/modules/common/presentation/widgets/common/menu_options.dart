import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

import '../../../../../config/routes.dart';

Widget menuOptions(context) {
  return PopupMenuButton<String>(
    onSelected: (String value) => optionAction(value, context),
    itemBuilder: (BuildContext context) {
      return Options.options.map((String option) {
        return PopupMenuItem<String>(
          value: option,
          child: Text(option),
        );
      }).toList();
    },
  );
}

class Options {
  static const String Menu = 'Menu';
  static const String SignOut = 'Sign out';

  static const List<String> options = <String>[/*Menu,*/ SignOut];
}

void optionAction(String option, context) {
  if (option == Options.Menu) {
    print('Menu TODO');
  } else if (option == Options.SignOut) {
    Navigator.popUntil(context, ModalRoute.withName(FEIRoutes.INITIAL));
  }
}
