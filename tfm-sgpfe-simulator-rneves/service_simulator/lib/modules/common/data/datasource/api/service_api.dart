import '../../../../../core/network/api_request.dart';
import '../../model/queue_input_model.dart';
import '../../model/service_input_model.dart';
import '../../model/service_post_office_input_model.dart';

class ServiceApi {
  static Future<List<ServiceInputModel>> getServices(token) async {
    //TODO try catch
    var path = "/service";

    return await ApiRequest.doGet(path, token)
        .then((value) => ServiceInputModel.fromMap(value));
  }

  static Future<ServiceInputModel> getService(token) async {
    //TODO try catch
    var path = "/service/0";

    return await ApiRequest.doGet(path, token)
        .then((value) => ServiceInputModel.singleFromMap(value));
  }

  static Future<List<ServicePostOfficeInputModel>> getServicePostOffices(
      int serviceId, token) async {
    //TODO try catch
    var path = "/service/$serviceId/postoffices";

    return await ApiRequest.doGet(path, token)
        .then((value) => ServicePostOfficeInputModel.fromMap(value));
  }

  static Future<List<QueueInputModel>> getServicePostOfficeQueues(
      int postOfficeId, token) async {
    var path = "/service/postoffice/$postOfficeId/queues";

    return await ApiRequest.doGet(path, token)
        .then((value) => QueueInputModel.fromMap(value));
  }

  static Future<bool> createService(name, description, token) async {
    var path = "/service/";
    //TODO FIX THIS
    await ApiRequest.doPost(
        {"name": name, "description": description}, path, token);
    return true;
  }

  static Future<bool> createServicePostOffice(
      description, latitude, longitude, address, serviceId, token) async {
    var path = "/service/postoffice";
    //TODO FIX THIS
    await ApiRequest.doPost({
      "description": description,
      "latitude": latitude,
      "longitude": longitude,
      "address": address,
      "serviceId": serviceId
    }, path, token);
    return true;
  }

  static Future<bool> createServicePostOfficeQueue(
      name,
      description,
      letter,
      type,
      activeServers,
      maxAvailable,
      servicePostOfficeId,
      tolerance,
      token) async {
    var path = "/service/postoffice/queue";
    //TODO FIX THIS
    await ApiRequest.doPost({
      "name": name,
      "description": description,
      "letter": letter,
      "type": type,
      "activeServers": activeServers,
      "maxAvailable": maxAvailable,
      "servicePostOfficeId": servicePostOfficeId,
      "tolerance": tolerance
    }, path, token);
    return true;
  }

  static Future<bool> updateService(id, name, description, token) async {
    var path = "/service/$id/update";
    //TODO FIX THIS
    await ApiRequest.doPut(
        {"name": name, "description": description}, path, token);
    return true;
  }

  static Future<bool> updateServicePostOffice(
      id, description, latitude, longitude, address, token) async {
    var path = "/service/postoffice/$id/update";
    //TODO FIX THIS
    await ApiRequest.doPut({
      "description": description,
      "latitude": latitude,
      "longitude": longitude,
      "address": address
    }, path, token);
    return true;
  }

  static Future<bool> updateServicePostOfficeQueue(id, name, description,
      letter, type, activeServers, maxAvailable, tolerance, token) async {
    var path = "/service/postoffice/queue/$id/update";
    //TODO FIX THIS
    await ApiRequest.doPut({
      "name": name,
      "description": description,
      "letter": letter,
      "type" : type,
      "activeServers": activeServers,
      "maxAvailable": maxAvailable,
      "tolerance": tolerance
    }, path, token);
    return true;
  }

  static Future<bool> deleteService(id, token) async {
    var path = "/service/$id/delete";
    //TODO FIX THIS
    await ApiRequest.doPut({}, path, token);
    return true;
  }

  static Future<bool> deleteServicePostOffice(id, token) async {
    var path = "/service/postoffice/$id/delete";
    //TODO FIX THIS
    await ApiRequest.doPut({}, path, token);
    return true;
  }

  static Future<bool> deleteServicePostOfficeQueue(id, token) async {
    var path = "/service/postoffice/queue/$id/delete";
    //TODO FIX THIS
    await ApiRequest.doPut({}, path, token);
    return true;
  }
}
