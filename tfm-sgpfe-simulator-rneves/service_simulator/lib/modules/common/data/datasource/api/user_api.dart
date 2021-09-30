import '../../../../../core/network/api_request.dart';
import '../../model/user_input_model.dart';

class UserApi {
  static Future<UserInputModel> doLogin(
      String username, String password) async {
    //TODO try catch
    var path = "/login";
    var map = <String, String>{"username": username, "password": password};

    return await ApiRequest.doPost(map, path, null)
        .then((value) => UserInputModel.fromMap(value));
  }

  //todo maybe send the token - change type
  static Future<UserInputModel> doLogout(String username) async {
    //TODO try catch
    var path = "/logout";
    var map = <String, String>{
      "username": username,
    };

    return await ApiRequest.doPost(map, path, null)
        .then((value) => UserInputModel.fromMap(value));
  }

  static Future<bool> servingPostOfficeQueue(int queueId, token) async {
    var path = "/serving";

    await ApiRequest.doPut({
      "queueId": queueId,
      "serving": true,
    }, path, token);
    return true;
  }

  static Future<bool> leavePostOfficeQueue(int queueId, token) async {
    var path = "/serving";

    await ApiRequest.doPut({
      "queueId": queueId,
      "serving": false,
    }, path, token);
    return true;
  }
}
