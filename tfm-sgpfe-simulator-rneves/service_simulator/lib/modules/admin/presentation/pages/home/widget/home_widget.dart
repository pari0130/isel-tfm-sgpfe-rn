import 'package:equatable/equatable.dart';
import 'package:flutter/material.dart';
import '../../../../../common/presentation/widgets/common/loading_overlay.dart';

import '../../../../../../config/routes.dart';

class HomePageItem extends Equatable {
  final String name;
  final String route;

  const HomePageItem({@required this.name, @required this.route});

  @override
  List<Object> get props => [name];
}

class AdminHomepageWidget extends StatelessWidget {
  final List<HomePageItem> items = [
    HomePageItem(name: "Services", route: FEIRoutes.SERVICE_ADMIN),
    HomePageItem(name: "Users", route: FEIRoutes.SERVICE_USERS_ADMIN)
  ];

  @override
  Widget build(BuildContext context) {
    //return _buildHomeView();
    final overlay = LoadingOverlay.of(context);

    return Column(
      children: [
        Container(
          height: 300,
          child: _buildHomeView(),
        ),
        /*Container(
          child: FlatButton(
            child: Text("test overlay"),
            onPressed: () {
              overlay.during(Future.delayed(Duration(seconds: 5)));
            },
          ),
        ),*/
      ],
    );
  }

  Widget _buildHomeView() {
    return Container(
      child: GridView.builder(
        itemCount: items.length,
        itemBuilder: (context, index) {
          return _buildHomeCard(items[index], context);
        },
        gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
          maxCrossAxisExtent: 250.0,
          crossAxisSpacing: 10.0,
          mainAxisSpacing: 10.0,
        ),
      ),
    );
  }

  Widget _buildHomeCard(HomePageItem homeItem, context) {
    return Padding(
      padding: EdgeInsets.symmetric(horizontal: 4.0),
      child: InkWell(
        onTap: () => {Navigator.of(context).pushNamed(homeItem.route)},
        child: Card(
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(3.0),
          ),
          child: Container(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Expanded(
                  flex: 0,
                  child: Container(
                    color: Colors.black,
                    child: Image.asset(
                      'assets/background.png',
                      fit: BoxFit.fill,
                    ),
                  ),
                ),
                Expanded(
                  flex: 2,
                  child: Container(
                    alignment: Alignment.center,
                    child: Text(
                      homeItem.name,
                      textAlign: TextAlign.left,
                      style: TextStyle(
                        fontFamily: "Oswald Medium",
                        fontWeight: FontWeight.normal,
                        color: Color(0xFF1766A6),
                        fontSize: 20.0,
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
