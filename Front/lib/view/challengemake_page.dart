import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:provider/provider.dart';

import '../callapi.dart';
class ChallengeMake extends StatelessWidget {
  final int speciesId;
  final int imageId;

  ChallengeMake({Key? key, required this.speciesId, required this.imageId})
      : super(key: key);

  TextEditingController titleController = TextEditingController();
  TextEditingController descriptionController = TextEditingController();

  Future<void> _apiRequest(
      String token, String title, String description, int speciesId, int imageId) async {
    try {
      String myToken = token;
      final response = await http.post(
        Uri.parse('http://34.47.91.250:8080/api/v1/mission'),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': 'Bearer $myToken',
        },
        body: jsonEncode({
          'title': title,
          'description': description,
          'speciesId': speciesId.toString(),
          'imageId': imageId.toString(),
        }),
      );

      if (response.statusCode == 200) {
        print('API Response: ${response.body}');
      } else {
        print('API Request Failed with Status Code: ${response.statusCode}');
      }
    } catch (error) {
      print('Error during API Request: $error');
    }
  }
  @override
  Widget build(BuildContext context) {
    print('speciesId: $speciesId');
    print('imageId: $imageId');
    return Scaffold(
      appBar: AppBar(
        title: Text('Create Challenge'),
      ),
      body: SingleChildScrollView(
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Container(
                child: Text(
                  'Create Challenge',
                  style: TextStyle(
                    fontSize: 30,
                    color: Colors.black,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
              Container(
                margin: EdgeInsets.all(20),
                child: TextField(
                  controller: titleController,
                  decoration: InputDecoration(
                    border: OutlineInputBorder(
                      borderSide: BorderSide(color: Colors.lightGreen),
                    ),
                    labelText: 'Title',
                  ),
                ),
              ),
              Container(
                margin: EdgeInsets.all(20),
                child: TextField(
                  controller: descriptionController,
                  decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Description',
                  ),
                ),
              ),
              Container(
                margin: EdgeInsets.all(20),
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom(
                    primary: Colors.lightGreen,
                    onPrimary: Colors.white,
                  ),
                  onPressed: () {
                    _apiRequest(
                      Provider.of<Store1>(context, listen: false).token,
                      titleController.text,
                      descriptionController.text,
                      speciesId,
                      imageId,
                    );
                  },
                  child: Text('Submit'),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}