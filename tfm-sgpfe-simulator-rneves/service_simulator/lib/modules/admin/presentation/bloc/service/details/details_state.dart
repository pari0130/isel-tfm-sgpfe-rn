import 'package:equatable/equatable.dart';
import 'package:flutter/material.dart';
import 'package:meta/meta.dart';

import '../../../../../common/domain/entities/service_post_office.dart';

abstract class AdminServiceDetailsState extends Equatable {
  const AdminServiceDetailsState();

  @override
  List<Object> get props => [];
}

class ServiceUninitialized extends AdminServiceDetailsState {}

class ServiceLoading extends AdminServiceDetailsState {}

class ServicePostOfficeFetched extends AdminServiceDetailsState {
  final Key key = UniqueKey();
  final List<ServicePostOffice> postOffices;

  ServicePostOfficeFetched({@required this.postOffices});

  @override
  List<Object> get props => [postOffices];
}

class ServicePostOfficeCreated extends AdminServiceDetailsState {
  final bool success;

  ServicePostOfficeCreated({@required this.success});

  @override
  List<Object> get props => [success];
}

class ServiceDetailsUpdated extends AdminServiceDetailsState {
  final bool success;

  ServiceDetailsUpdated({@required this.success});

  @override
  List<Object> get props => [success];
}

class ServiceDetailsDeleted extends AdminServiceDetailsState {
  final bool success;

  ServiceDetailsDeleted({@required this.success});

  @override
  List<Object> get props => [success];
}
