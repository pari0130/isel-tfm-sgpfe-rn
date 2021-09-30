import 'package:meta/meta.dart';

import '../../domain/entities/service_post_office.dart';

class ServicePostOfficeInputModel extends ServicePostOffice {
  ServicePostOfficeInputModel(
      {@required int id,
      @required double latitude,
      @required double longitude,
      @required String description,
      @required int serviceId,
      @required String address})
      : super(
            id: id,
            latitude: latitude,
            longitude: longitude,
            description: description,
            serviceId: serviceId,
            address: address);

  Map toMap() {
    return {
      'id': id,
      'latitude': latitude,
      'longitude': longitude,
      'description': description,
      'serviceId' : serviceId,
      'address' : address
    };
  }

  static List<ServicePostOfficeInputModel> fromMap(Map<String, dynamic> map) {
    if (map == null) return null;
    List<ServicePostOfficeInputModel> servicePostOfficeInputModel = [];

    List data = map["properties"]["servicesPostOffices"];
    data.forEach((servicePostOffice) => {
          servicePostOfficeInputModel.add(ServicePostOfficeInputModel(
              id: servicePostOffice['properties']['id'],
              latitude: servicePostOffice['properties']['latitude'],
              longitude : servicePostOffice['properties']['longitude'],
              serviceId : servicePostOffice['properties']['serviceId'],
              address : servicePostOffice['properties']['address'],
              description: servicePostOffice['properties']['description']))
        });
    return servicePostOfficeInputModel;
  }
}
