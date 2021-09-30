import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

abstract class AdminServiceDetailsEvent extends Equatable {
  const AdminServiceDetailsEvent();

  @override
  List<Object> get props => [];
}

class ServicePostOfficeFetch extends AdminServiceDetailsEvent {
  final int serviceId;

  ServicePostOfficeFetch({@required this.serviceId});

  @override
  List<Object> get props => [serviceId];
}

class ServicePostOfficeCreate extends AdminServiceDetailsEvent {
  final String description;
  final double latitude;
  final double longitude;
  final int serviceId;
  final String address;

  ServicePostOfficeCreate(
      {@required this.description,
      @required this.latitude,
      @required this.longitude,
      @required this.serviceId,
      @required this.address});

  @override
  List<Object> get props =>
      [description, latitude, longitude, serviceId, address];
}

class ServiceDetailsUpdate extends AdminServiceDetailsEvent {
  final int id;
  final String name;
  final String description;

  ServiceDetailsUpdate(
      {@required this.id, @required this.name, @required this.description});

  @override
  List<Object> get props => [name, description];
}

class ServiceDetailsDelete extends AdminServiceDetailsEvent {
  final int id;

  ServiceDetailsDelete({@required this.id});

  @override
  List<Object> get props => [id];
}
