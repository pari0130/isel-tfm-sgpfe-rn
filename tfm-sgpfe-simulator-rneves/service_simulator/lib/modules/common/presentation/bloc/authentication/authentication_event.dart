import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

abstract class AuthenticationEvent extends Equatable {
  const AuthenticationEvent();

  @override
  List<Object> get props => [];
}

class AppStarted extends AuthenticationEvent {}

class BackToLogin extends AuthenticationEvent {}

class SignIn extends AuthenticationEvent {
  final String username;
  final String password;

  const SignIn({@required this.username, @required this.password});

  @override
  List<Object> get props => [username, password];
}

class SignedIn extends AuthenticationEvent {
  final String role;
  const SignedIn({@required this.role});

  @override
  List<Object> get props => [role];
}

class LoggedIn extends AuthenticationEvent {
  final String token;
  final String username;
  final String password;

  const LoggedIn(
      {@required this.token, @required this.username, @required this.password});

  @override
  List<Object> get props => [token];
}

class LoggedOut extends AuthenticationEvent {}
