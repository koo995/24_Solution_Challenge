import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:provider/provider.dart';
import 'home_page.dart';
import '/callapi.dart';
import 'package:http/http.dart' as http;

class SignInEx extends StatelessWidget {
  final FirebaseAuth _auth = FirebaseAuth.instance;
  final GoogleSignIn googleSignIn = GoogleSignIn();

  Future<void> _handleSignOut() async {
    await _auth.signOut();
    await googleSignIn.signOut();
  }

  SignInEx({super.key});

  Future<UserCredential?> _handleSignIn() async {
    try {
      final GoogleSignInAccount? googleSignInAccount = await googleSignIn.signIn();
      final GoogleSignInAuthentication googleSignInAuthentication =
      await googleSignInAccount!.authentication;
      final AuthCredential credential = GoogleAuthProvider.credential(
        accessToken: googleSignInAuthentication.accessToken,
        idToken: googleSignInAuthentication.idToken,
      );
      return await _auth.signInWithCredential(credential);
    } catch (error) {
      print(error);
      return null;
    }
  }

  Future<void> _apiRequest(String token) async {
    try {
      String mytoken = token;
      final response = await http.get(
        Uri.parse('http://34.47.91.250:8080/api/v1/1/location'),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': 'Bearer $mytoken',
        },
      );

      if (response.statusCode == 200) {
        print('API Response: ${response.body}');
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
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Google 소셜 로그인'),
      ),
      body: Center(
        child: ElevatedButton(

          style: ElevatedButton.styleFrom(
            minimumSize: Size(200, 50),
            primary: Colors.grey,
            onPrimary: Colors.white,
            textStyle: TextStyle(fontSize: 20),
          ),
          onPressed: () async {
                       // Google 로그인 수행
            UserCredential? userCredential = await _handleSignIn();
            if (userCredential != null) {
              Navigator.of(context).pushAndRemoveUntil(
                  MaterialPageRoute(builder: (context) => HomePage()), (route) => false);
              String? myToken = await userCredential.user!.getIdToken();
              Provider.of<Store1>(context, listen: false).setToken(myToken!);
              print('로그인 성공: ${userCredential.user!.displayName}');
            } else {
              print('로그인 실패');
            }
          },
          child: Text('Google Login'),
        ),
      ),
    );
  }
}

