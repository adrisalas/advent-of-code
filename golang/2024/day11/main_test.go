package main

import (
	"testing"
)

func Test_part1(t *testing.T) {
	input := []int{125, 17}
	want := 55312

	got := part1(input)

	if want != got {
		t.Errorf("Got %d, want %d", got, want)
	}
}

func Test_part2(t *testing.T) {
	input := []int{125, 7}
	want := 52962069533494

	got := part2(input)

	if want != got {
		t.Errorf("Got %d, want %d", got, want)
	}
}
