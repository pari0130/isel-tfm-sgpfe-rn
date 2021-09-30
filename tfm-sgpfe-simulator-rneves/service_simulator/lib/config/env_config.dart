import 'package:meta/meta.dart';

enum Environment { DEV, QA, PROD }

class EnvValues {
  final String serverUrl;

  EnvValues({@required this.serverUrl});
}

class EnvConfig {
  final Environment environment;
  final String name;
  final EnvValues values;

  static EnvConfig _instance;

  factory EnvConfig(
      {@required Environment environment, @required EnvValues values}) {
    _instance ??= EnvConfig._internal(
        environment, environment.toString().split('.').last, values);

    return _instance;
  }

  EnvConfig._internal(this.environment, this.name, this.values);

  static EnvConfig getInstance() {
    return _instance;
  }

  static bool isProduction() => _instance.environment == Environment.PROD;

  static bool isDevelopment() => _instance.environment == Environment.DEV;

  static bool isQA() => _instance.environment == Environment.QA;
}
