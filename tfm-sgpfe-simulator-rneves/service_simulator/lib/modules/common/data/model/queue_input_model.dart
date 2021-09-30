import 'package:meta/meta.dart';

import '../../domain/entities/queue.dart';

class QueueInputModel extends Queue {
  QueueInputModel(
      {@required int id,
      @required String letter,
      @required String name,
      @required String description,
      @required int servicePostOfficeId,
      @required int activeServers,
      @required int type,
      @required int maxAvailable,
      @required bool tolerance})
      : super(
            id: id,
            description: description,
            name: name,
            letter: letter,
            activeServers: activeServers,
            type: type,
            maxAvailable: maxAvailable,
            tolerance: tolerance,
            servicePostOfficeId: servicePostOfficeId);

  Map toMap() {
    return {
      'id': id,
      'description': description,
      'name': name,
      'letter': letter,
      'activeServers': activeServers,
      'type': type,
      'maxAvailable': maxAvailable,
      'tolerance': tolerance,
      'servicePostOfficeId': servicePostOfficeId
    };
  }

  static List<QueueInputModel> fromMap(Map<String, dynamic> map) {
    if (map == null) return null;
    List<QueueInputModel> queueInputModel = [];

    List data = map["properties"]["queues"];
    data.forEach((ticket) => {
          queueInputModel.add(QueueInputModel(
              id: ticket['properties']['id'],
              description: ticket['properties']['description'],
              letter: ticket['properties']['letter'],
              activeServers: ticket['properties']['activeServers'],
              type: ticket['properties']['type'],
              maxAvailable: ticket['properties']['maxAvailable'],
              tolerance: ticket['properties']['tolerance'],
              servicePostOfficeId: ticket['properties']['servicePostOfficeId'],
              name: ticket['properties']['name']))
        });
    return queueInputModel;
  }
}
