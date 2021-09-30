import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:meta/meta.dart';

import '../../../../common/data/repositories/queue_repository.dart';
import '../../../../common/presentation/bloc/authentication/authentication_bloc.dart';
import 'queue_event.dart';
import 'queue_state.dart';

class QueueBloc extends Bloc<QueueEvent, QueueState> {
  final AuthenticationBloc authenticationBloc;
  final QueueRepository queueRepository;

  QueueBloc({@required this.authenticationBloc, @required this.queueRepository})
      : super(QueueUninitialized());

  @override
  Stream<QueueState> mapEventToState(QueueEvent event) async* {
    if (event is QueueFetch) {
      var token = await authenticationBloc.userRepository.getUserToken();
      var queues = await queueRepository.getQueuesBeingAttended(
          event.postOfficeId, token);
      yield QueueFetched(tickets: queues == null ? [] : queues);
    }
    if (event is QueueAttend) {
      var token = await authenticationBloc.userRepository.getUserToken();
      var attended = await queueRepository.attendTicket(event.queueId, token);
      yield QueueAttended(); //todo add attended
    }

    if (event is QueueServing) {
      var token = await authenticationBloc.userRepository.getUserToken();
      var served =
          await queueRepository.serveQueue(event.queueId, event.serving, token);
      yield QueueServed(); //todo add served
    }
  }
}
