import 'package:equatable/equatable.dart';
import 'package:flutter/material.dart';
import 'package:service_simulator/modules/common/domain/entities/service.dart';

abstract class AuthenticationState extends Equatable {
  const AuthenticationState();
  @override
  List<Object> get props => [];
}

class AuthenticationUninitialized extends AuthenticationState {}

class AuthenticationAuthenticated extends AuthenticationState {
  final String role;
  final Service service;
  const AuthenticationAuthenticated({@required this.role, this.service});

  @override
  List<Object> get props => [role, service];
}

class AuthenticationUnauthenticated extends AuthenticationState {}

class AuthenticationLoading extends AuthenticationState {}

class AuthenticationError extends AuthenticationState {
  final String error;
  const AuthenticationError({@required this.error});

  @override
  List<Object> get props => [error];
}
