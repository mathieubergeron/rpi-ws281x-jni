#include <stdint.h>
#include <stdlib.h>
#include "ws2811.h"
#include <jni.h>
#include <stdio.h>
#include "ws2811jni.h"

JNIEXPORT jlong JNICALL
Java_org_ws2811_Ws2811Library_init (JNIEnv * env, jclass jc, jint frequency, jint dmanum, jobjectArray channels) {
    jclass classChannel = (*env)->FindClass(env, "org/ws2811/Ws2811Channel");
    jmethodID getGpio = (*env)->GetMethodID(env, classChannel, "getGpio", "()I");
    jmethodID getInvert = (*env)->GetMethodID(env, classChannel, "getInvert", "()I");
    jmethodID getCount = (*env)->GetMethodID(env, classChannel, "getCount", "()I");
    jmethodID getBrightness = (*env)->GetMethodID(env, classChannel, "getBrightness", "()I");

    ws2811_t *ledstring = (ws2811_t*) malloc(sizeof(ws2811_t));
    ledstring->freq = frequency;
    ledstring->dmanum = dmanum;

    int channelCount = (*env)->GetArrayLength(env, channels);

    int channelIndex;
    for (channelIndex = 0; channelIndex < channelCount; channelIndex++) {
        jint jgpio = 0;
        jint jinvert = 0;
        jint jcount = 0;
        jint jbrightness = 0;

        jobject jchannel = (*env)->GetObjectArrayElement(env, channels, channelIndex);
        if (jchannel != NULL) {
            jgpio = (*env)->CallIntMethod(env, jchannel, getGpio);
            jinvert = (*env)->CallIntMethod(env, jchannel, getInvert);
            jcount = (*env)->CallIntMethod(env, jchannel, getCount);
            jbrightness = (*env)->CallIntMethod(env, jchannel, getBrightness);
        }

        ledstring->channel[channelIndex] = (ws2811_channel_t )
            {
                .gpionum = jgpio,
                .count = jcount,
                .invert = jinvert,
                .brightness = jbrightness,
            };
    }

    return ws2811_init(ledstring) == 0 ? (jlong)((long) ledstring) : (jlong) 0;
}

JNIEXPORT void JNICALL Java_org_ws2811_Ws2811Library_fini(JNIEnv * env, jclass jc, jlong reference) {
    ws2811_t *ledstring = (ws2811_t*) ((long)reference);
    ws2811_fini(ledstring);
    free(ledstring);
}

JNIEXPORT jint JNICALL
Java_org_ws2811_Ws2811Library_render (JNIEnv * env, jclass jc, jlong reference, jint channel, jintArray jleds) {
    ws2811_t *ledstring = (ws2811_t*) ((long) reference);
    jint *leds = (*env)->GetIntArrayElements(env, jleds, NULL);
    int ledCount = ledstring->channel[channel].count;

    int ledIndex;
    for (ledIndex = 0; ledIndex < ledCount; ledIndex++) {
        ledstring->channel[channel].leds[ledIndex] = leds[ledIndex];
    }

    return ws2811_render(ledstring);
}

JNIEXPORT jint JNICALL
Java_org_ws2811_Ws2811Library_waitCompletion (JNIEnv * env, jclass jc, jlong reference) {
    ws2811_t *ledstring = (ws2811_t*) ((long) reference);
    return (jint) ws2811_wait(ledstring);
}
