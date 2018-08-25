package ru.suvitruf.androidndk.tutorial1;

public class AndroidNDK {
	
	// �������� ������ �AndroidNDK� � �������� ����������, � ������� ����������� ������. 
	// �������� ����� ������ �������� � ����� Android.mk.
	static {
		System.loadLibrary("AndroidNDK");
	}
	
	public static native void SetString(String str);
	public static native void ChangeString();
	public static native String GetString(); 
}
 