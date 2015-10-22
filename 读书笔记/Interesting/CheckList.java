

	
		1.Adapter 中为什么不建议加监听器, 那怎么做更好?


		2.layout_weight及常见属性解析

		  1>TextView 在LinearLayout中是第一行字是基于LinearLayout的基线来对齐的.如果不想让其对齐,可以在LinrearLayout中设置android:baselineAligned="false"；
		  2>LinearLayout中三个TextView,第一个TextView的width设置为wrap_content,另外两个设置成0dp,weight分别是1,2,2.
		  	// 宽分别是wrap_content+剩下的1/5,剩下的2/5,剩下的2/5
		  3>LinearLayout中三个TextView,三个TextView的width设置为match_parent,weight分别是1,2,2.
		  	// 宽分别是198 96 96(假设LinearLayout的宽是480dp)
          weight:首先按照控件声明的尺寸进行分配,然后剩下的尺寸按照weight分配.(控件宽度+父控件剩余宽度*比例)
          4>LinearLayout中中只有一个TextView,需要设置为父控件的1/2,可以在LinrearLayout中设置android:weightSum="2";TextView的weight设为1即可.
          5>layout_ 这种属性是相对于父控件的.例如layout_gravity是在父控件的布局,而gravity是控件本身内部的布局.

		3.在开发过程中是否自定义过控件,如何自定义控件

		4.ListView的性能优化

		5.当一个界面中有多个可点击的块,怎么解决?

		  // 
		  添加监听器,只能第一个点击的动作生效,或者第一个点击后,第二个点击让两个动作同时无效.