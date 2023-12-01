## 项目简述
### 三维四子棋Web服务器：
- springBoot项目，后续转为springCloud项目
- 用于展示三维四子棋的相关网站，如选择AI，本地，联机，熄灯模式
- 将前端页面部署在Nginx反向代理服务器，并转发后端请求
- 实现联机的与后端的对接
- springSecurity登录界面
- 新增游戏中用户信息，发送信息等功能，新增游戏大厅
- 将前端服务器大部分适配手机
- 整合web3d引擎，展示三维四子棋画面，以及加载画面等 <br>

## 部署条件:
后端服务器仅提供接口访问，前后端分离 <br>
前端依赖nginx服务器转发请求 <br>
[nginx服务器下载地址:https://github.com/MrOwenovo/nginx-KarGoBang](https://github.com/MrOwenovo/nginx-KarGoBang)

## 首页地址:
[三维四子棋首页:https://kargobang.top/](https://kargobang.top/)


## 后端接口文档swagger访问地址:
[swagger文档:https://kargobang.top/swagger-ui/](https://kargobang.top/swagger-ui/)

## 项目内容:
> ### KarGoBangWebServer内容:
![1.png](https://s2.loli.net/2022/08/17/r1GHgfbmvZFnKpM.png)
![2.png](https://s2.loli.net/2022/08/17/EO8tVco2jl1Yzgu.png)
<p align="center">首页内容</p>

![web1.png](https://s2.loli.net/2022/07/01/6Lplj4e1DQzHbUd.png)
<p align="center">springSecurity登录界面</p>

![3.png](https://s2.loli.net/2022/08/17/tn5hapXFLEHWvsm.png)
<p align="center">维四子棋-AI,本地,熄灯模式选择</p>

![wev4.png](https://s2.loli.net/2022/07/01/rN7jUsVJqiX2Fbz.png)
<p align="center">三维四子棋AI-困难,中等,简单选择</p>

![web5.png](https://s2.loli.net/2022/07/01/xs9tub6zK5LFXPV.png)
<p align="center">自定义联机界面</p>

![web7.png](https://s2.loli.net/2022/07/01/C9EMjskc2Iz7YXh.png)
<p align="center">联机创建房间-加密</p>

![web8.png](https://s2.loli.net/2022/07/01/7dyaiexk65EslnP.png)
<p align="center">联机创建房间成功-等待对方加入</p>

![0730fb6fea44c4d71e5a0406a78d77b6.png](https://img.gejiba.com/images/0730fb6fea44c4d71e5a0406a78d77b6.png)
<p align="center">房间界面游戏大厅</p>

![wbe2.png](https://s2.loli.net/2022/07/01/bIhHDMVkUL2Z5Tw.png)
<p align="center">三维四子棋加载界面</p>

![2df0c2696a2dceddde3ab40212ec4871.png](https://img.gejiba.com/images/2df0c2696a2dceddde3ab40212ec4871.png)
<p align="center">加载界面用户信息以及发送信息</p>

![web9.png](https://s2.loli.net/2022/07/01/8oAu592ghDTQMsx.png)
<p align="center">三维四子棋操作提示</p>

![web10.png](https://s2.loli.net/2022/07/01/UcNkqfzlKa5B4JV.png)
<p align="center">AI游戏过程画面</p>


<br>
