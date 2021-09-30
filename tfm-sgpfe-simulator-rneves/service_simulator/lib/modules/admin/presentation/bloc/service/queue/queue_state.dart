import 'package:equatable/equatable.dart';
import 'package:flutter/material.dart';
import 'package:meta/meta.dart';

abstract class AdminQueueDetailsState extends Equatable {
  const AdminQueueDetailsState();

  @override
  List<Object> get props => [];
}

class QueueUninitialized extends AdminQueueDetailsState {}

class QueueDetailsUpdated extends AdminQueueDetailsState {
  final bool success;

  QueueDetailsUpdated({@required this.success});

  @override
  List<Object> get props => [success];
}

class QueueDetailsDeleted extends AdminQueueDetailsState {
  final bool success;

  QueueDetailsDeleted({@required this.success});

  @override
  List<Object> get props => [success];
}
