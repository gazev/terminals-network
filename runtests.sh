#! /bin/bash
RED="\e[31m"
GREEN="\e[32m"
NC="\e[0m"
echo "$0"


echo -e "${RED}IF JAVA ERRORS/EXCEPTIONS ARE THROWN, THE TEST WILL APPEAR AS ${GREEN}PASSED${NC}${RED}, THIS IS OBVIOUSLY FALSE${NC}"
sleep 2

for x in $2/*.in; do
    if [ -e ${x%.in}.import ]; then
	java -cp "$1" -Dimport=${x%.in}.import -Din=$x -Dout=${x%.in}.outhyp prr.app.App;
    else
	java -cp "$1" -Din=$x -Dout=${x%.in}.outhyp prr.app.App;
    fi

    diff -cB -w ${x%.in}.out ${x%.in}.outhyp > ${x%.in}.diff ;
    if [ -s ${x%.in}.diff ]; then
      echo -e "${RED}FAILED: $(basename "$x"). See file $(basename "${x%.in}").diff ${NC}" ;
    else
      echo -e "${GREEN}Passed $(basename "$x") ${NC}"
        rm -f ${x%.in}.diff ${x%.in}.outhyp ; 
    fi
done

#rm -f saved*

echo "Done"

