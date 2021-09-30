import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

class ServicePostOffice extends Equatable {
  final int id;
  final double latitude;
  final double longitude;
  final String description;
  final int serviceId;
  final String address;

  ServicePostOffice(
      {@required this.id,
      @required this.latitude,
      @required this.longitude,
      @required this.serviceId,
      @required this.description,
      @required this.address});

  @override
  List<Object> get props =>
      [id, latitude, longitude, serviceId, description, address];
}
