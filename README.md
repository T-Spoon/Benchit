Benchit
============
Fast & simple benchmarking framework for Android.  Ever had the feeling that certain parts of your code were slow? Now you can prove it (and make sure your refactor worked)!  

Usage
------
```java
for(int i = 0; i < 10; i++) {
    Benchit.begin("benchmark-name");
    // Code you want to benchmark
    Benchit.end("benchmark-name").log();
}
Benchit.analyze("benchmark-name").log();
```
In your LogCat output you should see something like the following:
```
D/Benchit﹕[benchmark-name] --> Sample Size[10], Average[22ms], Range[20ms --> 33ms], Deviation[4ms]
```
You can also compare multiple benchmarks:
```java
// Compare & print results of all Benchmarks
Benchit.compare(Benchit.Stat.STANDARD_DEVIATION).log();
// Compare & print results of specific Benchmarks
Benchit.compare(Benchit.Stat.RANGE, Benchit.Order.ASCENDING, "benchmark-one", "benchmark-two").log();
```

Configuration
------
```java
// Statistics to calculate.  By default all are enabled.
Benchit.setEnabledStats(Benchit.Stat.AVERAGE, Benchit.Stat.STANDARD_DEVIATION);
// Sets the precision for all future benchmarks.  Default is Milliseconds
Benchit.setDefaultPrecision(Benchit.Precision.MICRO);
```

Tips
------
Benchmarking can be a funny thing.  The Dalvik JIT & ART AoT compilers can optimize certain blocks of code without you knowing, which could lead to inaccurate results. So here are a couple of tips for ensuring your benchmarking correctly (or as correctly as possible):
- Run your benchmarks for enough iterations to be meaningful.  If you see inconsistent results, the code probably hasn't been run enough.
- Run `System.gc()` between benchmarks, so that the liklihood of a garbage collector pause during one of your tests is reduced.
- As always - don't over-optimize!  If the differences in your benchmarks aren't significant, then don't sacrifice simplicity & readability for speed.


Download
------
Add the following code to your `build.gradle` file (as described on [JitPack])
```
repositories {
    maven {
        url "https://jitpack.io"
    }
}
dependencies {
    compile 'com.github.T-Spoon:Benchit:v1.0.2'
}
```

License
-------

    Copyright 2015 Oisín O'Neill

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
[JitPack]:https://jitpack.io/#T-Spoon/Benchit/v1.0.2