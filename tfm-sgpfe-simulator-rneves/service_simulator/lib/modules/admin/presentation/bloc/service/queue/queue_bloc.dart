import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';

import '../../../../../common/data/repositories/service_repository.dart';
import '../../../../../common/presentation/bloc/authentication/authentication_bloc.dart';
import 'queue_event.dart';
import 'queue_state.dart';

class AdminQueueDetailsBloc
    extends Bloc<AdminQueueDetailsEvent, AdminQueueDetailsState> {
  final AuthenticationBloc authenticationBloc;
  final ServiceRepository serviceRepository;

  AdminQueueDetailsBloc(
      {@required this.authenticationBloc, @required this.serviceRepository})
      : super(QueueUninitialized());

  @override
  Stream<AdminQueueDetailsState> mapEventToState(
    AdminQueueDetailsEvent event,
  ) async* {
    var token = await authenticationBloc.userRepository.getUserToken();
    if (event is QueueDetailsUpdate) {
      var result = await serviceRepository.updateServicePostOfficeQueue(
          event.id,
          event.name,
          event.description,
          event.letter,
          event.type,
          event.activeServers,
          event.maxAvailable,
          event.tolerance,
          token);

      yield QueueDetailsUpdated(success: result);
    }

    if (event is QueueDetailsDelete) {
      var result =
          await serviceRepository.deleteServicePostOfficeQueue(event.id, token);
      yield QueueDetailsDeleted(success: result);
    }
  }
}
