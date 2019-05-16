目前傅里叶变换需要借助python代码
读取的图片为D:\1.jpg
生成的结果会自动保存在D:\2.png

git log 查看日志
git reset --hard HEAD^ 返回上一个版本
git reset --hard 加版本号回到指定版本
git reflog 记录每一次命令
git diff HEAD -- readme.txt 比较工作区和版本库里面最新版本的区别
git checkout -- file 恢复到上一次commit或者add的情况
git reset HEAD readme.txt 从暂存区退回到工作区

关联一个远程库，使用命令git remote add origin git@server-name:path/repo-name.git；
关联后，使用命令git push -u origin master第一次推送master分支的所有内容；
此后，每次本地提交后，只要有必要，就可以使用命令git push origin master推送最新修改；

git checkout -b dev 创建dec分支并切换到dev分支
==git branch dev
  git checkout dev
  git branch 查看当前分支
git merge dev 将dev分支合并到master
git merge --no-ff -m "merge with no-ff" dev 禁用快速合并，生成一次新的commit
git stash 保留工作空间
git stash apply 恢复 git stash drop 删除
git stash pop 恢复并删除

查看远程库信息，使用git remote -v；
本地新建的分支如果不推送到远程，对其他人就是不可见的；
从本地推送分支，使用git push origin branch-name，如果推送失败，先用git pull抓取远程的新提交；
在本地创建和远程分支对应的分支，使用git checkout -b branch-name origin/branch-name，本地和远程分支的名称最好一致；
建立本地分支和远程分支的关联，使用git branch --set-upstream branch-name origin/branch-name；
从远程抓取分支，使用git pull，如果有冲突，要先处理冲突。

git tag v1.0
git tag -a v1.1 -m "v1.1" commitID