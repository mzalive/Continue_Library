<table>
	<tr>
		<td>error_code</td>
		<td>description</td>
	</tr>
	<tr>
		<td>1000</td>
		<td>请求成功</td>
	</tr>
	<tr>
		<td>2001</td>
		<td>授权失败</td>
	</tr>
	<tr>
		<td>2002</td>
		<td>书籍已借完</td>
	</tr>
	<tr>
		<td>2003</td>
		<td>书籍已还完</td>
	</tr>
	<tr>
		<td>0000</td>
		<td>无结果</td>
	</tr>
</table>



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
			<td>keys_name</td>
			<td>传输的数据字段的名称</td>
		</tr>
		<tr>
			<td>ArrayList<String></td>
			<td>keys_value</td>
			<td>传输的数据字段的内容</td>
		</tr>
		<tr>
			<td>String</td>
			<td>url</td>
			<td>处理该请求的文件路径</td>
		</tr>
	</table>
+ 返回参数 String result

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
+ 返回参数 String error_code
+ 样例：{"error_code":"1000"}
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
			<td>user_id</td>
			<td>用户名称</td>
		</tr>
	</table>
+ 返回参数 {JSON}
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
			<td>user_id</td>
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
			<td>old_password</td>
			<td>原来的密码</td>
		</tr>
		<tr>
			<td>String</td>
			<td>new_password</td>
			<td>新的密码</td>
		</tr>
		<tr>
			<td>String</td>
			<td>user_id</td>
			<td>用户ID</td>
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
			<td>book_id</td>
			<td>图书ID</td>
		</tr>
		<tr>
			<td>String</td>
			<td>user_id</td>
			<td>用户ID</td>
		</tr>
	</table>
+ 返回参数

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
			<td>book_id</td>
			<td>图书ID</td>
		</tr>
		<tr>
			<td>String</td>
			<td>user_id</td>
			<td>用户ID</td>
		</tr>
	</table>
+ 返回参数

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
+ 返回参数{JSON} 见文档最下

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
+ 返回参数 {JSON} 见文档最下

##获取书籍列表
+ 请求方法 getBooklist
+ 请求参数 无
+ 返回参数 {JSON}
+ 样例
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
+ 返回参数 {JSON} 见文档最下

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

##我也想看
+ 请求方法 addHeat
+ 请求参数
		<table>
		<tr>
			<td>参数类型</td>
			<td>参数名称</td>
			<td>参数说明</td>
		</tr>
		<tr>
			<td>String</td>
			<td>user_id</td>
			<td>用户ID</td>
		</tr>
		<tr>
			<td>String</td>
			<td>book_isbn</td>
			<td>书籍的条形码</td>
		</tr>
		<tr>
			<td>boolean</td>
			<td>isOwned</td>
			<td>wishlist是否已经有这本书了</td>
		</tr>
	</table>		 
+ 返回参数 String error_code

##获取心愿单列表
+ 请求方法 getWishlist
+ 请求参数 无
+ 返回参数 {JSON} 见文档最下

---
# 返回JSON的格式
---
## 图书（book对象）
	{
		"isbn" : "9787505715660",	
		"title" : "小王子",
		"subtitle" : "",
		"publisher" : "中国友谊出版公司",
		"author" : [
			"（法）圣埃克苏佩里"
		],
		"summary" : "小王子驾到！大家好，我是小王子，生活在B612星球.",
		"image" : "http:\/\/img1.douban.com\/lpic\/s1001902.jpg",
		"pubdate" : "2000-9-1",
		"status" : {
			"isInStock" : false,		//true -> 图书在库；false -> 图书在WL；
			"amount_total" : 5,			//[图书在库] 库存总数 ［default 0］
			"amount_available" : 3,		//[图书在库] 可借数目 ［default 0］
			"heat" : 0,					//[图书在WL] 想看的人数 ［default 0］
			"isWanted" : false			//[图书在WL] 获取信息的用户已经点过“我也想看” ［default false］
		}
	}
	
	
## 图书列表
	{
		"errorCode" : "200",	//通用错误码，另外定义
		"start" : "0",		//结果的offset
		"count" : "20",		//结果的条数
		"total" : 500,		//可返回的总数据条数
		"books" : [book,]		//book对象的数组
	}
		