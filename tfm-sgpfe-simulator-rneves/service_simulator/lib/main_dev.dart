import 'config/env_config.dart';
import 'core/utils/constants.dart';
import 'main.dart';

void main() {
  EnvConfig(
      environment: Environment.DEV, values: EnvValues(serverUrl: HOST_DEV));
  commonMain();
}
