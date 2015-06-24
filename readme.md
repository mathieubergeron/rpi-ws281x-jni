rpi-ws281x-jni
==============

This is a (**work-in-progress**) JNI library wrapper over
[jgarff/rpi_ws281x](https://github.com/jgarff/rpi_ws281x) C library.

1. clone [jgarff/rpi_ws281x](https://github.com/jgarff/rpi_ws281x) and compile to a shared object library

```
git clone git@github.com:jgarff/rpi_ws281x.git
cd rpi_ws281x
gcc -Wall -shared -o libws2811.so -fPIC dma.c pwm.c ws2811.c
```

2. clone rpi-ws281x-jni and compile JNI wrapper C file to a shared object library

```
git clone git@github.com:mathieubergeron/rpi-ws281x-jni.git
cd rpi-ws281x-jni
gcc -I"/path/to/rpi_ws281x" -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -Wall -shared -o libws2811jni.so -fPIC org_ws281x_Ws281xLibrary.c -L. -lws2811
```