LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

# ��� ������ ������, ������� ����� ���������� � Java ��� ������ System.loadLibrary()
LOCAL_MODULE    := AndroidNDK

LOCAL_C_INCLUDES :=  $(LOCAL_PATH)/../openal/ $(LOCAL_PATH)/../openal/include/AL $(LOCAL_PATH)/utils $(LOCAL_PATH)/../tremolo

LOCAL_STATIC_LIBRARIES :=  openal tremolo

LOCAL_CFLAGS := -DANDROID_NDK -fexceptions -Wno-psabi -DGL_GLEXT_PROTOTYPES=1

LOCAL_DEFAULT_CPP_EXTENSION := cpp

#������ ������, ������� ����� �������
LOCAL_SRC_FILES := MyNative.cpp \
	utils\AAssetFile.cpp \
	utils\OALContext.cpp \
	utils\OALSound.cpp \
	utils\OALOgg.cpp \
	utils\OALWav.cpp
#������ ��������� �� ndk, ������� ���� �������� � ������
LOCAL_LDLIBS :=  -ldl -llog -lz -landroid

include $(BUILD_SHARED_LIBRARY)
