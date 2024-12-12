package main

import (
	"strings"
	"testing"
)

func Test_part1(t *testing.T) {
	input := strings.Split(`RRRRIICCFF
RRRRIICCCF
VVRRRCCFFF
VVRCCCJFFF
VVVVCJJCFE
VVIVCCJJEE
VVIIICJJEE
MIIIIIJJEE
MIIISIJEEE
MMMISSJEEE`, "\n")
	want := 1930

	got := part1(input)

	if want != got {
		t.Errorf("Got %d, want %d", got, want)
	}
}

func Test_part2(t *testing.T) {
	input := strings.Split(`RRRRIICCFF
RRRRIICCCF
VVRRRCCFFF
VVRCCCJFFF
VVVVCJJCFE
VVIVCCJJEE
VVIIICJJEE
MIIIIIJJEE
MIIISIJEEE
MMMISSJEEE`, "\n")
	want := 1206

	got := part2(input)

	if want != got {
		t.Errorf("Got %d, want %d", got, want)
	}
}
