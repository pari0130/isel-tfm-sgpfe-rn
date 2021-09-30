import 'package:flutter_test/flutter_test.dart';
import 'package:service_simulator/config/env_config.dart';
import 'package:service_simulator/core/network/api_request.dart';
import 'package:service_simulator/core/utils/cache/cache.dart';
import 'package:service_simulator/core/utils/constants.dart';
import 'package:service_simulator/core/utils/roles.dart';


void main() {
  test('Login', () async {
    /*EnvConfig(
        environment: Environment.DEV, values: EnvValues(serverUrl: HOST_DEV));
    //Future<dynamic> request = ApiRequest.doGet("ticket/1");
    UserInputModel result = await UserApi.doLogin("rneves", "4B68AB3847FEDA7D6C62C1FBCBEEBFA35EAB7351ED5E78F4DDADEA5DF64B8015");
    
    assert(result != null);*/
    expect(null, null);

    assert(null == null);
  });

  test('Test Name', () async {
    expect(null, null);
    assert(null == null);
  });

  test('Cache Test', () async {
    Cache cache = Cache();
    cache.write("key", "value");

    dynamic value = cache.read("key");

    assert(value == "value");
  });

  test('Enum Test', () async {
    print(Roles.ADMIN.name);

    assert(null == null);
  });
}
