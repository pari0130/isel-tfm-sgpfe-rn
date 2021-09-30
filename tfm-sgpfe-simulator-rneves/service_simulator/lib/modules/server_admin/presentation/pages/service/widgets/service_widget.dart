import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:service_simulator/core/utils/constants.dart';
import 'package:service_simulator/modules/common/presentation/widgets/common/loading_overlay.dart';

import '../../../../../../config/routes.dart';
import '../../../../../common/domain/entities/service_post_office.dart';
import '../../../../../common/presentation/widgets/common/loading_indicator.dart';
import '../../../bloc/service/details/details_bloc.dart';
import '../../../bloc/service/details/details_event.dart';
import '../../../bloc/service/details/details_state.dart';
import '../service_page.dart';

class ServiceAdminDetailsWidget extends StatefulWidget {
  @override
  State<ServiceAdminDetailsWidget> createState() => _ServiceDetailsState();
}

class _ServiceDetailsState extends State<ServiceAdminDetailsWidget> {
  final _serviceNameController = TextEditingController();
  final _serviceDescriptionController = TextEditingController();

  final _servicePostOfficeCreateDescriptionController = TextEditingController();
  final _servicePostOfficeCreateAddressController = TextEditingController();
  final _servicePostOfficeCreateLatitudeController = TextEditingController();
  final _servicePostOfficeCreateLongitudeController = TextEditingController();

  void _cleanUpControllers() {
    _servicePostOfficeCreateDescriptionController.clear();
    _servicePostOfficeCreateAddressController.clear();
    _servicePostOfficeCreateLatitudeController.clear();
    _servicePostOfficeCreateLongitudeController.clear();
  }

