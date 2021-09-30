import 'package:meta/meta.dart';

import '../../domain/entities/user.dart';

class UserInputModel extends User {
  UserInputModel(
      {@required int userId,
      @required String token,
      @required bool authenticated,
      @required String role})
      : super(
            userId: userId,
            token: token,
            authenticated: authenticated,
            role: role);

  Map toMap() {
    return {
      'userId': userId,
      'token': token,
      'authenticated': authenticated,
      'role': role
    };
  }

  static UserInputModel fromMap(Map<String, dynamic> map) {
    if (map == null) return null;
    UserInputModel userInputModel;

    Map<String, dynamic> data = map["properties"];
    userInputModel = UserInputModel(
        userId: data['userId'],
        token: data['token'],
        authenticated: data['authenticated'],
        role: data['role']);

    return userInputModel;
  }
}
