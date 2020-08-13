package helpers

/*
#cgo LDFLAGS: -landroid
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include <jni.h>
#include <stdlib.h>

char* get_assets_path(uintptr_t java_vm, uintptr_t jni_env, jobject ctx) {
	JavaVM* vm = (JavaVM*)java_vm;
	JNIEnv* env = (JNIEnv*)jni_env;

	jclass cls = (*env)->GetObjectClass(env, ctx);
	jmethodID nJmethodID = (*env)->GetMethodID(env, cls, "getAssetsPath", "()Ljava/lang/String;");
	jstring path = (jstring)(*env)->CallObjectMethod(env, ctx, nJmethodID);
	const char* filesdir = (*env)->GetStringUTFChars(env, path, NULL);
	return (char*)filesdir;
}
*/
import "C"
import (
	"github.com/shranet/mobile/internal/mobileinit"
)

func GetAssetsPath() string {
	var result = ""
	err := mobileinit.RunOnJVM(func(vm, env, ctx uintptr) error {
		result = C.GoString(C.get_assets_path(C.uintptr_t(vm), C.uintptr_t(env), C.jobject(ctx)))
		return nil
	})

	if err != nil {
		return ""
	}

	return result
}
