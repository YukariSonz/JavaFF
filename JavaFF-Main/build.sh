#Where the script is located
BASEDIR=$(dirname "$0")
#Where the shell user actually ran the script from
ORIGINALDIR=$(pwd)
cd $BASEDIR
rm -rf build
mkdir build
find "$PWD" -name "*.java" > build/sources.txt
cd build
javac -d . -Xlint:deprecation @sources.txt
rm sources.txt
cd $ORIGINALDIR