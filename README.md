# community
一个社区网站项目
完成的功能包含：
社区注册功能；邮箱激活功能完成；
登录功能；忘记密码重置密码；
使用拦截器记录好了登录登出状态；
上传头像图片格式成功；
对于没有登录的用户，输入设置，上传头像界面会自动跳转到登录页面（用拦截器，自定义注解使要拦截的方法无法访问）
登录注册功能全部完成；
过滤敏感词，发布帖子
显示帖子详情页
添加评论，并完善帖子详情页
私信列表显示，未读消息
私信发送功能实现
统一异常处理
统一日志记录实现

整合redis；添加点赞功能：帖子、评论、回复
添加了点赞列表，回复列表功能；

此外，homework 完成了个人页面的所发的所有帖子页面和所有回复过的评论的页面

使用Redis优化了登录模块，重构验证码，ticket等，用户信息也缓存到了redis内存中；

测试阻塞队列

添加kafka系统通知部分：触发评论事件；触发点赞事件；触发关注事件；（需要启动kafka服务器）

显示系统通知列表：在此页面上显示的是三类消息每一类消息最近的消息；
点击可以进去分页显示每一类主题所包含的通知列表；在消息页面头部显示所有未读消息数量。

添加Elasticsearch搜索引擎，可以搜索所有帖子和标题中的关键词；（需要启动elasticsearch服务器）
进行增贴操作时，异步的（kafka消息队列来实现）将帖子信息写到了elasticsearch服务器

集成好了Spring Security，登陆检查功能，授权访问功能；绕过认证用户功能，由自己实现了认证用户功能；
实现帖子的置顶、删除、加精功能，并添加对其的访问控制功能，集成thymleaf security对页面显示进行权限控制；

添加生成长图工具，添加云服务器功能；对上传头像功能进行了重构：
在客户端将头像直接上传至云服务器；将生成的长图由服务端直接上传至云服务器；

实现了热帖排行；加入caffeine缓存，用于缓存热门帖子及数量；进行了压力测试；
