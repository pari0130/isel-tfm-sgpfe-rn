import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:service_simulator/core/utils/constants.dart';
import 'package:service_simulator/modules/common/presentation/widgets/common/loading_overlay.dart';
import '../../../../../../config/routes.dart';
import '../../../../../common/domain/entities/queue.dart';
import '../../../../../common/domain/entities/service_post_office.dart';
import '../../../bloc/service/post_office/service_postoffice_bloc.dart';
import '../../../bloc/service/post_office/service_postoffice_event.dart';
import '../../../bloc/service/post_office/service_postoffice_state.dart';
import '../../../../../common/presentation/widgets/common/loading_indicator.dart';
import '../post_office_page.dart';

class PostOfficeDetailsWidget extends StatefulWidget {
  @override
  State<PostOfficeDetailsWidget> createState() => _PostOfficeDetailsState();
}

class _PostOfficeDetailsState extends State<PostOfficeDetailsWidget> {
  final _latitudeController = TextEditingController();
  final _longitudeController = TextEditingController();
  final _descriptionController = TextEditingController();
  final _addressController = TextEditingController();

  final _queueNameDescriptionController = TextEditingController();
  final _queueCreateDescriptionController = TextEditingController();
  final _queueCreateLetterController = TextEditingController();
  final _queueCreateActiveServersController = TextEditingController();
  final _queueCreateMaxAvailableController = TextEditingController();

  void _cleanUpControllers() {
    _queueNameDescriptionController.clear();
    _queueCreateDescriptionController.clear();
    _queueCreateLetterController.clear();
    _queueCreateActiveServersController.clear();
    _queueCreateMaxAvailableController.clear();
  }

  List<String> _queueTypes = [
    "General Purpose",
    "Specific",
    "Priority"
  ]; //TODO fix this. get the types from the server
  List<String> _queueTolerance = ["No", "Yes"];

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
        BlocProvider.of<AdminServicePostOfficeBloc>(context)
            .add(ServicePostOfficeDelete(id: id));

