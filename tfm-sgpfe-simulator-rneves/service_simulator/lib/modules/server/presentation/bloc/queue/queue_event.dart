import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

abstract class QueueEvent extends Equatable {
  const QueueEvent();

  @override
  List<Object> get props => [];
}

class QueueLoading extends QueueEvent {}

class QueueFetch extends QueueEvent {
  final int postOfficeId;

  const QueueFetch({@required this.postOfficeId});

  @override
  List<Object> get props => [postOfficeId];
}

class QueueAttend extends QueueEvent {
  final int queueId;

  const QueueAttend({@required this.queueId});

  @override
  List<Object> get props => [queueId];
}

class QueueServing extends QueueEvent {
  final int queueId;
  final bool serving;

  const QueueServing({@required this.queueId, @required this.serving});

  @override
  List<Object> get props => [queueId, serving];
}
