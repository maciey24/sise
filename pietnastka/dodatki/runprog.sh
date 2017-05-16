#!/usr/bin/env bash
#
# Runs a program supposed to solve the 15-puzzle in a batch mode using a
# sequence of various strategies for searching the state space and applying them
# to all the initial states of the puzzle stored in files in the current
# directory.
#
# The names of the files storing the initial states of the puzzle should obey
# the following convention:
#  size_depth_id.txt
# for example:
#  4x4_01_0001.txt
#
# TODO: Change variable $progname according to the name of the actual program,
# using the absolute (or relative) path, for example:
#  progname="/home/user/15puzzle/bin/solver" (native code)
#  progname="./solver" (native code)
#  progname="java /home/user/15puzzle/bin/solver" (Java class)
#  progname="java -jar /home/user/15puzzle/bin/solver.jar" (executable jar file)

progname="java -jar ../../target/pietnastka-1.0-SNAPSHOT.jar"
cd uklady
orders=(RDUL RDLU DRUL DRLU LUDR LURD ULDR ULRD)
heuristics=(hamm manh)
filename_regex="^[a-zA-Z0-9]+_[0-9]+_[0-9]+.txt$"

runprog()
{
    for filename in *; do
        if [[ -f "$filename" && "$filename" =~ $filename_regex ]]; then
            filename_root=$(echo "$filename" | cut -d '.' -f 1)
            filename_root="${filename_root}_${1}_${2,,}"
            $progname "$1" "$2" "$filename" "${filename_root}_sol.txt" \
                "${filename_root}_stats.txt"
        fi
    done
}

runbfs()
{
    echo "===> Strategy: bfs <==="
    for o in ${orders[@]}; do
        echo " -> Order: $o"
        runprog bfs "$o"
    done
}

rundfs()
{
    echo "===> Strategy: dfs <==="
    for o in ${orders[@]}; do
        echo " -> Order: $o"
        runprog dfs "$o"
    done
}

runastr()
{
    echo "===> Strategy: astr <==="
    for h in ${heuristics[@]}; do
        echo " -> Heuristic: $h"
        runprog astr "$h"
    done
}

runbfs
rundfs
runastr
