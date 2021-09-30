import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:meta/meta.dart';

import '../../../../common/domain/entities/service.dart';

abstract class AdminServiceState extends Equatable {
  const AdminServiceState();

  @override
  List<Object> get props => [];
}

class ServiceLoading extends AdminServiceState {}

class ServiceUninitialized extends AdminServiceState {}

class ServiceFetched extends AdminServiceState {
  final Key key = UniqueKey();
  final List<Service> services;

  ServiceFetched({@required this.services});

  @override
  List<Object> get props => [services];
}

class ServiceCreated extends AdminServiceState {
  final bool success;

  ServiceCreated({@required this.success});

  @override
  List<Object> get props => [success];
}
