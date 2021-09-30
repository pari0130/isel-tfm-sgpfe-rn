import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../../../../../common/presentation/bloc/login/login_bloc.dart';
import '../../../../../common/presentation/bloc/login/login_event.dart';
import '../../../../../common/presentation/bloc/login/login_state.dart';
import '../../../widgets/common/loading_indicator.dart';

class LoginForm extends StatefulWidget {
  @override
  State<LoginForm> createState() => _LoginFormState();
}

class _LoginFormState extends State<LoginForm> {
  final _usernameController = TextEditingController();
  final _passwordController = TextEditingController();
  var obscurePass = true;
  var loginButtonPressed = false;

  @override
  Widget build(BuildContext context) {
    _onSignInButtonPressed() {
      setState(() {
        loginButtonPressed = true;
      });
      BlocProvider.of<LoginBloc>(context).add(
        LoginButtonPressed(
          username: _usernameController.text,
          password: _passwordController.text,
        ),
      );
    }

    return BlocListener<LoginBloc, LoginState>(
      listener: (context, state) async {
        if (state is LoginFailure) {
          setState(() {
            loginButtonPressed = false;
          });
          Scaffold.of(context).showSnackBar(
            SnackBar(
              content: Text('${state.error}'),
              backgroundColor: Theme.of(context).errorColor,
            ),
          );
        }
      },
      child: BlocBuilder<LoginBloc, LoginState>(
        builder: (context, state) {
          return GestureDetector(
            onTap: () {},
            child: Stack(
              children: [
                Form(
                  child: Column(
                    children: <Widget>[
                      Row(
                        crossAxisAlignment: CrossAxisAlignment.center,
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Expanded(
                            child: Container(),
                          ),
                          Expanded(
                            child: Column(
                              children: <Widget>[
                                Container(
                                  padding: const EdgeInsets.only(top: 40.0),
                                  child: Row(
                                    crossAxisAlignment:
                                        CrossAxisAlignment.center,
                                    mainAxisAlignment: MainAxisAlignment.center,
                                    children: <Widget>[
                                      Flexible(
                                        flex: 0,
                                        child: Padding(
                                          padding: const EdgeInsets.all(10),
                                          child: Image.asset(
                                            'assets/appicon.png',
                                            fit: BoxFit.fill,
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                                Padding(
                                  padding: EdgeInsets.all(20.0),
                                ),
                                Container(
                                  child: Column(
                                    children: <Widget>[
                                      Container(
                                        width: double.infinity,
                                        height: 60,
                                        child: TextFormField(
                                          enabled: !loginButtonPressed,
                                          cursorColor: Colors.white,
                                          style: TextStyle(
                                            color: Colors.white,
                                          ),
                                          decoration: InputDecoration(
                                            contentPadding:
                                                const EdgeInsets.only(
                                                    left: 25.0,
                                                    top: 30.0,
                                                    bottom: 10.0),
                                            hintText: 'Username',
                                            enabledBorder: OutlineInputBorder(
                                              borderRadius:
                                                  BorderRadius.circular(30.0),
                                              borderSide: const BorderSide(
                                                  color: Colors.white,
                                                  width: 2.0),
                                            ),
                                            disabledBorder: OutlineInputBorder(
                                              borderRadius:
                                                  BorderRadius.circular(30.0),
                                              borderSide: const BorderSide(
                                                  color: Colors.white,
                                                  width: 2.0),
                                            ),
                                            focusColor: Colors.white,
                                            hintStyle: TextStyle(
                                                fontSize: 15,
                                                fontWeight: FontWeight.normal,
                                                color: Colors.white),
                                            border: OutlineInputBorder(
                                              borderRadius:
                                                  BorderRadius.circular(30.0),
                                              borderSide: const BorderSide(
                                                  color: Colors.white,
                                                  width: 2.0),
                                            ),
                                            focusedBorder: OutlineInputBorder(
                                              borderRadius:
                                                  BorderRadius.circular(30.0),
                                              borderSide: BorderSide(
                                                  color: Colors.white,
                                                  width: 2.0),
                                            ),
                                          ),
                                          controller: _usernameController,
                                        ),
                                      ),
                                      Padding(
                                        padding: EdgeInsets.all(5.0),
                                      ),
                                      Container(
                                        width: double.infinity,
                                        height: 60,
                                        child: TextFormField(
                                          enabled: !loginButtonPressed,
                                          cursorColor: Colors.white,
                                          style: TextStyle(
                                            color: Colors.white,
                                          ),
                                          decoration: InputDecoration(
                                            suffixIcon: Container(
                                              margin:
                                                  EdgeInsets.only(right: 10.0),
                                              child: IconButton(
                                                  icon: Icon(
                                                    obscurePass
                                                        ? Icons.visibility
                                                        : Icons.visibility_off,
                                                    color: Colors.white,
                                                  ),
                                                  onPressed: () {
                                                    setState(() {
                                                      obscurePass =
                                                          !obscurePass;
                                                    });
                                                  }),
                                            ),
                                            contentPadding:
                                                const EdgeInsets.only(
                                                    left: 25.0,
                                                    top: 30.0,
                                                    bottom: 10.0),
                                            hintText: 'Password',
                                            enabledBorder: OutlineInputBorder(
                                              borderRadius:
                                                  BorderRadius.circular(30.0),
                                              borderSide: const BorderSide(
                                                  color: Colors.white,
                                                  width: 2.0),
                                            ),
                                            disabledBorder: OutlineInputBorder(
                                              borderRadius:
                                                  BorderRadius.circular(30.0),
                                              borderSide: const BorderSide(
                                                  color: Colors.white,
                                                  width: 2.0),
                                            ),
                                            hintStyle: TextStyle(
                                                fontFamily: 'Lato Regular',
                                                fontSize: 16.0,
                                                fontWeight: FontWeight.normal,
                                                color: Colors.white),
                                            border: OutlineInputBorder(
                                              borderRadius:
                                                  BorderRadius.circular(30.0),
                                              borderSide: const BorderSide(
                                                  color: Colors.white,
                                                  width: 2.0),
                                            ),
                                            focusedBorder: OutlineInputBorder(
                                              borderRadius:
                                                  BorderRadius.circular(30.0),
                                              borderSide: BorderSide(
                                                color: Colors.white,
                                                width: 2.0,
                                              ),
                                            ),
                                          ),
                                          controller: _passwordController,
                                          obscureText: obscurePass,
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                                Padding(
                                  padding: EdgeInsets.all(10.0),
                                ),
                                Container(
                                  child: Column(
                                    children: <Widget>[
                                      Container(
                                        width: double.infinity,
                                        height: 60,
                                        child: RaisedButton(
                                          highlightColor: Colors.white,
                                          disabledColor: Colors.white,
                                          elevation: 0,
                                          onPressed: (state is! LoginLoading) &&
                                                  !loginButtonPressed
                                              ? _onSignInButtonPressed
                                              : null,
                                          shape: RoundedRectangleBorder(
                                            borderRadius:
                                                BorderRadius.circular(35.0),
                                          ),
                                          color: Colors.white,
                                          child: Row(
                                            crossAxisAlignment:
                                                CrossAxisAlignment.center,
                                            mainAxisAlignment:
                                                MainAxisAlignment.center,
                                            children: [
                                              Expanded(
                                                  flex: 5,
                                                  child: Container(
                                                    padding: EdgeInsets.only(
                                                        left: 60.0),
                                                    child: Center(
                                                      child: Text(
                                                        'SIGN IN',
                                                        style: TextStyle(
                                                          color:
                                                              Color(0xFF1766A6),
                                                          letterSpacing: 1.5,
                                                          fontSize: 16.0,
                                                          fontFamily:
                                                              'Oswald Bold',
                                                        ),
                                                      ),
                                                    ),
                                                  )),
                                              Expanded(
                                                flex: 1,
                                                child: Container(
                                                  child: loginButtonPressed
                                                      ? LoadingIndicator(
                                                          color:
                                                              Color(0xFF1766A6))
                                                      : null,
                                                ),
                                              ),
                                            ],
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                              ],
                            ),
                          ),
                          Expanded(child: Container()),
                        ],
                      ),
                    ],
                  ),
                ),
              ],
            ),
          );
        },
      ),
    );
  }
}
