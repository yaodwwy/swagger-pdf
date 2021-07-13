#!/usr/bin/env bash

git fetch origin
git checkout -B temp-"$(date "+%m%d-%H%M%S")"
git branch -D master
git prune
git checkout -B "$(git config user.name)" origin/master