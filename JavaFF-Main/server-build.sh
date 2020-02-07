BASEDIR=$(dirname "$0")
echo $BASEDIR
ORIGINALDIR=$(pwd)
cd $BASEDIR
rm -rf build
mkdir build
find "$PWD" -name "*.java" > build/sources.txt
cd build
javac -d . -Xlint:deprecation -nowarn @sources.txt
rm sources.txt
cd $ORIGINALDIR