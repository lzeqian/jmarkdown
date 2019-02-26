@[TOC](docker-machine)
# docker-machine简介
Docker Machine是一个工具，用来在虚拟主机上安装Docker Engine，并使用 docker-machine命令来管理这些虚拟主机
您可以docker-machine完成以下工作：
- 在Mac或Windows上安装和运行docker（类似于在window上安装多个虚拟机），
- 提供和管理多个远程docker主机
- 提供安装Swarm 集群

# docker-machine安装
官方安装文档：https://docs.docker.com/machine/install-machine/
安装过程（linux执行）
```
base=https://github.com/docker/machine/releases/download/v0.16.0 &&
  curl -L $base/docker-machine-$(uname -s)-$(uname -m) >/tmp/docker-machine &&
  sudo install /tmp/docker-machine /usr/local/bin/docker-machine
```
如果下载速度慢 可以去github下载https://github.com/docker/machine/releases
拷贝到linux 比如名字是docker-machine-Linux-x86_64当前目录下执行
```
mv ./docker-machine-Linux-x86_64  /usr/local/bin/docker-machine
[root@swarm04 ~]# 
```
添加执行权限
```
[root@swarm04 ~]# chmod +x /usr/local/bin/docker-machine
[root@swarm04 ~]# docker-machine -v                     
docker-machine version 0.16.1, build cce350d7
```
给docker-machine取个别名 文件名太长 将alias可以写入/root/.bash_profile

```
[root@swarm04 ~]# alias dm=docker-machine
[root@swarm04 ~]# dm -v
docker-machine version 0.16.1, build cce350d7
```

卸载只需要删除该文件即可

# docker-machine安装虚拟机
docker-machine安装虚拟机必须使用第三方的驱动 比如virtualbox 
下载centos7版本[virtualbox](https://download.virtualbox.org/virtualbox/6.0.4/VirtualBox-6.0-6.0.4_128413_el7-1.x86_64.rpm)

拷贝到linux执行命令（一般安装依赖很多包 安装较复杂 不推荐）

```
rpm -ivh VirtualBox-6.0-6.0.4_128413_el7-1.x86_64.rpm
```
进入https://www.virtualbox.org/wiki/Linux_Downloads到最下面的
RPM-based Linux distributions章节
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190225222425985.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpYW9taW40MTYxMDA1Njk=,size_16,color_FFFFFF,t_70)
找到连接 获取

```
cd /etc/yum.repos.d && wget https://download.virtualbox.org/virtualbox/rpm/el/virtualbox.repo
```
使用yum安装，自动安装依赖（注意安装virtualbox5.0别安装6.0否则 提示不支持5.0）
```
yum install VirtualBox-5.0
```
官方支持的驱动有下面这些

- amazonec2
- azure
- digitalocean
- exoscale
- generic
- google
- hyperv
- none
- openstack
- rackspace
- softlayer
- virtualbox
- vmwarevcloudair
- vmwarefusion
- vmwarevsphere

创建一个virtualbox的虚拟机名称为default
```
docker-machine create --driver virtualbox default
```
启动报错

```
[root@swarm04 yum.repos.d]# docker-machine create --driver virtualbox default![在这里插入图片描述](https://img-blog.csdnimg.cn/20190226004118449.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpYW9taW40MTYxMDA1Njk=,size_16,color_FFFFFF,t_70)
Running pre-create checks...
Error with pre-create check: "We support Virtualbox starting with version 5. Your VirtualBox install is \"WARNING: The vboxdrv kernel module is not loaded. Either there is no module\\n         available for the current kernel (3.10.0-327.el7.x86_64) or it failed to\\n         load. Please recompile the kernel module and install it by\\n\\n           sudo /sbin/rcvboxdrv setup\\n\\n         You will not be able to start VMs until this problem is fixed.\\n5.0.40r115130\". Please upgrade at https://www.virtualbox.org"
```
安装支持内核

```
yum -y install kernel-devel
```
查看uname -r 和/usr/src/kernel内核版本不匹配
此时 下载uname -r 对应版本的最大的iso镜像里面会包含 devel源码包
```
[root@swarm04 kernels]# uname -r
3.10.0-327.el7.x86_64
```
下载该版本的的centos（你自己安装的机器总该有镜像iso吧 因为之前下的mini版本所以不带源码包）centos版本不等于内核版本哦 
http://mirror.nsc.liu.se/centos-store/7.2.1511/isos/x86_64/（4G版本DVD版本）
比如我的mini版本中没有kernel-devel 肯定是有内核kernel的和uname -r一致
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190226004245434.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpYW9taW40MTYxMDA1Njk=,size_16,color_FFFFFF,t_70)
dvd版本中
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190226004726623.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpYW9taW40MTYxMDA1Njk=,size_16,color_FFFFFF,t_70)
删除之前源码包

```
yum autoremove kernel-devel
```
将该包上传到linux使用 rpm安装
```
[root@swarm04 kernels]# rpm -ivh kernel-devel-3.10.0-327.el7.x86_64.rpm 
Preparing...                          ################################# [100%]
Updating / installing...
   1:kernel-devel-3.10.0-327.el7      ################################# [100%]
[root@swarm04 kernels]# ll
total 11208
drwxr-xr-x 22 root root     4096 Feb 25 08:50 3.10.0-327.el7.x86_64
```
根据内核重新配置 virtualbox

```
[root@swarm04 kernels]# /sbin/vboxconfig
vboxdrv.sh: Stopping VirtualBox services.
vboxdrv.sh: Starting VirtualBox services.
vboxdrv.sh: Building VirtualBox kernel modules.
```
再次测试创建 docker-machine
```
[root@swarm04 kernels]# docker-machine create --driver virtualbox default
Running pre-create checks...
Error with pre-create check: "This computer doesn't have VT-X/AMD-v enabled. Enabling it in the BIOS is mandatory"
```
因为我是用vmware安装的centos7打开虚拟化，物理机bios中设置开启即可
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190226005751779.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpYW9taW40MTYxMDA1Njk=,size_16,color_FFFFFF,t_70)
之后就是正常的 需要下载boot2docker.iso所有速度会比较慢












# docker-machine管理docker主机


