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
+ 返回参数 {JSON}


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
+ 返回参数

---
#借还
---
##借书
+ 请求方法 borrowBook
+ 请求参数 
	
+ 返回参数

##还书
+ 请求方法 retBook
+ 请求参数

+ 返回参数

---
#书籍信息
---
##获取书籍详情
+ 请求方法
+ 请求参数
+ 返回参数


<table>
	<table>
		<tr>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
		</tr>
	</table>