#!/usr/bin/env bash
#
# Extract data regarding performance of various strategies used to search the
# state space in order to solve the 15-puzzle from auxiliary files generated
# by a solver program and stored in the current directory, aggregate them in a
# tabular form and write the result to the standard output.
#
# The names of the auxiliary files should obey the following convention:
#  size_depth_id_strategy_param_stats.txt
# for example:
#  4x4_01_0001_bfs_rdul_stats.txt

filename_regex="^[a-zA-Z0-9]+_[0-9]+_[0-9]+_[a-zA-Z]+_[a-zA-Z]+_stats.txt$"

for filename in *; do
    if [[ -f "$filename" && "$filename" =~ $filename_regex ]]; then
        line=$(echo "$filename" | \
               awk -F '_' '{ printf "%d %d %s %s ", $2, $3, $4, $5 }')
        line+=$(echo $(cat "$filename"))
        echo "$line"
    fi
done
