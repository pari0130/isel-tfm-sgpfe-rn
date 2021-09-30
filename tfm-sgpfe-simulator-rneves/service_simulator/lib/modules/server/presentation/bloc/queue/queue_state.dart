import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

import '../../../../common/domain/entities/ticket.dart';

abstract class QueueState extends Equatable {
  const QueueState();

  @override
  List<Object> get props => [];
}

class QueueUninitialized extends QueueState {}

class QueueFetched extends QueueState {
  final List<Ticket> tickets;

  const QueueFetched({@required this.tickets});

  @override
  List<Object> get props => [tickets];
}

class QueueAttended extends QueueState {}

class QueueServed extends QueueState {}
