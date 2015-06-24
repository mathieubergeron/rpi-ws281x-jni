/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_ws2811_Ws2811Library */

#ifndef _Included_org_ws2811_Ws2811Library
#define _Included_org_ws2811_Ws2811Library
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_ws2811_Ws2811Library
 * Method:    init
 * Signature: (II[Lorg/ws2811/Ws2811Channel;)J
 */
JNIEXPORT jlong JNICALL Java_org_ws2811_Ws2811Library_init
  (JNIEnv *, jclass, jint, jint, jobjectArray);

/*
 * Class:     org_ws2811_Ws2811Library
 * Method:    fini
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_ws2811_Ws2811Library_fini
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_ws2811_Ws2811Library
 * Method:    render
 * Signature: (JI[I)I
 */
JNIEXPORT jint JNICALL Java_org_ws2811_Ws2811Library_render
  (JNIEnv *, jclass, jlong, jint, jintArray);

/*
 * Class:     org_ws2811_Ws2811Library
 * Method:    waitCompletion
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_ws2811_Ws2811Library_waitCompletion
  (JNIEnv *, jclass, jlong);

#ifdef __cplusplus
}
#endif
#endif
