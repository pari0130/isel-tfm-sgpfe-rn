import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

class User extends Equatable {
  final int userId;
  final String token;
  final bool authenticated;
  final String role;

  User(
      {@required this.userId,
      @required this.token,
      @required this.authenticated,
      @required this.role});

  @override
  List<Object> get props => [userId, token, authenticated, role];

  Map toMap() {
    return {
      'userId': userId,
      'token': token,
      'authenticated': authenticated,
      'role': role
    };
  }

  static User fromMap(Map<String, dynamic> map) {
    if (map == null) return null;
    User userInputModel;

    Map<String, dynamic> data = map["properties"];
    userInputModel = User(
        userId: data['userId'],
        token: data['token'],
        authenticated: data['authenticated'],
        role: data['role']);

    return userInputModel;
  }
}
