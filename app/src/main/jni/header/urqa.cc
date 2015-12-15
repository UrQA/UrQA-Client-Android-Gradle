#include "urqa.h"

JNIEnv * UrqaNative::jEnv = 0;

UrqaNative::UrqaNative()
{

}

UrqaNative::~UrqaNative()
{

}
jstring UrqaNative::GetCachePath()
{
	//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "In JavaCallTest");


	jclass cls = jEnv->FindClass("com/urqa/clientinterface/URQAController");
	//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "Find Class Call");

	if(cls == NULL)
	{
		//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "Fail Findclass");
		return NULL;
	}

	jmethodID mhthod = jEnv->GetStaticMethodID(cls, "GetCachePath", "()Ljava/lang/String;");
	//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "STAticMethodId Call");
	if(mhthod == NULL)
	{
		//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "Fail FindMethod");
		return NULL;
	}

	jstring CachePath = (jstring) jEnv->CallStaticObjectMethod(cls, mhthod);

	return CachePath;

	//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "%d",i);
	//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "CallStaticVoidMethod Call");
}
bool UrqaNative::DumpCallback(const google_breakpad::MinidumpDescriptor& descriptor,
		void* context,
		bool succeeded)
{
	//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "Dump path: %s\n", descriptor.path());
	JavaCallTest(descriptor.path());
	//여기서 처리해줘야됨
	return succeeded;
}

void UrqaNative::URQAIntialize(JNIEnv *env)
{
	jEnv = env;
	//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "IntializeURQANative");


	jstring CachePath = GetCachePath();
	const char* cDesc = jEnv->GetStringUTFChars(CachePath, 0);

	//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "CachePath : %s" ,cDesc);
	google_breakpad::MinidumpDescriptor descriptor(cDesc);

	jEnv->ReleaseStringUTFChars(CachePath, cDesc);
	static google_breakpad::ExceptionHandler eh(descriptor, NULL, UrqaNative::DumpCallback,NULL, true, -1);

	//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "IntializeURQANativeSuccess");
}
void UrqaNative::JavaCallTest(const char* path)
{
	//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "In JavaCallTest");


	jclass cls = jEnv->FindClass("com/urqa/clientinterface/URQAController");
	//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "Find Class Call");

	if(cls == NULL)
	{
		//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "Fail Findclass");
		return;
	}

	jmethodID mhthod = jEnv->GetStaticMethodID(cls, "NativeCrashCallback", "(Ljava/lang/String;)I");
	//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "STAticMethodId Call");
	if(mhthod == NULL)
	{
		//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "Fail FindMethod");
		return;
	}

	jstring jstr = jEnv->NewStringUTF(path);
	if (jstr == NULL) {
		//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "Fail JString");
			return;
	}

	jint i = jEnv->CallStaticIntMethod(cls, mhthod, jstr);
	//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "%d",i);
	//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "CallStaticVoidMethod Call");
}

//void UrqaNative::URQASendException(std::exception &e)
//{

//}

