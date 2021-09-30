import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';

import '../../../../../common/data/repositories/queue_repository.dart';
import '../../../../../common/data/repositories/service_repository.dart';
import '../../../../../common/presentation/bloc/authentication/authentication_bloc.dart';
import 'service_postoffice_event.dart';
import 'service_postoffice_state.dart';

class AdminServicePostOfficeBloc
    extends Bloc<AdminServicePostOfficeEvent, AdminServicePostOfficeState> {
  final AuthenticationBloc authenticationBloc;
  final ServiceRepository serviceRepository;
  final QueueRepository queueRepository;

  AdminServicePostOfficeBloc(
      {@required this.authenticationBloc,
      @required this.serviceRepository,
      @required this.queueRepository})
      : super(ServicePostOfficeUninitialized());

  @override
  Stream<AdminServicePostOfficeState> mapEventToState(
    AdminServicePostOfficeEvent event,
  ) async* {
    var token = await authenticationBloc.userRepository.getUserToken();
    if (event is ServicePostOfficeQueueFetch) {
      yield ServicePostOfficeLoading();
      var queues = await serviceRepository.getServicePostOfficeQueues(
          event.postOfficeId, token);
      yield ServicePostOfficeQueuesFetched(
          queues: queues == null ? [] : queues);
    }
    if (event is ServicePostOfficeQueueCreate) {
      var result = await serviceRepository.createServicePostOfficeQueue(
          event.name,
          event.description,
          event.letter,
          event.type,
          event.activeServers,
          event.maxAvailable,
          event.servicePostOfficeId,
          event.tolerance,
          token);
      yield ServicePostOfficeQueueCreated(success: result);
    }
    if (event is ServicePostOfficeUpdate) {
      var result = await serviceRepository.updateServicePostOffice(
          event.id,
          event.description,
          event.latitude,
          event.longitude,
          event.address,
          token);
      yield ServicePostOfficeUpdated(success: result);
    }
    if (event is ServicePostOfficeDelete) {
      var result =
          await serviceRepository.deleteServicePostOffice(event.id, token);
      yield ServicePostOfficeDeleted(success: result);
    }
  }
}
