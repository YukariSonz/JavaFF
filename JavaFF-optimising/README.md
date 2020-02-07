# JavaFF Coursework Edition

This repo contains a clean and fresh copy of JavaFF for the 2019 AI Planning Module at King's College London.

## Getting set up 
1. Fork this repo! **don't clone it**. You will need your own copy on your repo for us to mark. To fork, click the fork button in the top right corner of [this repo](https://github.kcl.ac.uk/k1502498/JavaFF) **DO NOT RENAME YOUR REPO, AUTO MARKING WON'T WORK OTHERWISE**

2. Once forked, clone your copy of the repo (make sure that your k number is at the start of the repo, not my one!)

3. Once cloned on your desktop, you can use the shell scripts provided to compile and run JavaFF
    - `./build.sh` will build JavaFF for you
    - `./run.sh <your-domain.pddl> <your-problem.pddl>` will run the built JavaFF on your domain and problem file (this will not work unless you've built first)

**Note: These scripts were written for the Informatics Lab machines, and may not work on your personal machines (and definitely won't work on Windows)**

4. Please add me as a collaborator to your repo, on the repo, go to settings, then add collaborator. My k number is k1502498.

5. Change the name in the `team_name.txt` file, to your team name - with correct **case-sensitive** spelling - *and then commit!* This will help us work out who is in what team.

6. Commit all your changes before the deadline, _11th December, 2019, 11:59:59PM GMT_. Any changes committed after this time will be ignored (we will use the commit closest to this time that is not over)

## Rules
- Do not rename your repo, it should be JavaFF
- Do not change the build shell scripts, or the server-build shell script, these are the scripts we will run when we test your code. We will conduct these tests on the lab machines, so if you run the build script on your code on the lab machine and it doesn't compile, you know it won't compile for us
- Do not submit compiled files. This repo has been setup to ignore files compiled with the build script, but if you compile using the command line (`javac`) this might accidentally get committed. **We will not test pre-compiled files and will wipe them before testing**
- Make sure your team name is spelt correctly when you enter it into the team_name.txt file
- We will compile and run all submission on an informatics lab machine, it up to you to ensure your code base compiles by submission time!
- Do not change the README or the scripts provided, in the event that we have to make corrections either to the scripts or to the readme, if you make changes this will make it harder for us to push those changes to your repo. If you put something meaningful in these files, we will force push and overwrite them 
- If you use an IDE such as IntelliJ IDEA or Eclipse, your code must still be checked using the CLI scripts provided, it is *not sufficient that it compiles in your chosen IDE*
## Extended Guide for Command Line shell scripts
Four scripts are provided as standard with this coursework, two are more important for you than the others, and we provide them for my convenience when testing. 

- `build.sh` - A script for building your JavaFF code automatically 
- `run.sh` - A script for running a domain and problem file (and optionally piping the output plan into a file)
- `server-build.sh` _(For our use)_ - A nearly identical script to the `build.sh` script, the only difference is that the compiler output is captured into a file so I can process it automatically. It's better to use the `build.sh` script because it will show you the compiler errors, `server-build.sh` **will not**
- `benchmark_tests.sh` - A script for executing all of the PDDL benchmark domains, depots, driverlog and rovers. It should spit out the results we will be using to rank your implementations

`build.sh` and `benchmark_tests.sh` can be run on the command line simply by typing respectively

```bash
<your-javaff-repo-location>/build.sh
```
```bash
<your-javaff-repo-location>/benchmark_tests.sh
```

The `run.sh` script which can be (and should be) used to run your implementation of JavaFF should be executed like this

```bash
<your-javaff-repo-location>/run.sh <your-domain-location> <your-problem-location>
```

To get just the plan in a separate file, you can use `run.sh` like this

```bash
<your-javaff-repo-location>/run.sh <your-domain-location> <your-problem-location> <name-of-output-file>
```

for example **if I'm in my JavaFF folder** with a terminal already open I can run

```bash
./run.sh ./pddl/depots/domain.pddl ./pddl/depots/instances/instance-1.pddl
```
(This will run the first instance of the depots domain).

If I also want to save my plan to a file I could write

```bash
./run.sh ./pddl/depots/domain.pddl ./pddl/depots/instances/instance-1.pddl resulting_plan.txt
```

**Every time you make a change to your Java Code you will need to use the build script again before running**

## Useful Resources
- [The Planning Wiki](https://www.planning.wiki/) - The planning wiki has useful resources on writing PDDL (If you want a fun side project, fork and commit a page about JavaFF to the Planning Wiki - not worth any credit, but would be a great help)

