BASEDIR=$(dirname "$0")
ORIGINALDIR=$(pwd)


#Standard sanity check, 2 paths provided, the first not marked help and both lead to files we can read
if [ -z $1 ] || [ -z $2 ] || [ $1 = '-h' ] || [ $1 = '--help' ] || [ ! -r $1 ] || [ ! -r $2 ]
then
    echo "to run JavaFF, use <your-javaff-location/run.sh <your-domain> <your-problem>"
else
    #the domain and problem the shell user gave us converted to absolute paths
    DOMAIN_FILE=$(readlink -f "$1")
    PROBLEM_FILE=$(readlink -f "$2")
    #If a destination folder has been provided, better convert it too
    if [ -n $3 ]
    then
        OUTPUT_FILE=$(readlink -f "$3")
    else
        OUTPUT_FILE=""
    fi
    #change directory into the build directory
    cd $BASEDIR/build
    #fire up java with the
    java javaff.JavaFF $DOMAIN_FILE $PROBLEM_FILE 0 $OUTPUT_FILE
    cd $ORIGINALDIR
fi

