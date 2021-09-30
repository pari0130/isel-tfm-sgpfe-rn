import 'package:meta/meta.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../../../../core/security/utils.dart';
import '../../../../core/utils/cache/cache.dart';
import '../../domain/entities/user.dart';
import '../datasource/api/user_api.dart';

class UserRepository {
  static const String SIGNED_IN_USER = "signed_in_user";
  final Cache cache;

  UserRepository({@required this.cache}) : assert(cache != null);

  Future<User> login(String username, String password) async {
    try {
      return UserApi.doLogin(username, SecurityUtils.computePassword(password));
    } catch (error) {
      return null;
    }
  }

  Future<void> logout(String username) async {
    try {
      UserApi.doLogout(username);
    } catch (error) {}
    return;
  }

  Future<void> persistUser(User user) async {
    //cache.write(SIGNED_IN_USER, user.toMap());
    //cache.write(SIGNED_IN_USER+"_", user);

    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.setString(SIGNED_IN_USER, user.token);
    return;
  }

  Future<String> getUserToken() async {
    //User user = User.fromMap(cache.read(SIGNED_IN_USER) as Map);
    //User user_ = cache.read(SIGNED_IN_USER+"_") as User;

    //print(user);
    //String token = (cache.read(SIGNED_IN_USER) as User).token;
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String token = prefs.getString(SIGNED_IN_USER);
    return token;
  }

  Future<bool> servingPostOfficeQueue(queueId, token) async {
    try {
      return UserApi.servingPostOfficeQueue(queueId, token);
    } catch (error) {
      return false;
    }
  }

  Future<bool> leavePostOfficeQueue(queueId, token) async {
    try {
      return UserApi.leavePostOfficeQueue(queueId, token);
    } catch (error) {
      return false;
    }
  }
}
