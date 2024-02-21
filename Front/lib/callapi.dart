import 'package:flutter/material.dart';

class Store1 with ChangeNotifier {
  var _id = "";
  String get id => _id;

  var numchallenge = 0;

  String _token = "";
  String get token => _token;
  // 토큰 설정 메서드
  void setToken(String newToken) {
    _token = newToken;
    notifyListeners(); // 변경 사항을 구독자에게 알림
  }
  void setId(String newid) {
    _id = newid;
    notifyListeners();
  }
}//