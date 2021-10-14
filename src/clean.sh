#!/usr/bin/bash
class_files=`find . | grep .class`
for class_file in $class_files; do
    rm $class_file --verbose
done
