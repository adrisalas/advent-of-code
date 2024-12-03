package main

import (
	"testing"
)

func Test_part1(t *testing.T) {
	input := "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
	want := 161

	got := part1(input)

	if want != got {
		t.Errorf("Got %d, want %d", got, want)
	}
}

func Test_part2(t *testing.T) {
	input := "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
	want := 48

	got := part2(input)

	if want != got {
		t.Errorf("Got %d, want %d", got, want)
	}
}
