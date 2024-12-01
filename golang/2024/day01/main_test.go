package main

import (
	"testing"
)

func Test_part1(t *testing.T) {
	input := [][]int{
		{3, 4},
		{4, 3},
		{2, 5},
		{1, 3},
		{3, 9},
		{3, 3},
	}
	want := 11

	got := part1(input)

	if want != got {
		t.Errorf("Got %d, want %d", got, want)
	}
}

func Test_part2(t *testing.T) {
	input := [][]int{
		{3, 4},
		{4, 3},
		{2, 5},
		{1, 3},
		{3, 9},
		{3, 3},
	}
	want := 31

	got := part2(input)

	if want != got {
		t.Errorf("Got %d, want %d", got, want)
	}
}
