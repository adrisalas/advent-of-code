package main

import (
	"fmt"
	"strings"
	"testing"
)

func Test_part1(t *testing.T) {
	input := strings.Split(
		`1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb787uchet`, "\n")
	want := 142

	got := part1(input)

	if want != got {
		t.Errorf("Got %d, want %d", got, want)
	}
}

func Test_part2(t *testing.T) {
	tests := []struct {
		input []string
		want  int
	}{
		{
			input: strings.Split("eightwo", "\n"),
			want:  82,
		},
		{
			input: strings.Split(
				`two1nine
		eightwothree
		abcone2threexyz
		xtwone3four
		4nineeightseven2
		zoneight234
		7pqrstsixteen`, "\n"),
			want: 281,
		},
	}

	for i, tt := range tests {
		t.Run(fmt.Sprint(i), func(t *testing.T) {
			if got := part2(tt.input); got != tt.want {
				t.Errorf("got %v, want %v", got, tt.want)
			}
		})
	}
}
