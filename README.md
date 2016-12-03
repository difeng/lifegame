###1. 生命游戏简介：
  生命游戏(Game Of Life)是英国数学家约翰·何顿·康威在1970年发明的细胞自动机。它是由3条规则构成的二维元胞自动机(2D Cellular Automata)。
###2. 原理
在有N\*N个格子的平面上，把每一个格子都可以看成是一个生命体，每个生命都有生和死两种状态，如果该格子生就显示蓝色，死则显示白色。每一个格子旁边都有邻居格子存在，如果我们把3*3的9个格子构成的正方形看成一个基本单位的话，那么这个正方形中心的格子的邻居就是它旁边的8个格子。
每个格子的生死遵循下面的原则：
1． 如果一个细胞周围有3个细胞为生（一个细胞周围共有8个细胞），则该细胞为生（即该细胞若原先为死，则转为生，若原先为生，则保持不变） 。
2． 如果一个细胞周围有2个细胞为生，则该细胞的生死状态保持不变；
3． 在其它情况下，该细胞为死（即该细胞若原先为生，则转为死，若原先为死，则保持不变）
###3. 代码实现
功能：
####1.全屏显示滑翔机发射器的演化过程如下图：
![滑翔机发射器演示图](http://7xawio.com1.z0.glb.clouddn.com/lifegame_plant.png)
####2. 按“Esc”键程序退出
>类说明：
Canvas.java       画布类，封装了细胞演化的算法
Cell.java        细胞单元的类
Constant.java    常量类
MainFrame.java   主控程序

算法思路：
用三个链表，cellList(活着的细胞)，bornCellList(刚出生的细胞)，deadCellList（死去的细胞），遍历cellList，对每个细胞按规则进行繁衍，繁衍一代后，处理cellList，从中去掉deadCellList，加入bornCellList。在如此可完成一轮的繁衍，其中还要用到一个二维数组flagArr来保存细胞状态。
二维数组表示细胞的状态
//取值为1 和 2   1:当代存活的细胞      2:刚产生的下一代细胞
int [][] flagArr;
活着的细胞链表
List<Cell> cellList = new LinkedList<Cell>();
新生代的细胞链表
List<Cell> bornCellList = new LinkedList<Cell>();
死亡的细胞链表
List<Cell> deadCellList = new LinkedList<Cell>();

1. 清空bornCellList，deadCellList
2. 遍历cellList，对每个细胞按规则进行演化
3. 2步骤后deadCellList里面会有一些死去的细胞，遍历deadCellList，将其中细胞的状态标记到flagArr中。
		for(Cell cel:deadCellList)
			flagArr[cel.row][cel.col] = 0;
从活着的细胞列表中去掉在下一代将要死掉的
cellList.removeAll(deadCellList);
将下一代新生成的细胞标记为当代存活的细胞
		for(Cell cel:bornCellList)
			flagArr[cel.row][cel.col]=1;
将新生成的细胞加入到活着的细胞列表中
		cellList.addAll(bornCellList);

重复执行1到3的步骤，每重复一次，重绘整个细胞面板
