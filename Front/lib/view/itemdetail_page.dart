import 'dart:async';
import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:provider/provider.dart';
import 'package:http/http.dart' as http;


class ItemDetail extends StatelessWidget {
  const ItemDetail({super.key});
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        actions: [],
      ),
      body: ListView(
        children:[
          Container(
            child:Image.asset('assets/images/sample.jpg')//여기에 사진 받아야함
          ),
          Container(
            width: 100,
            height: 100,
            decoration:BoxDecoration(
                color: Colors.blue,
              ),
            child:Text('',
            style: TextStyle(
              fontSize: 20,
              color: Colors.black,
            ),
          ),
          ),
          Container(
            width: 100,
            height: 400,
            child: GoogleMap(
                initialCameraPosition: CameraPosition(
                  target: LatLng(37.5665, 126.9780),
                  zoom: 15,
                ),
              ),
            ),
        ]
      ),
    );
  }
}