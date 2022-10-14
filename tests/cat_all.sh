#! /bin/bash

for file in $(find . -name \*.import);
do
	cat $file
	echo ""
done