        Navigator.of(context).pop();
      },
    );

    AlertDialog alert = AlertDialog(
      title: Text(
        "Post Office",
        style: TextStyle(
            fontFamily: "Lato Regular",
            fontWeight: FontWeight.bold,
            letterSpacing: 0.8,
            color: Theme.of(context).primaryColor,
            fontSize: 18.0),
      ),
      content: Text("Delete Post Office?",
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

  @override
  Widget build(BuildContext context) {
    final overlay = LoadingOverlay.of(context);

    final Map arguments = ModalRoute.of(context).settings.arguments as Map;
    ServicePostOffice postOffice = arguments["servicePostOffice"];
    _descriptionController.text = postOffice.description;
    _addressController.text = postOffice.address;
    _latitudeController.text = postOffice.latitude.toString();
    _longitudeController.text = postOffice.longitude.toString();

    return ListView(
      children: [
        Row(
          children: [
            Expanded(
              child: Container(
                margin: EdgeInsets.only(right: 10.0),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    SizedBox(height: 10),
                    Column(
                      mainAxisAlignment: MainAxisAlignment.start,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Container(
                          alignment: Alignment.topLeft,
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
                          alignment: Alignment.topLeft,
                          //width: 350, //TODO user media query
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
                            controller: _descriptionController,
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
                          alignment: Alignment.topLeft,
                          margin: EdgeInsets.only(left: 7.0),
                          child: Text(
                            "Address:",
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
                          alignment: Alignment.topLeft,
                          //width: 350, //TODO use media query
                          child: TextFormField(
                            minLines: 2,
                            maxLines: 3,
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
                            controller: _addressController,
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
                          alignment: Alignment.topLeft,
                          margin: EdgeInsets.only(left: 7.0),
                          child: Text(
                            "Latitude:",
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
                          alignment: Alignment.topLeft,
                          //width: 350, //TODO use media query
                          child: TextFormField(
                            maxLines: 1,
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
                            controller: _latitudeController,
                          ),
                        ),
                      ],
                    ),
                    SizedBox(
                      height: 10,
                    ),
                    Column(
                      mainAxisAlignment: MainAxisAlignment.start,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Container(
                          alignment: Alignment.topLeft,
                          margin: EdgeInsets.only(left: 7.0),
                          child: Text(
                            "Longitude:",
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
                          alignment: Alignment.topLeft,
                          //width: 350, //TODO use media query
                          child: TextFormField(
                            maxLines: 1,
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
                            controller: _longitudeController,
                          ),
                        ),
                        SizedBox(height: 20),
                        Row(
                          children: [
                            OutlineButton(
                              textColor: Theme.of(context).primaryColor,
                              highlightedBorderColor: Theme.of(context)
                                  .primaryColor
                                  .withOpacity(0.12),
                              onPressed: () {
                                overlay.show();

                                BlocProvider.of<AdminServicePostOfficeBloc>(
                                        context)
                                    .add(ServicePostOfficeUpdate(
                                  id: postOffice.id,
                                  description: _descriptionController.text,
                                  latitude: double.tryParse(
                                          _latitudeController.text) ??
                                      0.0,
                                  longitude: double.tryParse(
                                          _longitudeController.text) ??
                                      0.0,
                                  address: _addressController.text,
                                ));
                              },
                              child: Text("Update"),
                            ),
                            new Container(
                              width: 12.0,
                            ),
                            OutlineButton(
                              textColor: Theme.of(context).errorColor,
                              highlightedBorderColor: Theme.of(context)
                                  .errorColor
                                  .withOpacity(0.12),
                              onPressed: () {
                                showDialog(
                                  context: context,
                                  builder: (BuildContext bcontext) {
                                    return _popupDelete(context, postOffice.id);
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
            ),
            Expanded(
              child: Container(
                height: 420, //TODO FIX using media query, this is temporary
                child: Card(
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(3.0),
                  ),
                  child: Container(
                    alignment: Alignment.center,
                    child: Text(
                      "MAP WITH POST OFFICE LOCATION",
                      style: TextStyle(fontSize: 25),
                    ),
                  ),
                ),
              ),
            )
          ],
        ),
        SizedBox(height: 20),
        Container(
          alignment: Alignment.centerLeft,
          margin: EdgeInsets.only(left: 7.0),
          child: Text(
            "Queues: ",
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
        Container(
          height: 350,
          child: Column(
            children: [
              Flexible(
                child: BlocProvider(
                  create: (context) {
                    return BlocProvider.of<AdminServicePostOfficeBloc>(context)
                      ..add(ServicePostOfficeQueueFetch(
                          postOfficeId: postOffice.id));
                  },
                  child: BlocConsumer<AdminServicePostOfficeBloc,
                      AdminServicePostOfficeState>(
                    listener: (context, state) {
                      if (state is ServicePostOfficeQueueCreated) {
                        var text = "Service PostOffice Queue Not Created";
                        var color = Theme.of(context).errorColor;
                        if (state.success) {
                          text = "Service PostOffice Queue Created";
                          color = Colors.green;
                          BlocProvider.of<AdminServicePostOfficeBloc>(context)
                              .add(ServicePostOfficeQueueFetch(
                                  postOfficeId: postOffice.id));
                          _cleanUpControllers();
                        }
                        Scaffold.of(context).showSnackBar(
                          SnackBar(
                            content: Text(text),
                            backgroundColor: color,
                          ),
                        );
                      }
                      if (state is ServicePostOfficeUpdated) {
                        overlay.hide();
                        var text = "PostOffice Not Updated";
                        var color = Theme.of(context).errorColor;
                        if (state.success) {
                          text = "PostOffice Updated";
                          color = Colors.green;
                        }
                        PostOfficeDetailsPage.of(context).changed =
                            POST_OFFICE_CHANGED;

                        Scaffold.of(context).showSnackBar(
                          SnackBar(
                            content: Text(text),
                            backgroundColor: color,
                          ),
                        );
                      }
                      if (state is ServicePostOfficeDeleted) {
                        var text = "PostOffice Not Deleted";
                        var color = Theme.of(context).errorColor;
                        if (state.success) {
                          text = "PostOffice Deleted";
                          color = Colors.green;
                        }

                        Scaffold.of(context).showSnackBar(
                          SnackBar(
                            content: Text(text),
                            backgroundColor: color,
                          ),
                        );
                        if (state.success)
                          Navigator.of(context).pop(POST_OFFICE_CHANGED);
                      }
                    },
                    buildWhen: (previous, current) =>
                        current is! ServicePostOfficeQueueCreated &&
                        current is! ServicePostOfficeDeleted &&
                        current is! ServicePostOfficeUpdated,
                    builder: (context, state) {
                      if (state is ServicePostOfficeQueuesFetched) {
                        return _buildQueuesView(state.queues, postOffice.id);
                      }
                      return Container(
                        child: LoadingIndicator(color: Color(0xFF1766A6)),
                      );
                    },
                  ),
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }

  Widget _buildQueueCardCreate(context, servicePostOfficeId) {
    return Padding(
      padding: EdgeInsets.symmetric(horizontal: 4.0),
      child: InkWell(
        onTap: () async => {
          showDialog(
            context: context,
            builder: (bcontext) {
              return _popupCreate(context, servicePostOfficeId);
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

  Widget _popupCreate(context, servicePostOfficeId) {
    String _queueTypeDropdownValue;
    String _queueToleranceDropdownValue;

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
        BlocProvider.of<AdminServicePostOfficeBloc>(context)
            .add(ServicePostOfficeQueueCreate(
          name: _queueNameDescriptionController.text,
          description: _queueCreateDescriptionController.text,
          letter: _queueCreateLetterController.text,
          activeServers:
              int.tryParse(_queueCreateActiveServersController.text) ?? 0,
          maxAvailable:
              int.tryParse(_queueCreateMaxAvailableController.text) ?? 0,
          servicePostOfficeId: servicePostOfficeId,
          tolerance: _queueToleranceDropdownValue == 'Yes' ? true : false,
          type:
              _queueTypes.indexOf(_queueTypeDropdownValue) + 1, //TODO fix this
        ));
        Navigator.of(context).pop();
      },
    );

    AlertDialog alert = AlertDialog(
      title: Text("New Queue",
          style: TextStyle(
              fontFamily: "Lato Regular",
              fontWeight: FontWeight.bold,
              letterSpacing: 0.8,
              color: Theme.of(context).primaryColor,
              fontSize: 18.0)),
      content: StatefulBuilder(
        builder: (context, setState) {
          return Container(
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
                  decoration: InputDecoration(
                    hintText: 'Name',
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
                  controller: _queueNameDescriptionController,
                ),
                SizedBox(height: 10),
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
                  controller: _queueCreateDescriptionController,
                ),
                SizedBox(height: 10),
                TextFormField(
                  enabled: true,
                  cursorColor: Theme.of(context).primaryColor,
                  style: TextStyle(
                    color: Theme.of(context).primaryColor,
                  ),
                  decoration: InputDecoration(
                    hintText: 'Letter',
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
                  controller: _queueCreateLetterController,
                ),
                SizedBox(height: 10),
                Container(
                  padding: EdgeInsets.only(left: 8),
                  decoration: BoxDecoration(
                    border: Border.all(
                        color: Theme.of(context).primaryColor, width: 2.0),
                    borderRadius: BorderRadius.circular(10.0),
                  ),
                  child: DropdownButton(
                    value: _queueTypeDropdownValue,
                    hint: Text(
                      "Select Type",
                      style: TextStyle(color: Theme.of(context).primaryColor),
                    ),
                    isExpanded: true,
                    underline: Container(
                      color: Colors.transparent,
                    ),
                    onChanged: (String newValue) {
                      setState(() {
                        _queueTypeDropdownValue = newValue;
                      });
                    },
                    items: _queueTypes
                        .map<DropdownMenuItem<String>>((String value) {
                      return DropdownMenuItem<String>(
                        value: value,
                        child: Text(
                          value,
                          style: TextStyle(
                            fontFamily: 'Lato Regular',
                            fontSize: 15.0,
                            fontWeight: FontWeight.normal,
                          ),
                        ),
                      );
                    }).toList(),
                    style: TextStyle(
                      color: Theme.of(context).primaryColor,
                    ),
                    dropdownColor: Colors.white,
                  ),
                ),
                SizedBox(height: 10),
                TextFormField(
                  enabled: true,
                  cursorColor: Theme.of(context).primaryColor,
                  style: TextStyle(
                    color: Theme.of(context).primaryColor,
                  ),
                  decoration: InputDecoration(
                    hintText: 'Active Servers',
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
                  controller: _queueCreateActiveServersController,
                ),
                SizedBox(height: 10),
                TextFormField(
                  enabled: true,
                  cursorColor: Theme.of(context).primaryColor,
                  style: TextStyle(
                    color: Theme.of(context).primaryColor,
                  ),
                  decoration: InputDecoration(
                    hintText: 'Maximum Tickets Available',
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
                  controller: _queueCreateMaxAvailableController,
                ),
                SizedBox(height: 10),
                Container(
                  padding: EdgeInsets.only(left: 8),
                  decoration: BoxDecoration(
                    border: Border.all(
                        color: Theme.of(context).primaryColor, width: 2.0),
                    borderRadius: BorderRadius.circular(10.0),
                  ),
                  child: DropdownButton(
                    value: _queueToleranceDropdownValue,
                    hint: Text(
                      "Has Tolerance",
                      style: TextStyle(color: Theme.of(context).primaryColor),
                    ),
                    isExpanded: true,
                    underline: Container(
                      color: Colors.transparent,
                    ),
                    onChanged: (String newValue) {
                      setState(() {
                        _queueToleranceDropdownValue = newValue;
                      });
                    },
                    items: _queueTolerance
                        .map<DropdownMenuItem<String>>((String value) {
                      return DropdownMenuItem<String>(
                        value: value,
                        child: Text(
                          value,
                          style: TextStyle(
                            fontFamily: 'Lato Regular',
                            fontSize: 15.0,
                            fontWeight: FontWeight.normal,
                          ),
                        ),
                      );
                    }).toList(),
                    style: TextStyle(
                      color: Theme.of(context).primaryColor,
                    ),
                    dropdownColor: Colors.white,
                  ),
                ),
                SizedBox(height: 10),
              ],
            ),
          );
        },
      ),
      actions: [cancelButton, createButton],
    );
    return alert;
  }

  Widget _buildQueuesView(List<Queue> queues, servicePostOfficeId) {
    return Container(
      child: GridView.builder(
        itemCount: queues.length + 1,
        itemBuilder: (context, index) {
          if (index == queues.length)
            return _buildQueueCardCreate(context, servicePostOfficeId);
          return _buildQueueCard(queues[index], context);
        },
        gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
          maxCrossAxisExtent: 300.0,
          crossAxisSpacing: 10.0,
          mainAxisSpacing: 10.0,
        ),
      ),
    );
  }

  Widget _buildQueueCard(Queue queue, context) {
    return Padding(
      padding: EdgeInsets.symmetric(horizontal: 4.0),
      child: InkWell(
        onTap: () => {
          Navigator.of(context).pushNamed(FEIRoutes.QUEUE_DETAILS_ADMIN,
              arguments: <String, dynamic>{"queue": queue}).then((value) {
            if (value != null && value == QUEUE_CHANGED)
              BlocProvider.of<AdminServicePostOfficeBloc>(context)
                ..add(ServicePostOfficeQueueFetch(
                    postOfficeId: queue.servicePostOfficeId));
          })
        },
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
                      queue.name,
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
