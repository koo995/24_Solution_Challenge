import 'dart:convert';
import 'package:front_flutter/callapi.dart';
import 'package:front_flutter/view/missioncamera_page.dart';
import 'package:provider/provider.dart';
import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:http/http.dart' as http;

class ChallengeDetail extends StatefulWidget {
   ChallengeDetail( {super.key, required this.i});

  final int i;

  @override
  State<ChallengeDetail> createState() => _ChallengeDetailState();
}

class _ChallengeDetailState extends State<ChallengeDetail> {

  var data = {};
  Future<void> _apiRequest(String token) async {
    try {
      String mytoken = token;
      final response = await http.get(
        Uri.parse('http://34.47.91.250:8080/api/v1/mission/${widget.i+1}'),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': 'Bearer $mytoken',
        },
      );
      if (response.statusCode == 200) {
        print('API Response: ${jsonDecode(utf8.decode(response.bodyBytes))}');
        setState(() {
          data = jsonDecode(utf8.decode(response.bodyBytes));
        });
        // 여기서 응답을 처리하거나 상태를 업데이트할 수 있습니다.
      } else {
        print('API Request Failed with Status Code: ${response.statusCode}');
        // 실패한 경우에 대한 처리를 추가할 수 있습니다.
      }
    } catch (error) {
      print('Error during API Request: $error');
    }
  }
  @override
  void initState() {
    super.initState();
    _apiRequest(Provider.of<Store1>(context, listen: false).token);
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => MCameraPage()),
          );
        },
        elevation: 0.5,
        child: Icon(Icons.camera),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
      appBar: AppBar(
        actions: [],
      ),
      body: ListView(
        children: [
                    Container(
            width: 100,
            height: 100,
            decoration: BoxDecoration(
              color: Colors.blue,
            ),
            child: Column(
              children: [
                Text('제목 :  ${data['title']}',
                  style: TextStyle(
                    fontSize: 20,
                    color: Colors.black,
                  ),
                ),
                Text('설명 :  ${data['description']}',
                  style: TextStyle(
                    fontSize: 20,
                    color: Colors.black,
                  ),
                ),
                Text('학명 :  ${data['speciesName']}',
                  style: TextStyle(
                    fontSize: 20,
                    color: Colors.black,
                  ),
                ),
              ],
            ),
          ),
          Container(
            width: 250 ,
            height: 250,
            child: Image.network(
              '${data['imageUrl']}',
              loadingBuilder: (BuildContext context, Widget child, ImageChunkEvent? loadingProgress) {
                if (loadingProgress == null) {
                  return child;
                } else {
                  return Center(
                    child: CircularProgressIndicator(
                      value: loadingProgress.expectedTotalBytes != null
                          ? loadingProgress.cumulativeBytesLoaded / (loadingProgress.expectedTotalBytes ?? 1)
                          : null,
                    ),
                  );
                }
              },
            ), //여기에 사진 받아야함
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
        ],
      )
    );
  }
}
