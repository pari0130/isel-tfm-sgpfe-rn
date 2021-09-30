import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../../../../../config/fei_application.dart';
import '../../../../../config/types.dart';
import '../../../../common/data/repositories/user_repository.dart';
import '../../bloc/authentication/authentication_bloc.dart';
import '../../bloc/login/login_bloc.dart';
import 'widgets/login_widget.dart';

class LoginPage extends StatelessWidget {
  final UserRepository _userRepository =
      FeiApplication().getRepository(RepositoryType.USER_REPOSITORY);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: BlocProvider(
        create: (context) {
          return LoginBloc(
            authenticationBloc: BlocProvider.of<AuthenticationBloc>(context),
            userRepository: _userRepository,
          );
        },
        child: Container(
          decoration: BoxDecoration(
            gradient: LinearGradient(
              begin: Alignment.topLeft,
              end: Alignment.bottomRight,
              colors: <Color>[
                Theme.of(context).accentColor,
                Theme.of(context).primaryColor
              ],
            ),
          ),
          child: LoginForm(),
        ),
      ),
    );
  }
}
