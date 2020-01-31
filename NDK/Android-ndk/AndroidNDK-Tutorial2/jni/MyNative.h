#include <def.h>
#include <jni.h>




extern "C" {
JNIEXPORT void JNICALL Java_ru_suvitruf_androidndk_tutorial2_MainActivity_nativeCall(JNIEnv *pEnv, jobject pThis, jobject pNativeCallListener);
JNIEXPORT void JNICALL Java_ru_suvitruf_androidndk_tutorial2_MainActivity_startTimer(JNIEnv *pEnv, jobject pThis);
JNIEXPORT void JNICALL Java_ru_suvitruf_androidndk_tutorial2_MainActivity_destroyObjects(JNIEnv *pEnv, jobject pThis);
JNIEXPORT void JNICALL Java_ru_suvitruf_androidndk_tutorial2_MainActivity_stopTimer(JNIEnv *pEnv, jobject pThis);
//JNIEXPORT void JNICALL Java_ru_suvitruf_androidndk_tutorial2_MainActivity_resumeTimer(JNIEnv *pEnv, jobject pThis);
}

class NativeCallListener {
public:
	int threadCount;
	//������� ������ �������
	int timeLeft;
    //������� �� ������
	bool timerOn;
	NativeCallListener(JNIEnv* pJniEnv, jobject pWrapperInstance);
	NativeCallListener() {}
	//������ �������
	void startTimer();
	//�������� ������
	//void resumeTimer();
	//������� ������
	void stopTimer();
	//�������� �������� � Java �����
    void sendTime(int time);
    //��������� ������
    void destroy();
	~NativeCallListener(){}
private:
	JNIEnv* getJniEnv();

    //������ �� �����
	jmethodID sendTimeID;
	//������ �� ������
	jobject mObjectRef;
	JavaVM* mJVM;
};

extern NativeCallListener listener;
