
import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;
import 'package:image_picker/image_picker.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:provider/provider.dart';

import '../callapi.dart';

class CameraPage extends StatefulWidget {
  const CameraPage({super.key});

  @override
  State<CameraPage> createState() => _CameraPageState();
}

class _CameraPageState extends State<CameraPage> {
  File? _image;
  final picker = ImagePicker();

  // 비동기 처리를 통해 카메라와 갤러리에서 이미지를 가져온다.
  Future<void> getImage(ImageSource imageSource) async {
    final image = await picker.pickImage(source: imageSource, imageQuality: 50);
    setState(() {
      _image = File(image!.path); // 가져온 이미지를 _image에 저장
    });

    // 이미지를 서버로 전송
    _imageUpload(_image!, Provider.of<Store1>(context, listen: false).token);
  }

  Future<void> _imageUpload(File imageFile, String token) async {
    try {
      // 서버 엔드포인트 설정
      final Uri url = Uri.parse('http://34.47.91.250:8080/api/v1/image');

      // 이미지를 서버로 업로드하기 위한 request 생성
      var request = http.MultipartRequest('POST', url);

      // 이미지 파일 추가
      request.files.add(http.MultipartFile(
        'file', // 서버에서 사용하는 필드명
        imageFile.readAsBytes().asStream(),
        imageFile.lengthSync(),
        filename: 'uploaded_image.jpg',
      ));

      // 토큰 추가
      request.headers['Authorization'] = 'Bearer $token';

      // 요청 보내기 및 응답 받기
      var response = await http.Response.fromStream(await request.send());

      // 서버 응답 확인
      if (response.statusCode == 200) {
        print('이미지 업로드 성공');
      } else {
        print('이미지 업로드 실패: ${response.statusCode}');
      }
    } catch (e) {
      print('에러 발생: $e');
    }
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
