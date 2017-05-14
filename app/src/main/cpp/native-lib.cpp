#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_sqlcipherjniexample_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "K#Y_4_Encrypt!0n";
    return env->NewStringUTF(hello.c_str());
}
