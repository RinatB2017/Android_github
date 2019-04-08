#!/bin/bash

find . -iname "*.xml" -print0 | xargs -0 dos2unix
find . -iname "*.java" -print0 | xargs -0 dos2unix
find . -iname "*.bat" -print0 | xargs -0 dos2unix
find . -iname "*.properties" -print0 | xargs -0 dos2unix
