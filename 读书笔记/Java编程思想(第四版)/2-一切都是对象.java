

		1.对象存储在什么地方
		  1>寄存器 // 这是最快的存储区.
		  2>堆栈,位于通用RAM(随机访问寄存器) // 速度仅次于寄存器 存储一般对象引用
		  3>堆.一种通用的内存池(也位于RAM区),用于存放所有的java对象.
		  4>常量存储 (ROM 只读存储器中) // for example --> 字符串池
		  5>非RAM存储. // 两个例子是流对象和持久化对象

		2.java要确定每种基本类型所占存储空间的大小.他们的大小并不像其他大多数语言那样随机器硬件架构的变化而变化.这种所占存储空间大小的不变性是Java程序比用其他大多数语言编写的程序更具可移植性的原因之一.

			byte 8bits
			char 16
			short 16
			int  32 
			long 64
			float 32
			double 64

		3.高精度数字
		  BitInteger 和 BigDecimal 以速度换取了精度

		4.大多数语言都有作用域的概念(scope)
		  Java是一种free-form语言(自由格式)	// 空格 制表符 换行都不会影响程序的执行效果

		5.初始化的方法并不适用于"局部"变量.比如在某个方法中定义:
		  int x;
		  x可能是任意值,而不会被自动初始化为0.所以在使用前,必须为x赋值.

				  	

		  
