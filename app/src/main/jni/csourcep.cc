#include <string.h>
#include <jni.h>
//#include <android/log.h>
#include <stdio.h>


//#include "JavaCatchHeader.h"
#include "urqa.h"

void Crash() {
  volatile int* a = reinterpret_cast<volatile int*>(NULL);
  *a = 1;
}


extern "C" {
	JNIEXPORT jstring Java_com_example_urqanative_MainActivity_invokeNativeFunction(JNIEnv* env, jobject javaThis) {
		//__android_log_print(ANDROID_LOG_FATAL, "zzz", "start");

		//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "address:%d",);

		UrqaNative::URQAIntialize(env);

		Crash();
		return env->NewStringUTF("Hello from C Code!");
	}
}



