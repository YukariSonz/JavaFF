BASEDIR=$(dirname "$0")
ORIGINALDIR=$(pwd)

echo "WARNING: I run about 60 tests, many may not solve, or may take a long time to solve, to cancel the current test, press CTRL+C"
echo "To cancel all, hold CTRL+C until all tests are cancelled"

echo 'Recompiling code with Server Build Script'
$BASEDIR/server-build.sh

echo 'Making a output directory for' $(date)
OUTDIR=tests/$(date +'test-results-%Y%m%d-%H%M%S')
cd $BASEDIR
if [ ! -w tests ]
then 
    mkdir tests
fi
mkdir $OUTDIR
touch $OUTDIR/results.csv
echo 'instance,time,initial-heuristic,cost' >> $OUTDIR/results.csv
echo 'Running Depots Tests...'
echo 'depots,' >> $OUTDIR/results.csv
for TEST in {1..22} 
do
    echo "Running instance "$TEST
    echo -n 'instance-'$TEST >> $OUTDIR/results.csv 
    echo -n ',' >> $OUTDIR/results.csv
    RESULT=$(./run.sh ./pddl/depots/domain.pddl ./pddl/depots/instances/instance-$TEST.pddl ./$OUTDIR/depots-plan-instance-$TEST.sol)
    echo -n $(echo "$RESULT" | tail -3 | head -1) >> $OUTDIR/results.csv
    echo -n ',' >> $OUTDIR/results.csv
    echo -n $(echo "$RESULT" | head -4 | tail -1 | tr -dc '0-9') >> $OUTDIR/results.csv
    echo -n ',' >> $OUTDIR/results.csv
    echo "$RESULT" | tail -1 >> $OUTDIR/results.csv
done


echo 'Running Driverlog Tests...'
echo 'driverlog,' >> $OUTDIR/results.csv
for TEST in {1..20} 
do
    echo "Running instance "$TEST
    echo -n 'instance-'$TEST >> $OUTDIR/results.csv 
    echo -n ',' >> $OUTDIR/results.csv
    RESULT=$(./run.sh ./pddl/driverlog/domain.pddl ./pddl/driverlog/instances/instance-$TEST.pddl ./$OUTDIR/driverlog-plan-instance-$TEST.sol)
    echo -n $(echo "$RESULT" | tail -3 | head -1) >> $OUTDIR/results.csv
    echo -n ',' >> $OUTDIR/results.csv
    echo -n $(echo "$RESULT" | head -4 | tail -1 | tr -dc '0-9') >> $OUTDIR/results.csv
    echo -n ',' >> $OUTDIR/results.csv
    echo "$RESULT" | tail -1 >> $OUTDIR/results.csv
done

echo 'Running Rovers Tests...'
echo 'rovers,' >> $OUTDIR/results.csv
for TEST in {1..20} 
do
    echo "Running instance "$TEST
    echo -n 'instance-'$TEST >> $OUTDIR/results.csv 
    echo -n ',' >> $OUTDIR/results.csv
    RESULT=$(./run.sh ./pddl/rovers/domain.pddl ./pddl/rovers/instances/instance-$TEST.pddl ./$OUTDIR/rovers-plan-instance-$TEST.sol)
    echo -n $(echo "$RESULT" | tail -3 | head -1) >> $OUTDIR/results.csv
    echo -n ',' >> $OUTDIR/results.csv
    echo -n $(echo "$RESULT" | head -4 | tail -1 | tr -dc '0-9') >> $OUTDIR/results.csv
    echo -n ',' >> $OUTDIR/results.csv
    echo "$RESULT" | tail -1 >> $OUTDIR/results.csv
done

echo 'done'