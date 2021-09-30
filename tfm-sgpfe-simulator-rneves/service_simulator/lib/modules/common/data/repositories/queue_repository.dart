import 'package:service_simulator/modules/common/data/datasource/api/user_api.dart';

import '../../domain/entities/ticket.dart';
import '../datasource/api/queue_api.dart';

class QueueRepository {
  Future<List<Ticket>> getQueuesBeingAttended(
      int servicePostOfficeId, token) async {
    try {
      return QueueApi.getQueuesBeingAttended(servicePostOfficeId, token);
    } catch (error) {
      return [];
    }
  }

  Future<bool> attendTicket(int ticketId, token) async {
    try {
      return QueueApi.attendTicket(ticketId, token);
    } catch (error) {
      return false;
    }
  }

  Future<bool> serveQueue(int queueId, bool serving, token) async {
    try {
      if (serving) return UserApi.servingPostOfficeQueue(queueId, token);
      return UserApi.leavePostOfficeQueue(queueId, token);
    } catch (error) {
      return false;
    }
  }
}
