import 'package:equatable/equatable.dart';
import 'package:flutter/material.dart';
import 'package:meta/meta.dart';

import '../../../../../common/domain/entities/service_post_office.dart';

abstract class ServiceAdminServiceDetailsState extends Equatable {
  const ServiceAdminServiceDetailsState();

  @override
  List<Object> get props => [];
}

class ServiceUninitialized extends ServiceAdminServiceDetailsState {}

class ServiceLoading extends ServiceAdminServiceDetailsState {}

class ServicePostOfficeFetched extends ServiceAdminServiceDetailsState {
  final Key key = UniqueKey();
  final List<ServicePostOffice> postOffices;

  ServicePostOfficeFetched({@required this.postOffices});

  @override
  List<Object> get props => [postOffices];
}

class ServicePostOfficeCreated extends ServiceAdminServiceDetailsState {
  final bool success;

  ServicePostOfficeCreated({@required this.success});

  @override
  List<Object> get props => [success];
}

class ServiceDetailsUpdated extends ServiceAdminServiceDetailsState {
  final bool success;

  ServiceDetailsUpdated({@required this.success});

  @override
  List<Object> get props => [success];
}

class ServiceDetailsDeleted extends ServiceAdminServiceDetailsState {
  final bool success;

  ServiceDetailsDeleted({@required this.success});

  @override
  List<Object> get props => [success];
}
