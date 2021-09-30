import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

abstract class AdminServicePostOfficeEvent extends Equatable {
  const AdminServicePostOfficeEvent();

  @override
  List<Object> get props => [];
}

class ServicePostOfficeQueueFetch extends AdminServicePostOfficeEvent {
  final int postOfficeId;

  const ServicePostOfficeQueueFetch({@required this.postOfficeId});

  @override
  List<Object> get props => [postOfficeId];
}

class ServicePostOfficeQueueCreate extends AdminServicePostOfficeEvent {
  final String name;
  final String description;
  final String letter;
  final int type;
  final int activeServers;
  final int maxAvailable;
  final int servicePostOfficeId;
  final bool tolerance;

  ServicePostOfficeQueueCreate(
      {@required this.name,
      @required this.description,
      @required this.letter,
      @required this.type,
      @required this.activeServers,
      @required this.maxAvailable,
      @required this.servicePostOfficeId,
      @required this.tolerance});

  @override
  List<Object> get props => [
        name,
        description,
        letter,
        type,
        activeServers,
        maxAvailable,
        servicePostOfficeId,
        tolerance
      ];
}

class ServicePostOfficeUpdate extends AdminServicePostOfficeEvent {
  final int id;
  final String description;
  final double latitude;
  final double longitude;
  final String address;

  ServicePostOfficeUpdate(
      {@required this.id,
      @required this.description,
      @required this.latitude,
      @required this.longitude,
      @required this.address});

  @override
  List<Object> get props =>
      [id, description, latitude, longitude, address];
}

class ServicePostOfficeDelete extends AdminServicePostOfficeEvent {
  final int id;

  ServicePostOfficeDelete({@required this.id});

  @override
  List<Object> get props => [id];
}
