import 'package:meta/meta.dart';

import '../../domain/entities/service.dart';

class ServiceInputModel extends Service {
  ServiceInputModel(
      {@required int id, @required String name, @required String description})
      : super(id: id, name: name, description: description);

  Map toMap() {
    return {
      'id': id,
      'name': name,
      'description': description,
    };
  }

  static List<ServiceInputModel> fromMap(Map<String, dynamic> map) {
    if (map == null) return null;
    List<ServiceInputModel> serviceInputModel = [];

    List data = map["properties"]["services"];
    data.forEach((service) => {
          serviceInputModel.add(ServiceInputModel(
              id: service['properties']['id'],
              name: service['properties']['name'],
              description: service['properties']['description']))
        });
    return serviceInputModel;
  }

  static ServiceInputModel singleFromMap(Map<String, dynamic> map) {
    if (map == null) return null;
    ServiceInputModel serviceInputModel;

    Map data = map["properties"];
    serviceInputModel = ServiceInputModel(
        id: data['id'], name: data['name'], description: data['description']);
    return serviceInputModel;
  }
}
