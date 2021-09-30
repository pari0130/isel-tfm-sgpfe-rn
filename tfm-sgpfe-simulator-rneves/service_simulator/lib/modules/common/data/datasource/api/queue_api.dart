import '../../../../../core/network/api_request.dart';
import '../../model/ticket_input_model.dart';

//TODO change name to ticket api
class QueueApi {
  //TODO

  //TODO verify this
  static Future<List<TicketInputModel>> getQueuesBeingAttended(
      int servicePostOfficeId, token) async {
    var path = "/ticket/postoffice/$servicePostOfficeId/battended";

    return await ApiRequest.doGet(path, token)
        .then((value) => TicketInputModel.fromMap(value));
  }

  static Future<bool> attendTicket(int ticketId, token) async {
    var path = "/ticket/$ticketId";

    //TODO FIX THIS
    await ApiRequest.doPut({}, path, token);
    return true;
  }
}
