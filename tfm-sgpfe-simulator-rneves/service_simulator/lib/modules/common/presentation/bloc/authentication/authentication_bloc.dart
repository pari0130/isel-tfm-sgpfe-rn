import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:meta/meta.dart';
import 'package:service_simulator/core/utils/roles.dart';
import 'package:service_simulator/modules/common/data/repositories/service_repository.dart';
import 'package:service_simulator/modules/common/domain/entities/service.dart';

import '../../../data/repositories/user_repository.dart';
import 'authentication_event.dart';
import 'authentication_state.dart';

class AuthenticationBloc
    extends Bloc<AuthenticationEvent, AuthenticationState> {
  final UserRepository userRepository;
  final ServiceRepository serviceRepository; //should not be here! martelada

  AuthenticationBloc(
      {@required this.userRepository, @required this.serviceRepository})
      : super(AuthenticationUninitialized());

  @override
  Stream<AuthenticationState> mapEventToState(
      AuthenticationEvent event) async* {
    if (event is AppStarted) {
      await Future.delayed(Duration(seconds: 3));
      yield AuthenticationUnauthenticated();
    }
    if (event is SignedIn) {
      if (event.role == Roles.SERVICE_ADMIN.name) {
        var token = await userRepository.getUserToken();
        var service = await serviceRepository.getService(token);
        yield AuthenticationAuthenticated(role: event.role, service: service);
      } else
        yield AuthenticationAuthenticated(role: event.role);
    }

    if (event is BackToLogin) {
      yield AuthenticationUnauthenticated();
    }
  }
}
