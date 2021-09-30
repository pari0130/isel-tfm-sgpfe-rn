import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:service_simulator/core/utils/constants.dart';
import 'package:service_simulator/modules/admin/presentation/bloc/service/queue/queue_event.dart';
import 'package:service_simulator/modules/common/presentation/widgets/common/loading_overlay.dart';
import '../../../bloc/service/queue/queue_bloc.dart';
import '../../../bloc/service/queue/queue_state.dart';
import '../../../../../common/domain/entities/queue.dart';
import '../queue_page.dart';

class QueueDetailsWidget extends StatefulWidget {
  @override
  State<QueueDetailsWidget> createState() => _QueueDetailsState();
}

class _QueueDetailsState extends State<QueueDetailsWidget> {
  final _queueNameController = TextEditingController();
  final _queueDescriptionController = TextEditingController();
  final _queueLetterController = TextEditingController();
  final _queueActiveServersController = TextEditingController();
  final _queueMaxAvailableController = TextEditingController();

  List<String> _queueTypes = [
    "General Purpose",
    "Specific",
    "Priority"
  ]; //TODO fix this. get the types from the server
  List<String> _queueTolerance = ["No", "Yes"];

  String _queueTypeDropdownValue;
  String _queueToleranceDropdownValue;

  Widget _popupDelete(context, queueId) {
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
        BlocProvider.of<AdminQueueDetailsBloc>(context)
            .add(QueueDetailsDelete(id: queueId));

        Navigator.of(context).pop();
      },
    );

    AlertDialog alert = AlertDialog(
      title: Text(
        "Queue",
        style: TextStyle(
            fontFamily: "Lato Regular",
            fontWeight: FontWeight.bold,
            letterSpacing: 0.8,
            color: Theme.of(context).primaryColor,
            fontSize: 18.0),
      ),
      content: Text("Delete queue?",
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
    Queue queue = arguments["queue"];

    _queueNameController.text = queue.name;
    _queueDescriptionController.text = queue.description;
    _queueLetterController.text = queue.letter;
    _queueActiveServersController.text = queue.activeServers.toString();
    _queueMaxAvailableController.text = queue.maxAvailable.toString();

    setState(() {
      _queueTypeDropdownValue = _queueTypes[queue.type - 1];
      _queueToleranceDropdownValue = queue.tolerance ? "Yes" : "No";
    });

    return BlocProvider(
      create: (context) {
        return BlocProvider.of<AdminQueueDetailsBloc>(context);
      },
      child: BlocListener<AdminQueueDetailsBloc, AdminQueueDetailsState>(
        listener: (BuildContext context, state) {
          if (state is QueueDetailsUpdated) {
            overlay.hide();
            var text = "Queue Not Updated";
            var color = Theme.of(context).errorColor;
            if (state.success) {
              text = "Queue Updated";
              color = Colors.green;
            }
            QueueDetailsPage.of(context).changed = QUEUE_CHANGED;

            Scaffold.of(context).showSnackBar(
              SnackBar(
                content: Text(text),
                backgroundColor: color,
              ),
            );
          }
          if (state is QueueDetailsDeleted) {
            var text = "Queue Not Deleted";
            var color = Theme.of(context).errorColor;
            if (state.success) {
              text = "Queue Deleted";
              color = Colors.green;
            }

            Scaffold.of(context).showSnackBar(
              SnackBar(
                content: Text(text),
                backgroundColor: color,
              ),
            );
            if (state.success) Navigator.of(context).pop(QUEUE_CHANGED);
          }
        },
        child: StatefulBuilder(
          builder: (context, setState) {
            return Container(
              child: ListView(
                //mainAxisAlignment: MainAxisAlignment.start,
                //crossAxisAlignment: CrossAxisAlignment.start,
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
                      controller: _queueNameController,
                    ),
                  ),
                  SizedBox(height: 10),
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
                      controller: _queueDescriptionController,
                    ),
                  ),
                  SizedBox(height: 10),
                  Container(
                    alignment: Alignment.centerLeft,
                    margin: EdgeInsets.only(left: 7.0),
                    child: Text(
                      "Letter: ",
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
                    child: TextFormField(
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
                      controller: _queueLetterController,
                    ),
                  ),
                  SizedBox(height: 10),
                  Container(
                    alignment: Alignment.centerLeft,
                    margin: EdgeInsets.only(left: 7.0),
                    child: Text(
                      "Type: ",
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
                  Container(
                    alignment: Alignment.centerLeft,
                    margin: EdgeInsets.only(left: 7.0),
                    child: Text(
                      "Active Servers: ",
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
                    child: TextFormField(
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
                      controller: _queueActiveServersController,
                    ),
                  ),
                  SizedBox(height: 10),
                  Container(
                    alignment: Alignment.centerLeft,
                    margin: EdgeInsets.only(left: 7.0),
                    child: Text(
                      "Maximum Available Tickets: ",
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
                    child: TextFormField(
                      enabled: true,
                      cursorColor: Theme.of(context).primaryColor,
                      style: TextStyle(
                        color: Theme.of(context).primaryColor,
                      ),
                      decoration: InputDecoration(
                        hintText: 'Maximum Available Tickets',
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
                      controller: _queueMaxAvailableController,
                    ),
                  ),
                  SizedBox(height: 10),
                  Container(
                    alignment: Alignment.centerLeft,
                    margin: EdgeInsets.only(left: 7.0),
                    child: Text(
                      "Tolerance: ",
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
                    padding: EdgeInsets.only(left: 8),
                    decoration: BoxDecoration(
                      border: Border.all(
                          color: Theme.of(context).primaryColor, width: 2.0),
                      borderRadius: BorderRadius.circular(10.0),
                    ),
                    child: DropdownButton(
                      value: _queueToleranceDropdownValue,
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
                  SizedBox(height: 20),
                  Row(
                    children: [
                      OutlineButton(
                        textColor: Theme.of(context).primaryColor,
                        highlightedBorderColor:
                            Theme.of(context).primaryColor.withOpacity(0.12),
                        onPressed: () {
                          overlay.show();

                          BlocProvider.of<AdminQueueDetailsBloc>(context).add(
                              QueueDetailsUpdate(
                                  id: queue.id,
                                  name: _queueNameController.text,
                                  description: _queueDescriptionController.text,
                                  letter: _queueLetterController.text,
                                  activeServers: int.tryParse(
                                          _queueActiveServersController.text) ??
                                      1,
                                  maxAvailable: int.tryParse(
                                          _queueMaxAvailableController.text) ??
                                      100,
                                  type: _queueTypes
                                          .indexOf(_queueTypeDropdownValue) +
                                      1,
                                  tolerance:
                                      _queueToleranceDropdownValue == "Yes"));
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
                              return _popupDelete(context, queue.id);
                            },
                          );
                        },
                        child: Text("Delete"),
                      )
                    ],
                  ),
                ],
              ),
            );
          },
        ),
      ),
    );
  }
}
