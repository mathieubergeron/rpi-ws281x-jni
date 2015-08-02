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
mvn package
java -jar target/ws2811-jni-VERSION.jar
```
