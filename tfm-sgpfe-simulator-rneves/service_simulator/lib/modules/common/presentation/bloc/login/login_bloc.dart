import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';

import '../../../../../core/utils/roles.dart';
import '../../../data/repositories/user_repository.dart';
import '../../../domain/entities/user.dart';
import '../authentication/authentication_bloc.dart';
import '../authentication/authentication_event.dart';
import 'login_event.dart';
import 'login_state.dart';

class LoginBloc extends Bloc<LoginEvent, LoginState> {
  final UserRepository userRepository;
  final AuthenticationBloc authenticationBloc;

  LoginBloc({
    @required this.userRepository,
    @required this.authenticationBloc,
  })  : assert(userRepository != null),
        assert(authenticationBloc != null),
        super(LoginInitial());

  @override
  Stream<LoginState> mapEventToState(
    LoginEvent event,
  ) async* {
    if (event is LoginButtonPressed) {
      yield LoginLoading();
      User result = await userRepository.login(event.username, event.password);
      if (result != null &&
          result.authenticated &&
          result.role != Roles.USER.name) {
        await userRepository.persistUser(result);
        authenticationBloc.add(SignedIn(role: result.role));
        yield LoginInitial();
      } else {
        yield LoginFailure(error: "Failed to authenticate!");
      }
    }
  }
}
