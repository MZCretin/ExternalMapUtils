#ExternalMapuUilsLibrary

##最新版本V1.0.0

##使用方式：

-------------------

**Step 1.** Add the JitPack repository to your build file Add it in your root build.gradle at the end of repositories: 
```gradle
allprojects { repositories { ... maven { url 'https://jitpack.io' } } }
```

**Step 2.** Add the dependency
```gradle
dependencies { compile 'com.github.MZCretin:AutoUpdateProject:v1.0' }
```

**Step 3.** Start using it wherever you want as below.

```
CretinAutoUpdateUtils.getInstance(MainActivity.this).check();
```


##使用说明

我只是简单的对百度和高度的Uri使用方式进行了一个简单的归纳和总结，只是为了使用起来更加方便，开发者就应该全力注重业务逻辑，而不是花时间东找西找。所以做个简单集成。


####有什么意见或者建议欢迎与我交流，觉得不错欢迎Star

使用过程中如果有什么问题或者建议 欢迎在issue中提出来或者直接联系我 792075058 嘿嘿

