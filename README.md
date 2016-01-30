#DilatingDotsProgressBar

![](/../demo/example/src/main/assets/dotdemo.gif?raw=true)

##Installation

Uploading to maven central soon.

##Usage

```xml
<com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar
			android:id="@+id/progress"
			android:layout_width="wrap_content"
			android:layout_height="wrap_content"
			android:radius="5dp"
			android:color="#8C2323"
			app:dd_numDots="3"
			app:dd_scaleMultiplier="1.5"
			app:dd_animationDuration="500"
			app:dd_horizontalSpacing="4dp"
			/>
```

Show progress bar and start animating
```java
  mDilatingDotsProgressBar = (DilatingDotsProgressBar) findViewById(R.id.progress);
  mDilatingDotsProgressBar.showNow();
```

Stop animating and hide
```java
  mDilatingDotsProgressBar.hideNow();
```

##Field Customization
|name|format|description|
|:---:|:---:|:---:|
| android:radius | dimension | set the radius of each dot
| android:color | int | set the color of each dot
| dd_numDots | integer |set number of dots
| dd_scaleMultiplier | float |set the maximum size the dot will expand to. (scaleMultiplier x radius)
| dd_animationDuration | int |set the length of a single dot's full animation (in milliseconds) 
| dd_horizontalSpacing | dimension |set the number of dp between each dot

##Credit
Thanks to [Alessandro Crugnola](https://github.com/sephiroth74) for his contributions.
