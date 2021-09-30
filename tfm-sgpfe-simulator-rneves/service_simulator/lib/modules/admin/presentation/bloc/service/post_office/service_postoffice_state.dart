import 'package:equatable/equatable.dart';
import 'package:flutter/material.dart';
import 'package:meta/meta.dart';

import '../../../../../common/domain/entities/queue.dart';

abstract class AdminServicePostOfficeState extends Equatable {
  const AdminServicePostOfficeState();

  @override
  List<Object> get props => [];
}

class ServicePostOfficeUninitialized extends AdminServicePostOfficeState {}

class ServicePostOfficeLoading extends AdminServicePostOfficeState {}

class ServicePostOfficeQueuesFetched extends AdminServicePostOfficeState {
  final Key key = UniqueKey();
  final List<Queue> queues;

  ServicePostOfficeQueuesFetched({@required this.queues});

  @override
  List<Object> get props => [queues];
}

class ServicePostOfficeQueueCreated extends AdminServicePostOfficeState {
  final bool success;

  ServicePostOfficeQueueCreated({@required this.success});

  @override
  List<Object> get props => [success];
}

class ServicePostOfficeUpdated extends AdminServicePostOfficeState {
  final bool success;

  ServicePostOfficeUpdated({@required this.success});

  @override
  List<Object> get props => [success];
}

class ServicePostOfficeDeleted extends AdminServicePostOfficeState {
  final bool success;

  ServicePostOfficeDeleted({@required this.success});

  @override
  List<Object> get props => [success];
}