  @override
  Widget build(BuildContext context) {
    final overlay = LoadingOverlay.of(context);

    final Map arguments = ModalRoute.of(context).settings.arguments as Map;
    _serviceNameController.text = arguments["service_name"];
    _serviceDescriptionController.text = arguments["service_description"];
    return Column(
      children: [
        Container(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Column(
                mainAxisAlignment: MainAxisAlignment.start,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Container(
                    alignment: Alignment.centerLeft,
                    margin: EdgeInsets.only(left: 7.0),
                    child: Text(
                      "Name:",
                      textAlign: TextAlign.left,
                      style: TextStyle(
                        fontFamily: "Oswald Medium",
                        fontWeight: FontWeight.normal,
                        color: Color(0xFF1766A6),
                        fontSize: 20.0,
                      ),
                    ),
                  ),
                  SizedBox(height: 5),
                  Container(
                    alignment: Alignment.centerLeft,
                    //width: 250, //TODO use media query
                    child: TextFormField(
                      enabled: true,
                      cursorColor: Theme.of(context).primaryColor,
                      style: TextStyle(
                        color: Theme.of(context).primaryColor,
                      ),
                      decoration: InputDecoration(
                        hintText: 'Name',
                        enabledBorder: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10.0),
                          borderSide: BorderSide(
                              color: Theme.of(context).primaryColor,
                              width: 2.0),
                        ),
                        disabledBorder: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10.0),
                          borderSide: BorderSide(
                              color: Theme.of(context).primaryColor,
                              width: 2.0),
                        ),
                        focusColor: Theme.of(context).primaryColor,
                        hintStyle: TextStyle(
                            fontSize: 15,
                            fontWeight: FontWeight.normal,
                            color: Theme.of(context).primaryColor),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10.0),
                          borderSide: BorderSide(
                              color: Theme.of(context).primaryColor,
                              width: 2.0),
                        ),
                        focusedBorder: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10.0),
                          borderSide: BorderSide(
                              color: Theme.of(context).primaryColor,
                              width: 2.0),
                        ),
                      ),
                      controller: _serviceNameController,
                    ),
                  ),
                ],
              ),
              SizedBox(height: 10),
              Column(
                mainAxisAlignment: MainAxisAlignment.start,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Container(
                    alignment: Alignment.centerLeft,
                    margin: EdgeInsets.only(left: 7.0),
                    child: Text(
                      "Description: ",
                      textAlign: TextAlign.left,
                      style: TextStyle(
                        fontFamily: "Oswald Medium",
                        fontWeight: FontWeight.normal,
                        color: Color(0xFF1766A6),
                        fontSize: 20.0,
                      ),
                    ),
                  ),
                  SizedBox(height: 5),
                  Container(
                    alignment: Alignment.centerLeft,
                    //width: 250, //TODO user media query
                    child: TextFormField(
                      enabled: true,
                      cursorColor: Theme.of(context).primaryColor,
                      style: TextStyle(
                        color: Theme.of(context).primaryColor,
                      ),
                      maxLines: 6,
                      minLines: 4,
                      decoration: InputDecoration(
                        hintText: 'Description',
                        enabledBorder: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10.0),
                          borderSide: BorderSide(
                              color: Theme.of(context).primaryColor,
                              width: 2.0),
                        ),
                        disabledBorder: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10.0),
                          borderSide: BorderSide(
                              color: Theme.of(context).primaryColor,
                              width: 2.0),
                        ),
                        focusColor: Theme.of(context).primaryColor,
                        hintStyle: TextStyle(
                            fontSize: 15,
                            fontWeight: FontWeight.normal,
                            color: Theme.of(context).primaryColor),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10.0),
                          borderSide: BorderSide(
                              color: Theme.of(context).primaryColor,
                              width: 2.0),
                        ),
                        focusedBorder: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10.0),
                          borderSide: BorderSide(
                              color: Theme.of(context).primaryColor,
                              width: 2.0),
                        ),
                      ),
                      controller: _serviceDescriptionController,
                    ),
                  ),
                  SizedBox(height: 20),
                  Row(
                    children: [
                      OutlineButton(
                        textColor: Theme.of(context).primaryColor,
                        highlightedBorderColor:
                            Theme.of(context).primaryColor.withOpacity(0.12),
                        onPressed: () {
                          overlay.show();
                          BlocProvider.of<ServiceAdminServiceDetailsBloc>(context).add(
                            ServiceDetailsUpdate(
                              id: arguments["service_id"],
                              name: _serviceNameController.text,
                              description: _serviceDescriptionController.text,
                            ),
                          );
                        },
                        child: Text("Update"),
                      ),
                      new Container(
                        width: 12.0,
                      ),
                      OutlineButton(
                        textColor: Theme.of(context).errorColor,
                        highlightedBorderColor:
                            Theme.of(context).errorColor.withOpacity(0.12),
                        onPressed: () {
                          showDialog(
                            context: context,
                            builder: (BuildContext bcontext) {
                              return _popupDelete(
                                  context, arguments["service_id"]);
                            },
                          );
                        },
                        child: Text("Delete"),
                      )
                    ],
                  ),
                ],
              ),
            ],
          ),
        ),
        SizedBox(height: 20),
        Container(
          alignment: Alignment.centerLeft,
          margin: EdgeInsets.only(left: 7.0),
          child: Text(
            "Post Offices: ",
            textAlign: TextAlign.left,
            style: TextStyle(
              fontFamily: "Oswald Medium",
              fontWeight: FontWeight.bold,
              color: Color(0xFF1766A6),
              fontSize: 20.0,
            ),
          ),
        ),
        SizedBox(height: 10),
        Flexible(
          child: BlocProvider(
            create: (context) {
              return BlocProvider.of<ServiceAdminServiceDetailsBloc>(context)
                ..add(
                    ServicePostOfficeFetch(serviceId: arguments["service_id"]));
            },
            child:
                BlocConsumer<ServiceAdminServiceDetailsBloc, ServiceAdminServiceDetailsState>(
              listener: (context, state) {
                if (state is ServicePostOfficeCreated) {
                  var text = "Service PostOffice Not Created";
                  var color = Theme.of(context).errorColor;
                  if (state.success) {
                    text = "Service PostOffice Created";
                    color = Colors.green;
                    BlocProvider.of<ServiceAdminServiceDetailsBloc>(context).add(
                        ServicePostOfficeFetch(
                            serviceId: arguments["service_id"]));
                    _cleanUpControllers();
                  }
                  Scaffold.of(context).showSnackBar(
                    SnackBar(
                      content: Text(text),
                      backgroundColor: color,
                    ),
                  );
                }
                if (state is ServiceDetailsUpdated) {
                  overlay.hide();
                  var text = "Service Not Updated";
                  var color = Theme.of(context).errorColor;
                  if (state.success) {
                    text = "Service Updated";
                    color = Colors.green;
                  }
                  ServiceAdminPage.of(context).changed = SERVICE_CHANGED;

                  Scaffold.of(context).showSnackBar(
                    SnackBar(
                      content: Text(text),
                      backgroundColor: color,
                    ),
                  );
                }
                if (state is ServiceDetailsDeleted) {
                  var text = "Service Not Deleted";
                  var color = Theme.of(context).errorColor;
                  if (state.success) {
                    text = "Service Deleted";
                    color = Colors.green;
                  }

                  Scaffold.of(context).showSnackBar(
                    SnackBar(
                      content: Text(text),
                      backgroundColor: color,
                    ),
                  );
                  if (state.success)
                    Navigator.of(context).pop(SERVICE_CHANGED);
                }
              },
              buildWhen: (previous, current) =>
                  current is! ServicePostOfficeCreated &&
                  current is! ServiceDetailsDeleted &&
                  current is! ServiceDetailsUpdated,
              builder: (context, state) {
                if (state is ServicePostOfficeFetched) {
                  return _buildPostOfficeView(
                      state.postOffices, arguments["service_id"]);
                }
                return Container(
                  child: LoadingIndicator(color: Color(0xFF1766A6)),
                );
              },
            ),
          ),
        ),
      ],
    );
  }

  Widget _buildServicePostOfficeCardCreate(context, serviceId) {
    return Padding(
      padding: EdgeInsets.symmetric(horizontal: 4.0),
      child: InkWell(
        onTap: () async => {
          showDialog(
            context: context,
            builder: (BuildContext bcontext) {
              return _popupCreate(context, serviceId);
            },
          ),
        },
        child: Card(
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(3.0),
          ),
          child: Container(
              alignment: Alignment.center,
              child: Icon(Icons.add, color: Colors.grey, size: 30)),
        ),
      ),
    );
  }

  Widget _popupDelete(context, id) {
    Widget cancelButton = FlatButton(
      child: Text(
        "Cancel",
        style: TextStyle(
            fontFamily: 'Oswald Bold',
            fontSize: 15,
            letterSpacing: 1.2,
            fontStyle: FontStyle.normal,
            color: Colors.redAccent),
      ),
      onPressed: () {
        Navigator.of(context).pop();
      },
    );

    Widget yesButton = FlatButton(
      child: Text(
        "Yes",
        style: TextStyle(
            fontFamily: 'Oswald Bold',
            fontSize: 15,
            letterSpacing: 1.2,
            fontStyle: FontStyle.normal,
            color: Theme.of(context).primaryColor),
      ),
      onPressed: () {
        BlocProvider.of<ServiceAdminServiceDetailsBloc>(context)
            .add(ServiceDetailsDelete(id: id));

        Navigator.of(context).pop();
      },
    );

    AlertDialog alert = AlertDialog(
      title: Text(
        "Service",
        style: TextStyle(
            fontFamily: "Lato Regular",
            fontWeight: FontWeight.bold,
            letterSpacing: 0.8,
            color: Theme.of(context).primaryColor,
            fontSize: 18.0),
      ),
      content: Text("Delete service?",
          style: TextStyle(
              fontFamily: "Lato Regular",
              fontWeight: FontWeight.normal,
              letterSpacing: 0.5,
              color: Theme.of(context).primaryColor,
              fontSize: 16.0)),
      actions: [cancelButton, yesButton],
    );

    return alert;
  }

  Widget _popupCreate(context, serviceId) {
    Widget cancelButton = FlatButton(
      child: Text(
        "Cancel",
        style: TextStyle(
            fontFamily: 'Oswald Bold',
            fontSize: 15,
            letterSpacing: 1.2,
            fontStyle: FontStyle.normal,
            color: Colors.redAccent),
      ),
      onPressed: () {
        Navigator.of(context).pop();
      },
    );

    Widget createButton = FlatButton(
      child: Text(
        "Create",
        style: TextStyle(
            fontFamily: 'Oswald Bold',
            fontSize: 15,
            letterSpacing: 1.2,
            fontStyle: FontStyle.normal,
            color: Theme.of(context).primaryColor),
      ),
      onPressed: () {
        BlocProvider.of<ServiceAdminServiceDetailsBloc>(context).add(
            ServicePostOfficeCreate(
                description: _servicePostOfficeCreateDescriptionController.text,
                latitude: double.tryParse(
                        _servicePostOfficeCreateLatitudeController.text) ??
                    0.0,
                longitude: double.tryParse(
                        _servicePostOfficeCreateLongitudeController.text) ??
                    0.0,
                address: _servicePostOfficeCreateAddressController.text,
                serviceId: serviceId));
        Navigator.of(context).pop();
      },
    );

    AlertDialog alert = AlertDialog(
      title: Text("New Service Post Office",
          style: TextStyle(
              fontFamily: "Lato Regular",
              fontWeight: FontWeight.bold,
              letterSpacing: 0.8,
              color: Theme.of(context).primaryColor,
              fontSize: 18.0)),
      content: Container(
        width: 500, //TODO use mediaquery
        child: Column(
          mainAxisSize: MainAxisSize.min,
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            TextFormField(
              enabled: true,
              cursorColor: Theme.of(context).primaryColor,
              style: TextStyle(
                color: Theme.of(context).primaryColor,
              ),
              minLines: 4,
              maxLines: 6,
              decoration: InputDecoration(
                hintText: 'Description',
                enabledBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
                disabledBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
                focusColor: Theme.of(context).primaryColor,
                hintStyle: TextStyle(
                    fontSize: 15,
                    fontWeight: FontWeight.normal,
                    color: Theme.of(context).primaryColor),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
                focusedBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
              ),
              controller: _servicePostOfficeCreateDescriptionController,
            ),
            SizedBox(height: 10),
            TextFormField(
              enabled: true,
              cursorColor: Theme.of(context).primaryColor,
              style: TextStyle(
                color: Theme.of(context).primaryColor,
              ),
              decoration: InputDecoration(
                hintText: 'Address',
                enabledBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
                disabledBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
                focusColor: Theme.of(context).primaryColor,
                hintStyle: TextStyle(
                    fontSize: 15,
                    fontWeight: FontWeight.normal,
                    color: Theme.of(context).primaryColor),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
                focusedBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
              ),
              controller: _servicePostOfficeCreateAddressController,
            ),
            SizedBox(height: 10),
            TextFormField(
              enabled: true,
              cursorColor: Theme.of(context).primaryColor,
              style: TextStyle(
                color: Theme.of(context).primaryColor,
              ),
              decoration: InputDecoration(
                hintText: 'Latitude',
                enabledBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
                disabledBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
                focusColor: Theme.of(context).primaryColor,
                hintStyle: TextStyle(
                    fontSize: 15,
                    fontWeight: FontWeight.normal,
                    color: Theme.of(context).primaryColor),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
                focusedBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
              ),
              controller: _servicePostOfficeCreateLatitudeController,
            ),
            SizedBox(height: 10),
            TextFormField(
              enabled: true,
              cursorColor: Theme.of(context).primaryColor,
              style: TextStyle(
                color: Theme.of(context).primaryColor,
              ),
              decoration: InputDecoration(
                hintText: 'Longitude',
                enabledBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
                disabledBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
                focusColor: Theme.of(context).primaryColor,
                hintStyle: TextStyle(
                    fontSize: 15,
                    fontWeight: FontWeight.normal,
                    color: Theme.of(context).primaryColor),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
                focusedBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide(
                      color: Theme.of(context).primaryColor, width: 2.0),
                ),
              ),
              controller: _servicePostOfficeCreateLongitudeController,
            ),
            SizedBox(height: 10),
          ],
        ),
      ),
      actions: [cancelButton, createButton],
    );
    return alert;
  }

  Widget _buildPostOfficeView(List<ServicePostOffice> postOffices, serviceId) {
    return Container(
      child: GridView.builder(
        itemCount: postOffices.length + 1,
        itemBuilder: (context, index) {
          if (index == postOffices.length)
            return _buildServicePostOfficeCardCreate(context, serviceId);
          return _buildPostOfficeCard(postOffices[index], context);
        },
        gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
          maxCrossAxisExtent: 300.0,
          crossAxisSpacing: 10.0,
          mainAxisSpacing: 10.0,
        ),
      ),
    );
  }

  Widget _buildPostOfficeCard(ServicePostOffice postOffice, context) {
    return Padding(
      padding: EdgeInsets.symmetric(horizontal: 4.0),
      child: InkWell(
        //TODO fix this
        onTap: () => Navigator.of(context).pushNamed(
            FEIRoutes.SERVICE_POST_OFFICE_DETAILS_ADMIN,
            arguments: <String, dynamic>{"servicePostOffice": postOffice}).then(
          (value) {
            if (value != null && value == POST_OFFICE_CHANGED)
              BlocProvider.of<ServiceAdminServiceDetailsBloc>(context)
                ..add(ServicePostOfficeFetch(serviceId: postOffice.serviceId));
          },
        ),
        child: Card(
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(3.0),
          ),
          child: Container(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Expanded(
                  flex: 0,
                  child: Container(
                    child: Image.asset(
                      'assets/background.jpg',
                      fit: BoxFit.fill,
                    ),
                  ),
                ),
                Expanded(
                  flex: 1,
                  child: Container(
                    alignment: Alignment.centerLeft,
                    margin: EdgeInsets.only(left: 7.0),
                    child: Text(
                      postOffice.description,
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
                Expanded(
                  flex: 1,
                  child: Container(
                    alignment: Alignment.centerLeft,
                    margin: EdgeInsets.only(left: 7.0),
                    child: Text(
                      postOffice.description,
                      textAlign: TextAlign.left,
                      style: TextStyle(
                        fontFamily: "Oswald Medium",
                        fontWeight: FontWeight.normal,
                        color: Colors.black,
                        fontSize: 16.0,
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
