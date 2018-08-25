#include <def.h>
#include <jni.h>
#include "AAssetFile.h"
char  MyStr[80];


extern "C" {
JNIEXPORT void JNICALL Java_ru_suvitruf_androidndk_tutorial3_MainActivity_readResources(JNIEnv *pEnv, jobject pThis, jobject pNativeCallListener, jobject assetManager);


JNIEXPORT void JNICALL Java_ru_suvitruf_androidndk_tutorial3_AndroidNDK_destroyObjects(JNIEnv *pEnv, jobject pThis);

}
class NativeCallListener {
public:
	//������� ������ �������
	//int timeLeft;
    //������� �� ������
	//bool timerOn;
	NativeCallListener(JNIEnv* pJniEnv, jobject pWrapperInstance);
	NativeCallListener() {}
	//����� �������
	//void startTimer();
	//�������� �������� � Java �����
    void sendResult(int result);
    //��������� ������
    void destroy();
	~NativeCallListener(){
		//LOGI("~NativeCallListener");

	}
	void readResources();
private:
	JNIEnv* getJniEnv();

    //������ �� �����
	jmethodID sendResultID;
	//������ �� ������
	jobject mObjectRef;
	JavaVM* mJVM;
};

extern NativeCallListener listener;
