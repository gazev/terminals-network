#! /bin/bash

echo "$0"


for x in $2/*.in; do
    if [ -e ${x%.in}.import ]; then
	java -cp "$1" -Dimport=${x%.in}.import -Din=$x -Dout=${x%.in}.outhyp prr.app.App;
    else
	java -cp "$1" -Din=$x -Dout=${x%.in}.outhyp prr.app.App;
    fi

    diff -cB -w ${x%.in}.out ${x%.in}.outhyp > ${x%.in}.diff ;
    if [ -s ${x%.in}.diff ]; then
        echo "FAIL: $x. See file ${x%.in}.diff " ;
    else
        echo -n "."
        rm -f ${x%.in}.diff ${x%.in}.outhyp ; 
    fi
done

#rm -f saved*

echo "Done."

