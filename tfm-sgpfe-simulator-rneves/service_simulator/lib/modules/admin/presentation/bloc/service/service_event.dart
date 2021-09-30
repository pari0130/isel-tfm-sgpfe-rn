import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

abstract class AdminServiceEvent extends Equatable {
  const AdminServiceEvent();

  @override
  List<Object> get props => [];
}

class ServiceFetch extends AdminServiceEvent {}

class ServiceCreate extends AdminServiceEvent {
  final String name;
  final String description;
  
  ServiceCreate({@required this.name, @required this.description});

  @override
  List<Object> get props => [name, description];
}
