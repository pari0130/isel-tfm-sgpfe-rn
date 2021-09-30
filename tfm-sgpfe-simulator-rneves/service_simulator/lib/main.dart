import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:service_simulator/modules/server_admin/presentation/bloc/service/details/details_bloc.dart';
import 'package:service_simulator/modules/server_admin/presentation/pages/service/service_page.dart';

import 'config/env_config.dart';
import 'config/fei_application.dart';
import 'config/routes.dart';
import 'config/types.dart';
import 'core/utils/cache/cache.dart';
import 'core/utils/constants.dart';
import 'core/utils/roles.dart';
import 'modules/admin/presentation/bloc/service/details/details_bloc.dart';
import 'modules/admin/presentation/bloc/service/post_office/service_postoffice_bloc.dart';
import 'modules/admin/presentation/bloc/service/queue/queue_bloc.dart';
import 'modules/admin/presentation/bloc/service/service_bloc.dart';
import 'modules/admin/presentation/pages/home/home_page.dart';
import 'modules/admin/presentation/pages/post_office/post_office_page.dart';
import 'modules/admin/presentation/pages/queue/queue_page.dart';
import 'modules/admin/presentation/pages/service_details/service_details_page.dart';
import 'modules/admin/presentation/pages/services/service_page.dart';
import 'modules/common/presentation/bloc/authentication/authentication_bloc.dart';
import 'modules/common/presentation/bloc/authentication/authentication_event.dart';
import 'modules/common/presentation/bloc/authentication/authentication_state.dart';
import 'modules/common/presentation/pages/login/login_page.dart';
import 'modules/common/presentation/pages/splash/splash_page.dart';
import 'modules/server/presentation/bloc/queue/queue_bloc.dart';
import 'modules/server/presentation/pages/queue/queue_page.dart';

void commonMain() {
  WidgetsFlutterBinding.ensureInitialized();

  FeiApplication application = FeiApplication();
  runZoned(() {
    runApp(
      BlocProvider<AuthenticationBloc>(
        create: (context) {
          return AuthenticationBloc(
              userRepository:
                  application.getRepository(RepositoryType.USER_REPOSITORY),
              serviceRepository:
                  application.getRepository(RepositoryType.SERVICE_REPOSITORY))
            ..add(AppStarted());
        },
        child: FEISimulatorApp(),
      ),
    );
  }, onError: (dynamic error, dynamic stackTrace) {
    print("Error $error");
    print("StackTrace $stackTrace");
  });
}

class FEISimulatorApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'FEI Simulator',
      theme: ThemeData(
          primaryColor: Color(0xFF1766A6),
          accentColor: Color(0xFF1D82D3),
          backgroundColor: Colors.white,
          highlightColor: Color(0xFF1766A6),
          fontFamily: 'OpenSans2',
          errorColor: Colors.red),
      //home: MyHomePage(title: 'FEI Service Simulator'),
      routes: {
        FEIRoutes.INITIAL: (context) =>
            BlocConsumer<AuthenticationBloc, AuthenticationState>(
              builder: (context, state) {
                if (state is AuthenticationUninitialized) {
                  return SplashPage();
                }
                if (state is AuthenticationUnauthenticated) {
                  return AnimatedSwitcher(
                    child: LoginPage(),
                    duration: Duration(milliseconds: 250),
                  );
                }
                return SplashPage();
              },
              listener: (context, state) {
                if (state is AuthenticationAuthenticated) {
                  //Navigator.of(context).popAndPushNamed(FEIRoutes.SERVICE);
                  if (state.role == Roles.ADMIN.name) {
                    Navigator.of(context).pushNamed(FEIRoutes.HOME_ADMIN).then(
                        (value) => BlocProvider.of<AuthenticationBloc>(context)
                            .add(BackToLogin()));
                  } else if (state.role == Roles.SERVER.name) {
                    Navigator.of(context).pushNamed(FEIRoutes.QUEUE).then(
                        (value) => BlocProvider.of<AuthenticationBloc>(context)
                            .add(BackToLogin()));
                  } else {
                    Navigator.of(context).pushNamed(
                      FEIRoutes.SERVICE_DETAILS_SERVICE_ADMIN,
                      arguments: <String, dynamic>{
                        "service_id": state.service.id,
                        "service_name": state.service.name,
                        "service_description": state.service.description
                      },
                    ).then((value) =>
                        BlocProvider.of<AuthenticationBloc>(context)
                            .add(BackToLogin()));
                  }
                }
                if (state is AuthenticationUnauthenticated) {
                  /*(FeiApplication().getComponent(ComponentType.CACHE_COMPONENT)
                          as Cache)
                      .reset();*/
                }
              },
            ),
        FEIRoutes.QUEUE: (context) => BlocProvider(
              create: (context) {
                return QueueBloc(
                    authenticationBloc:
                        BlocProvider.of<AuthenticationBloc>(context),
                    queueRepository: FeiApplication()
                        .getRepository(RepositoryType.QUEUE_REPOSITORY));
              },
              child: QueuePage(),
            ),
        FEIRoutes.SERVICE_ADMIN: (context) => BlocProvider(
              create: (context) {
                return AdminServiceBloc(
                    authenticationBloc:
                        BlocProvider.of<AuthenticationBloc>(context),
                    serviceRepository: FeiApplication()
                        .getRepository(RepositoryType.SERVICE_REPOSITORY));
              },
              child: AdminServicePage(),
            ),
        FEIRoutes.HOME_ADMIN: (context) {
          return AdminHomepage();
        },
        FEIRoutes.SERVICE_DETAILS_ADMIN: (context) => BlocProvider(
              create: (context) {
                return AdminServiceDetailsBloc(
                    authenticationBloc:
                        BlocProvider.of<AuthenticationBloc>(context),
                    serviceRepository: FeiApplication()
                        .getRepository(RepositoryType.SERVICE_REPOSITORY));
              },
              child: ServiceDetailsPage(),
            ),
        FEIRoutes.SERVICE_POST_OFFICE_DETAILS_ADMIN: (context) => BlocProvider(
              create: (context) {
                return AdminServicePostOfficeBloc(
                  authenticationBloc:
                      BlocProvider.of<AuthenticationBloc>(context),
                  serviceRepository: FeiApplication()
                      .getRepository(RepositoryType.SERVICE_REPOSITORY),
                  queueRepository: FeiApplication()
                      .getRepository(RepositoryType.QUEUE_REPOSITORY),
                );
              },
              child: PostOfficeDetailsPage(),
            ),
        FEIRoutes.QUEUE_DETAILS_ADMIN: (context) => BlocProvider(
              create: (context) {
                return AdminQueueDetailsBloc(
                    authenticationBloc:
                        BlocProvider.of<AuthenticationBloc>(context),
                    serviceRepository: FeiApplication()
                        .getRepository(RepositoryType.SERVICE_REPOSITORY));
              },
              child: QueueDetailsPage(),
            ),
        FEIRoutes.SERVICE_DETAILS_SERVICE_ADMIN: (context) => BlocProvider(
              create: (context) {
                return ServiceAdminServiceDetailsBloc(
                    authenticationBloc:
                        BlocProvider.of<AuthenticationBloc>(context),
                    serviceRepository: FeiApplication()
                        .getRepository(RepositoryType.SERVICE_REPOSITORY));
              },
              child: ServiceAdminPage(),
            ),
      },
      initialRoute: FEIRoutes.INITIAL,
      debugShowCheckedModeBanner: false,
    );
  }
}
