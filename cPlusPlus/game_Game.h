/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class game_Game */

#ifndef _Included_game_Game
#define _Included_game_Game
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     game_Game
 * Method:    initializeBoard
 * Signature: ()[[I
 */
JNIEXPORT jobjectArray JNICALL Java_game_Game_initializeBoard__
  (JNIEnv *, jobject);

/*
 * Class:     game_Game
 * Method:    initializeBoard
 * Signature: ([[I)V
 */
JNIEXPORT void JNICALL Java_game_Game_initializeBoard___3_3I
  (JNIEnv *, jobject, jobjectArray);

/*
 * Class:     game_Game
 * Method:    movePiece
 * Signature: (IIII)Ljava/util/Map;
 */
JNIEXPORT jobject JNICALL Java_game_Game_movePiece
  (JNIEnv *, jobject, jint, jint, jint, jint);

/*
 * Class:     game_Game
 * Method:    canChoosePiece
 * Signature: (II)Z
 */
JNIEXPORT jboolean JNICALL Java_game_Game_canChoosePiece
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     game_Game
 * Method:    getWinner
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_game_Game_getWinner
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
