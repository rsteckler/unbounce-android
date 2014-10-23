cd $1
xml ed --inplace -d "/resources/string[not(text())]" strings.xml

