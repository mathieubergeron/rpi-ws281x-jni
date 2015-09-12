rpi-ws281x-jni
==============

This is a simple JNI library wrapper over
[jgarff/rpi_ws281x](https://github.com/jgarff/rpi_ws281x) C library.

+ clone, compile & install [jgarff/rpi_ws281x](https://github.com/jgarff/rpi_ws281x)

```
git clone git@github.com:jgarff/rpi_ws281x.git
cd rpi_ws281x
gcc -Wall -shared -o libws2811.so -fPIC dma.c pwm.c ws2811.c
cp libws2811.so /usr/lib
ldconfig
mkdir /usr/include/ws2811
cp *.h /usr/include/ws2811
```

+ clone & package

```
git clone git@github.com:mathieubergeron/rpi-ws281x-jni.git
cd rpi-ws281x-jni
mvn package
```

+ import & use `ws2811-jni-VERSION.jar` in your project

```java
    public static void main(String[] args) throws Exception {
        final Ws2811 ws2811 = new NativeWs2811(LED_COUNT);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                ws2811.shutdown();
            }
        });

        while (true) {
            ws2811.render(produceYourNextFrame());
        }
    }
```