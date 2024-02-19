
import 'dart:io';
import 'package:http/http.dart' as http;
import 'package:image_picker/image_picker.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:provider/provider.dart';

import '../callapi.dart';

class MCameraPage extends StatefulWidget {
  const MCameraPage({super.key});

  @override
  State<MCameraPage> createState() => _MCameraPageState();
}

class _MCameraPageState extends State<MCameraPage> {
  File? _image;
  final picker = ImagePicker();

  // 비동기 처리를 통해 카메라와 갤러리에서 이미지를 가져온다.
  Future getImage(ImageSource imageSource) async {
    final image = await picker.pickImage(source: imageSource, imageQuality: 50);
    setState(() {
      _image = File(image!.path); // 가져온 이미지를 _image에 저장

      Future<void> _imageUpload(File imageFile, String token) async {
        try {
          String myToken = token;

          // Multipart request 생성
          var request = http.MultipartRequest('POST', Uri.parse('http://34.47.91.250:8080/api/v1/mission/'));

          // 이미지 파일을 추가
          var fileStream = http.ByteStream(imageFile.openRead());
          var length = await imageFile.length();
          var multipartFile = http.MultipartFile('image', fileStream, length, filename: 'image.jpg');
          request.files.add(multipartFile);

          // Authorization 헤더에 Bearer 토큰 추가
          request.headers['Authorization'] = 'Bearer $myToken';

          // 요청 보내기
          var response = await request.send();

          // 응답 처리
          if (response.statusCode == 200) {
            print('Image uploaded successfully');
            print('API Response: ${await response.stream.bytesToString()}');
          } else {
            print('Failed to upload image. Status code: ${response.statusCode}');
            print('API Response: ${await response.stream.bytesToString()}');
          }
        } catch (error) {
          print('Error during image upload: $error');
        }
      }

      _imageUpload(_image!,Provider.of<Store1>(context, listen: false).token);
      // 이미지를 서버로 전송하는 코드를 작성하면 된다.
    });
  }
  @override
  Widget build(BuildContext context) {
    SystemChrome.setPreferredOrientations(
        [DeviceOrientation.portraitUp, DeviceOrientation.portraitDown]);

    return Scaffold(
      appBar: AppBar(
          actions: []
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Container(
            margin: EdgeInsets.all(20),
            decoration: BoxDecoration(
              border: Border.all(color: Colors.grey),
            ),
            width: 500,
            height: 500,
            child: Center(
              child: _image == null
                  ? Text('이미지가 없습니다.')
                  : Image.file(_image!),
            ),
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Container(
                margin: EdgeInsets.all(10),
                child: ElevatedButton(
                  onPressed: () async {
                    getImage(ImageSource.camera);
                  },
                  child: Text('카메라'),
                ),
              ),
              Container(
                margin: EdgeInsets.all(10),
                child: ElevatedButton(
                  onPressed: () async {
                    getImage(ImageSource.gallery);
                  },
                  child: Text('갤러리'),
                ),
              ),],
          ),

        ],
      ),
    );
  }
}
