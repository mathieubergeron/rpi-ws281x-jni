rpi-ws281x-jni
==============

This is a (**work-in-progress**) JNI library wrapper over
[jgarff/rpi_ws281x](https://github.com/jgarff/rpi_ws281x) C library.

+ compile and install [jgarff/rpi_ws281x](https://github.com/jgarff/rpi_ws281x)

```
git clone git@github.com:jgarff/rpi_ws281x.git
cd rpi_ws281x
gcc -Wall -shared -o libws2811.so -fPIC dma.c pwm.c ws2811.c
cp libws2811.so /usr/lib
ldconfig
mkdir /usr/include/ws2811
cp *.h /usr/include/ws2811
```

+ clone rpi-ws281x-jni and compile JNI wrapper C file to a shared object library

```
git clone git@github.com:mathieubergeron/rpi-ws281x-jni.git
cd rpi-ws281x-jni
gcc -I"/usr/include/ws2811" \
    -I"$JAVA_HOME/include" \
    -I"$JAVA_HOME/include/linux" \
    -Wall -shared -o libws2811jni.so \
    -fPIC src/main/c/ws2811jni.c \
    -lws2811
```

TODO
====

+ automatic build process
  + compile libws2811jni.so
  + bundle JAR with libs
