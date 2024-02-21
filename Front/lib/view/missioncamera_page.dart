
import 'dart:io';
import 'package:awesome_dio_interceptor/awesome_dio_interceptor.dart';
import 'package:http/http.dart' as http;
import 'package:image_picker/image_picker.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:provider/provider.dart';
import 'package:dio/dio.dart';
import 'package:http_parser/http_parser.dart';
import '../callapi.dart';

class MCameraPage extends StatefulWidget {
  var index;

   MCameraPage({super.key, required this.index});

  @override
  State<MCameraPage> createState() => _MCameraPageState();
}

class _MCameraPageState extends State<MCameraPage> {
  File? _image;
  final picker = ImagePicker();
  bool isLoading = false;
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
      setState(() {
        isLoading = true; // 로딩 상태 시작
      });

      // 서버 엔드포인트 설정
      // 이미지를 서버로 업로드하기 위한 request 생성
      String apiUrl = 'http://34.47.91.250:8080/api/v1/mission/${widget.index}';

      Dio dio = Dio();
      dio.interceptors.add(AwesomeDioInterceptor());
      dio.options.contentType = 'multipart/form-data';
      dio.options.headers['Authorization'] = 'Bearer $token';

      FormData formData = FormData.fromMap({
        'file': await MultipartFile.fromFile(imageFile.path, filename: 'uploaded_image', contentType: new MediaType('image', 'jpeg')),
      });
      Response response = await dio.post(apiUrl, data: formData);

      // 로딩 상태 종료
      setState(() {
        isLoading = false;
      });
      // 서버 응답 확인
      if (response.statusCode == 200) {
        print('이미지 업로드 성공');
        // 업로드 성공 시 팝업창 표시
        _showUploadSuccessPopup();
      } else {
        print('이미지 업로드 실패: ${response.statusCode}');
        print('서버 응답 데이터: ${response?.data}');
        print('formdata: $formData');
        _showFailedPopup();
      }
    } catch (e) {
      print('에러 발생: $e');
      _showFailedPopup();
      // 로딩 상태 종료
      setState(() {
        isLoading = false;
      });
    }
  }
  void _showUploadSuccessPopup() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('이미지 업로드 성공'),
          content: Text('이미지가 성공적으로 업로드되었습니다.'),
          actions: <Widget>[
            TextButton(
              onPressed: () {
                Navigator.of(context).pop();
              },
              child: Text('확인'),
            ),
          ],
        );
      },
    );
  }
  void _showFailedPopup() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('이미지 업로드 실패'),
          content: Text('다시 촬영해주세요'),
          actions: <Widget>[
            TextButton(
              onPressed: () {
                Navigator.of(context).pop();
              },
              child: Text('확인'),
            ),
          ],
        );
      },
    );
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
                  style: ElevatedButton.styleFrom(
                    primary: Colors.lightGreen,
                  ),
                  onPressed: () async {
                    getImage(ImageSource.camera);
                  },
                  child: Text('카메라'),
                ),
              ),
              Container(
                margin: EdgeInsets.all(10),
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom(
                    primary: Colors.lightGreen,
                  ),
                  onPressed: () async {
                    getImage(ImageSource.gallery);
                  },
                  child: Text('갤러리'),
                ),
              ),
            if(isLoading)
              CircularProgressIndicator(),
            ],
          ),

        ],
      ),
    );
  }
}
