import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';

import '../../../../common/data/repositories/service_repository.dart';
import '../../../../common/presentation/bloc/authentication/authentication_bloc.dart';
import 'service_event.dart';
import 'service_state.dart';

class AdminServiceBloc extends Bloc<AdminServiceEvent, AdminServiceState> {
  final AuthenticationBloc authenticationBloc;
  final ServiceRepository serviceRepository;

  AdminServiceBloc(
      {@required this.authenticationBloc, @required this.serviceRepository})
      : super(ServiceUninitialized());

  @override
  Stream<AdminServiceState> mapEventToState(
    AdminServiceEvent event,
  ) async* {
    var token = await authenticationBloc.userRepository.getUserToken();

    if (event is ServiceFetch) {
      yield ServiceLoading();
      var services = await serviceRepository.getServices(token);
      yield ServiceFetched(services: services == null ? [] : services);
    }
    if (event is ServiceCreate) {
      var result = await serviceRepository.createService(
          event.name, event.description, token);
      yield ServiceCreated(success: result);
    }
  }
}
