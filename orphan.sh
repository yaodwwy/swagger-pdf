#!/usr/bin/env bash

git checkout --orphan latest_branch
git add -A
git commit -m "initial commit"
echo "本地初始化为 master..."
git branch -D master
git branch -m master
echo "强制更新远程 master..."
git push -f origin master