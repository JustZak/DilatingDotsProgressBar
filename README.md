#DilatingDotsProgressBar
![](https://img.shields.io/badge/Android%20Arsenal-DilatingDotsProgressBar-green.svg?style=true)

![](/../demo/example/src/main/assets/dotdemo.gif?raw=true)

##Installation

Waiting for approval for jCenter

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

```android
  mDilatingDotsProgressBar = (DilatingDotsProgressBar) findViewById(R.id.progress);
  
  // show progress bar and start animating
  mDilatingDotsProgressBar.showNow();
  
  // stop animation and hide
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

## License

The MIT License (MIT)

Copyright (c) 2016 Zachary Reik

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
