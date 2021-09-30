import 'config/env_config.dart';
import 'core/utils/constants.dart';
import 'main.dart';

void main() {
  EnvConfig(
      environment: Environment.PROD, values: EnvValues(serverUrl: HOST_PROD));
  commonMain();
}
