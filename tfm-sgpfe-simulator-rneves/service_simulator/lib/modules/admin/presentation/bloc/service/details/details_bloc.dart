import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';

import '../../../../../common/data/repositories/service_repository.dart';
import '../../../../../common/presentation/bloc/authentication/authentication_bloc.dart';
import 'details_event.dart';
import 'details_state.dart';

class AdminServiceDetailsBloc
    extends Bloc<AdminServiceDetailsEvent, AdminServiceDetailsState> {
  final AuthenticationBloc authenticationBloc;
  final ServiceRepository serviceRepository;

  AdminServiceDetailsBloc(
      {@required this.authenticationBloc, @required this.serviceRepository})
      : super(ServiceUninitialized());

  @override
  Stream<AdminServiceDetailsState> mapEventToState(
    AdminServiceDetailsEvent event,
  ) async* {
    var token = await authenticationBloc.userRepository.getUserToken();

    if (event is ServicePostOfficeFetch) {
      yield ServiceLoading();
      var postOffices =
          await serviceRepository.getServicePostOffices(event.serviceId, token);
      yield ServicePostOfficeFetched(
          postOffices: postOffices == null ? [] : postOffices);
    }
    if (event is ServicePostOfficeCreate) {
      var result = await serviceRepository.createServicePostOffice(
          event.description,
          event.latitude,
          event.longitude,
          event.address,
          event.serviceId,
          token);
      yield ServicePostOfficeCreated(success: result);
    }
    if (event is ServiceDetailsUpdate) {
      var result = await serviceRepository.updateService(
          event.id, event.name, event.description, token);
      yield ServiceDetailsUpdated(success: result);
    }
    if (event is ServiceDetailsDelete) {
      var result = await serviceRepository.deleteService(event.id, token);
      yield ServiceDetailsDeleted(success: result);
    }
  }
}
