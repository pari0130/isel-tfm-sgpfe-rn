enum Roles { ADMIN, SERVER, USER, SERVICE_ADMIN }

extension RolesExtension on Roles {
  static const roles = {
    Roles.ADMIN: 'ADMIN',
    Roles.SERVER: 'SERVER',
    Roles.USER: 'USER',
    Roles.SERVICE_ADMIN: 'SERVICE_ADMIN'
  };

  String get name => roles[this];
}
