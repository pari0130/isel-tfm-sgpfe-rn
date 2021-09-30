import 'package:meta/meta.dart';

import '../../domain/entities/ticket.dart';

class TicketInputModel extends Ticket {
  TicketInputModel(
      {@required int id,
      @required int number,
      @required String name,
      @required String letter,
      @required int queueId})
      : super(
            id: id,
            number: number,
            name: name,
            letter: letter,
            queueId: queueId);

  Map toMap() {
    return {
      'id': id,
      'number': number,
      'name': name,
      'letter': letter,
      'queueId': queueId
    };
  }

  static List<TicketInputModel> fromMap(Map<String, dynamic> map) {
    if (map == null) return null;
    List<TicketInputModel> ticketInputModel = [];

    List data = map["properties"]["tickets"];
    data.forEach((ticket) => {
          ticketInputModel.add(TicketInputModel(
              id: ticket['properties']['ticketId'],
              number: ticket['properties']['ticketNumber'],
              letter: ticket['properties']['ticketLetter'],
              queueId: ticket['properties']['queueId'],
              name: ticket['properties']['name']))
        });
    return ticketInputModel;
  }
}
