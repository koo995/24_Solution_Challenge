import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'home_page.dart';

class SignInEx extends StatelessWidget {
  final FirebaseAuth _auth = FirebaseAuth.instance;
  final GoogleSignIn googleSignIn = GoogleSignIn();

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
  Future<void> _handleSignOut() async {
    await _auth.signOut();
    await googleSignIn.signOut();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Google 소셜 로그인'),
      ),
      body: Center(
        child: ElevatedButton(
          onPressed: () async {
                       // Google 로그인 수행
            UserCredential? userCredential = await _handleSignIn();

            if (userCredential != null) {
              Navigator.of(context).pushAndRemoveUntil(
                  MaterialPageRoute(builder: (context) => HomePage()), (route) => false);
              print('로그인 성공: ${userCredential.user!.displayName}');
            } else {
              print('로그인 실패');
            }
          },
          child: Text('Google 로그인'),
        ),
      ),
    );
  }
}