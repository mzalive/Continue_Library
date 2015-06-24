#通信
---
##http连接
+ 请求方法 httpConnection
+ 请求参数
	<table>
		<tr>
			<td>返回类型</td>
			<td>参数名称</td>
			<td>参数说明</td>
		</tr>
		<tr>
			<td>ArrayList<String></td>
			<td>keysName</td>
			<td>传输的数据字段的名称</td>
		</tr>
		<tr>
			<td>ArrayList<String></td>
			<td>keysValue</td>
			<td>传输的数据字段的内容</td>
		</tr>
		<tr>
			<td>String</td>
			<td>url</td>
			<td>处理该请求的文件路径</td>
		</tr>
	</table>
+ 返回参数 String 

---
#用户信息
---
##登录
+ 请求方法 login
+ 请求参数
	<table>
		<tr>
			<td>返回类型</td>
			<td>参数名称</td>
			<td>参数说明</td>
		</tr>
		<tr>
			<td>String</td>
			<td>username</td>
			<td>用户名</td>
		</tr>
		<tr>
			<td>String</td>
			<td>password</td>
			<td>用户密码</td>
		</tr>
	</table>
+ 返回参数 String errorCode
+ errorCode类型说明
	<table>
		<tr>
			<td>errorCode</td>
			<td>description</td>
		</tr>
		<tr>
			<td>1000</td>
			<td>登录成功</td>
		</tr>
		<tr>
			<td>1001</td>
			<td>用户名或密码错误</td>
		</tr>
	</table>

+ 样例：{"errorCode":"1000"}
##我的借阅
+ 请求方法 getMyBorrowlist
+ 请求参数
	<table>
		<tr>
			<td>参数类型</td>
			<td>参数名称</td>
			<td>参数说明</td>
		</tr>
		<tr>
			<td>String</td>
			<td>userId</td>
			<td>用户名称</td>
		</tr>
	</table>
+ 返回参数 
		

+ errorCode类型说明
	<table>
		<tr>
			<td>errorCode</td>
			<td>description</td>
		</tr>
		<tr>
			<td>2000</td>
			<td>获取成功</td>
		</tr>
		<tr>
			<td>2001</td>
			<td>获取失败</td>
		</tr>
	</table>
+ 样例



##我的心愿单
+ 请求方法 getMyWishlist
+ 请求参数 
	<table>
		<tr>
			<td>参数类型</td>
			<td>参数名称</td>
			<td>参数说明</td>
		</tr>
		<tr>
			<td>String</td>
			<td>userId</td>
			<td>用户名称</td>
		</tr>
	</table>
+ 返回参数 {JSON}
+ errorCode类型说明
	<table>
		<tr>
			<td>errorCode</td>
			<td>description</td>
		</tr>
		<tr>
			<td>3000</td>
			<td>获取成功</td>
		</tr>
		<tr>
			<td>3001</td>
			<td>获取失败</td>
		</tr>
	</table>
+ 样例:{"errorCode":"3000", "iterms":[]}
##修改密码
+ 请求方法 setPassword
+ 请求参数
		<table>
		<tr>
			<td>参数类型</td>
			<td>参数名称</td>
			<td>参数说明</td>
		</tr>
		<tr>
			<td>String</td>
			<td>oldPassword</td>
			<td>原来的密码</td>
		</tr>
		<tr>
			<td>String</td>
			<td>newPassword</td>
			<td>新的密码</td>
		</tr>
	</table>
+ 返回参数 String errorCode
	<table>
		<tr>
			<td>errorCode</td>
			<td>description</td>
		</tr>
		<tr>
			<td>2000</td>
			<td>修改成功</td>
		</tr>
		<tr>
			<td>2001</td>
			<td>修改失败</td>
		</tr>
	</table>

---
#借还
---
##借书
+ 请求方法 borrowBook
+ 请求参数 
	<table>
		<tr>
			<td>参数类型</td>
			<td>参数名称</td>
			<td>参数说明</td>
		</tr>
		<tr>
			<td>String</td>
			<td>bookId</td>
			<td>图书ID</td>
		</tr>
		<tr>
			<td>String</td>
			<td>userId</td>
			<td>用户ID</td>
		</tr>
	</table>
+ 返回参数
	<table>
		<tr>
			<td>errorCode</td>
			<td>description</td>
		</tr>
		<tr>
			<td>3000</td>
			<td>借书成功</td>
		</tr>
		<tr>
			<td>3001</td>
			<td>借书失败</td>
		</tr>
	</table>

##还书
+ 请求方法 retBook
+ 请求参数
	<table>
		<tr>
			<td>参数类型</td>
			<td>参数名称</td>
			<td>参数说明</td>
		</tr>
		<tr>
			<td>String</td>
			<td>bookId</td>
			<td>图书ID</td>
		</tr>
		<tr>
			<td>String</td>
			<td>userId</td>
			<td>用户ID</td>
		</tr>
	</table>
+ 返回参数
	<table>
		<tr>
			<td>errorCode</td>
			<td>description</td>
		</tr>
		<tr>
			<td>4000</td>
			<td>还书成功</td>
		</tr>
		<tr>
			<td>4001</td>
			<td>还书失败</td>
		</tr>
	</table>

---
#书籍信息
---
##获取书籍简略信息(丢弃)
+ 请求方法 getBookInfo
+ 请求参数
	<table>
		<tr>
			<td>参数类型</td>
			<td>参数名称</td>
			<td>参数说明</td>
		</tr>
		<tr>
			<td>String</td>
			<td>bookId</td>
			<td>图书ID</td>
		</tr>
	</table>
+ 返回参数{JSON}

##获取书籍详细信息（丢弃）
+ 请求方法 getBookDetail
+ 请求参数 
	<table>
		<tr>
			<td>参数类型</td>
			<td>参数名称</td>
			<td>参数说明</td>
		</tr>
		<tr>
			<td>String</td>
			<td>bookId</td>
			<td>图书ID</td>
		</tr>
	</table>
+ 返回参数 {JSON}

##获取书籍列表
+ 请求方法 getBooklist
+ 请求参数 无
+ 返回参数 {JSON}

---
#心愿单信息
---
##获取心愿单简略信息（丢弃）
+ 请求方法 getWishBookInfo
+ 请求参数 
	<table>
		<tr>
			<td>参数类型</td>
			<td>参数名称</td>
			<td>参数说明</td>
		</tr>
		<tr>
			<td>String</td>
			<td>bookId</td>
			<td>图书ID</td>
		</tr>
	</table>
+ 返回参数 {JSON}

##获取心愿单详情（丢弃）
+ 请求方法 getWishBookDetail
+ 请求参数 
	<table>
		<tr>
			<td>参数类型</td>
			<td>参数名称</td>
			<td>参数说明</td>
		</tr>
		<tr>
			<td>String</td>
			<td>bookId</td>
			<td>图书ID</td>
		</tr>
	</table>
+ 返回参数 {JSON}

##获取心愿单列表
+ 请求方法 getWishlist
+ 请求参数 无
+ 返回参数 {JSON}

