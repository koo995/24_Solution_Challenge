import 'package:flutter/material.dart';

class StoreMission with ChangeNotifier {
  var apiUrl = 'https://34.47.91.250:8080/api/v1/mission';
  String _token = "";
  String get token => _token;
  // 토큰 설정 메서드
  void setToken(String newToken) {
    _token = newToken;
    notifyListeners(); // 변경 사항을 구독자에게 알림
  }
  void changeApiUrl(String newUrl) {
    apiUrl = newUrl;
    notifyListeners();
  }
}//