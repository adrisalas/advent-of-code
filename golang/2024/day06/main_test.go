package main

import (
	"strings"
	"testing"
)

func Test_part1(t *testing.T) {
	input := strings.Split(`....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...`, "\n")
	want := 41

	got := part1(input)

	if want != got {
		t.Errorf("Got %d, want %d", got, want)
	}
}

func Test_part2(t *testing.T) {
	input := strings.Split(`....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...`, "\n")
	want := 6

	got := part2(input)

	if want != got {
		t.Errorf("Got %d, want %d", got, want)
	}
}
