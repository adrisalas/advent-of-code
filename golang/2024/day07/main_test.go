package main

import (
	"strings"
	"testing"
)

func Test_part1(t *testing.T) {
	input := strings.Split(`190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20`, "\n")
	want := 3749

	got := part1(input)

	if want != got {
		t.Errorf("Got %d, want %d", got, want)
	}
}

func Test_part2(t *testing.T) {
	input := strings.Split(`190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20`, "\n")
	want := 11387

	got := part2(input)

	if want != got {
		t.Errorf("Got %d, want %d", got, want)
	}
}
