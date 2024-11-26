#include "game_Game.h"
#include "Board.h"

Board *board = nullptr;

JNIEXPORT jobjectArray JNICALL Java_game_Game_initializeBoard__(JNIEnv *env, jobject) {
    board = new Board();

    jobjectArray result = env->NewObjectArray(Board::BOARD_SIZE, env->FindClass("[I"), nullptr);

    for (int i = 0; i < Board::BOARD_SIZE; i++) {
        jintArray row = env->NewIntArray(Board::BOARD_SIZE);

        env->SetIntArrayRegion(row, 0, Board::BOARD_SIZE, reinterpret_cast<const jint *>(board->cells[i]));
        env->SetObjectArrayElement(result, i, row);
        env->DeleteLocalRef(row);
    }

    return result;
}

JNIEXPORT void JNICALL Java_game_Game_initializeBoard___3_3I
(JNIEnv *env, jobject, jobjectArray array) {
    board = new Board();

    for (int i = 0; i < Board::BOARD_SIZE; i++) {
        jintArray row = (jintArray) env->GetObjectArrayElement(array, i);

        jint *rowElements = env->GetIntArrayElements(row, nullptr);

        for (int j = 0; j < Board::BOARD_SIZE; j++)
            board->cells[i][j] = static_cast<Board::Piece>(rowElements[j]);

        env->ReleaseIntArrayElements(row, rowElements, JNI_ABORT);
        env->DeleteLocalRef(row);
    }
}

JNIEXPORT jobject JNICALL Java_game_Game_movePiece(JNIEnv *env, jobject, jint fromRow, jint fromCol, jint toRow,
                                                   jint toCol) {
    jclass hashMapClass = env->FindClass("java/util/HashMap");
    jmethodID hashMapInit = env->GetMethodID(hashMapClass, "<init>", "()V");
    jmethodID hashMapPut = env->GetMethodID(hashMapClass, "put",
                                            "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
    jobject javaMap = env->NewObject(hashMapClass, hashMapInit);

    jclass pairClass = env->FindClass("util/Pair");
    jmethodID pairConstructor = env->GetMethodID(pairClass, "<init>", "(II)V");

    for (const auto &entry: board->movePiece(fromRow, fromCol, toRow, toCol)) {
        std::pair<int, int> pair = entry.first;
        jobject javaPair = env->NewObject(pairClass, pairConstructor, pair.first, pair.second);

        jint value = entry.second;
        jclass integerClass = env->FindClass("java/lang/Integer");
        jmethodID integerConstructor = env->GetMethodID(integerClass, "<init>", "(I)V");
        jobject javaInteger = env->NewObject(integerClass, integerConstructor, value);

        env->CallObjectMethod(javaMap, hashMapPut, javaPair, javaInteger);
        env->DeleteLocalRef(javaPair);
        env->DeleteLocalRef(javaInteger);
    }

    return javaMap;
}


JNIEXPORT jboolean JNICALL Java_game_Game_canChoosePiece
(JNIEnv *, jobject, jint y, jint x) {
    return board->canChoosePiece(x, y);
}


JNIEXPORT jstring JNICALL Java_game_Game_getWinner
(JNIEnv *env, jobject) {
    return env->NewStringUTF(board->getWinner().c_str());
}
