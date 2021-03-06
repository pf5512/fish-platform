# 认证流程

**前提：** 申请AppKey和SecurityKey


|参数|描述|作用|适用接口|
|------|------|-----|-----|
|expiredTime|当前时间戳      |防止DDos等无效请求|所有 |
|body       |POST请求参数   |防止参数被更改|POST请求  |
|token      |登陆凭证       |验证是否登陆|需要登录的请求,不需要登陆的请求可以省略|

将上述参数组成JSON字符串，示例：

{
"expiredTime":"当前时间戳"
"body":"body内的参数"
"token":"登陆凭证"
}

将JSON字符串进行加密.

在请求URL加上参数appkey、sign

> 加密算法：AES（对称加密）


# 认证流程

**前提：** 申请AppKey和SecurityKey


|参数|描述|作用|适用接口|
|------|------|-----|-----|
|expiredTime|当前时间戳      |防止DDos等无效请求|所有 |
|api        |请求的接口      |防止参数被更改|所有|
|body       |POST请求参数   |防止参数被更改|POST请求  |
|token      |登陆凭证       |不需要登陆的请求可以省略|

将上述参数组成JSON字符串，示例：

{  
"expiredTime":"当前时间戳"  
"api":"接口path"  
"body":"body内的参数"  
"token":"登陆凭证"  
}

将JSON字符串进行加密.

在请求URL加上参数appkey、sign

> 加密算法：AES（对称加密）

# 接口返回格式

{  
"code": 0(int) // 状态码  
"reason": ""（string） // 错误描述，没有错误则为null  
"data": 接口不同类型不同 // 业务返回的数据  
}

**code 说明**： 异常分为非业务异常（参数参数空缺）、业务异常（参数无效，密码错误等），
非业务异常的情况下，code和httpcode一致，非业务异常的情况下，httpcode 为 400，code为自定义。

|code|描述|
|------|------|
|0      |一切正常|
|10000  |未知错误|
|10001  |正在建设的api|
|10021  |用户名已经存在|
|10022  |用户名无效|
|10023  |邮箱已经存在|
|10024  |邮箱无效|
|10025  |手机号已经存在|
|10026  |手机号无效|
|10027  |用户名或密码无效|
|10028  |验证码无效|

> 客户端判断请求的正确性应该采用 code + httpcode的方式。


# 接口参数

## 获取邮箱验证码
**path :** /api/v1/authcode/email

**method :** get

**example :**
/api/v1/authcode/email?email=ansheck@163.com


## 下载文件
**path :** /api/v1/file/{id}

**method :** get

**example :** 
/api/v1/file/123456789

## 上传文件
**path :** /api/v1/file/upload

**method :** post

**example :** /api/v1/file/upload

> content-type 使用 **multipart/form-data** ,name必须为files,支持批量上传

## 添加钓鱼场
**path :** /api/v1/fishing/add

**method :** post

**example :** /api/v1/fishing/add

**body :**
{  
"summary" : "渔场信息",  
"adder" : 添加者的用户id,  
"location" : {  
"type" : "Point",  
"coordinates" : [ 1, 1 ] // 依次为 经度、纬度   
}  
}

## 获取附近的钓鱼场
**path :** /api/v1/fishing/near

**method :** get

**example :** /api/v1/fishing/near?longitude=&latitude=&maxDistance=

## 注册
**path :** /api/v1/user/regnew

**method :** post

**example :** /api/v1/user/regnew

**body :**
{  
"account":""  
"password":""  
"registerWay":"" // USERNAME, EMAIL, PHONE 三选一  
"authCode":""  
}

## 重置密码
**path :** /api/v1/user/resetpwd

**method :** post

**example :** /api/v1/user/resetpwd

**body :**
{  
"account":""  
"password":""  
"authCode":""  
}

## 获取用户信息
**path :** /api/v1/user/{id}

**method :** get

**example :** /api/v1/user/1

## 登录
**path :** /api/v1/session/login

**method :** post

**example :** /api/v1/session/login

**body :**
{  
"account":""  
"password":""   
}

## 获取天气
**path :** /api/v1/weather/

**method :** get

**example :** /api/v1/weather/?city=绵阳

## 钓友圈
**path :** /api/v1/moment/add

**method :** post

**example :** /api/v1/moment/add

**body ：**
{
    "content": "今天天气不错哟", 
    "photos": [ 
        "www.baidu.com" 
    ], 
    "video":"视频地址", 
    "sound":"音频地址", 
    "location": { 
        "type": "Point", 
        "coordinates": [ 
            "经度", 
            "纬度" 
        ] 
    } 
}

## 评论钓友圈
**path :** /api/v1/moment/comment/{id}?content="评论内容"

**method :** get

**example :** /api/v1/moment/comment/1?content="确实不错，适合钓鱼"

## 点赞钓友圈
**path :** /api/v1/moment/like/{id}

**method :** get

**example :** /api/v1/moment/like/1

## 获取钓友圈内容
**path :** /api/v1/moment/moments

**method :** get

**example :** /api/v1/moment/moments?longitude=&latitude=

## 获取钓友圈某一条内容详情
**path :** /api/v1/moment/{id}

**method :** get

**example :** /api/v1/moment/1