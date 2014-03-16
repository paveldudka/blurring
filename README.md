### Android blurring sample 

![](https://dl.dropboxusercontent.com/u/5854498/github.png)


#### Acknowledgements

##### Stack Blur v1.0 from
[http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html](http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html)
        
**Java Author:** Mario Klingemann <mario@quasimondo.com>
[http://incubator.quasimondo.com](http://incubator.quasimondo.com)
created Feburary 29, 2004

**Android port:** Yahel Bouaziz <yahel@kayenko.com>
[http://www.kayenko.com](http://www.kayenko.com)
ported april 5th, 2012

```
This is a compromise between Gaussian Blur and Box blur
It creates much better looking blurs than Box Blur, but is
7x faster than my Gaussian Blur implementation.
        
I called it Stack Blur because this describes best how this
filter works internally: it creates a kind of moving stack
of colors whilst scanning through the image. Thereby it
just has to add one new block of color to the right side
of the stack and remove the leftmost color. The remaining
colors on the topmost layer of the stack are either added on
or reduced by one, depending on if they are on the right or
on the left side of the stack.
        
If you are using this algorithm in your code please add
the following line:
        
Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>
```
