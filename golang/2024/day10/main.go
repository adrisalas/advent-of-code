package main

import (
	"fmt"
	"strconv"
	"time"

	"github.com/adrisalas/advent-of-code/golang/2024/utils"
)

func main() {
	unparsed := utils.ReadFileToStringSlice("day10")

	var lines [][]int

	for _, line := range unparsed {
		var l []int
		for _, r := range line {
			n, _ := strconv.Atoi(string(r))
			l = append(l, n)
		}
		lines = append(lines, l)
	}

	start := time.Now()
	fmt.Printf("Part1: %d\n", part1(lines))
	utils.PrintTimeTook(start)
	fmt.Printf("Part2: %d\n", part2(lines))
	utils.PrintTimeTook(start)
}

type Pos struct {
	row int
	col int
}

func part1(lines [][]int) int {
	sum := 0
	for row, line := range lines {
		for col, n := range line {
			if n == 0 {
				sum += trailHeadScore(lines, row, col)
			}
		}
	}
	return sum
}

func trailHeadScore(lines [][]int, row, col int) int {
	var stack []Pos
	stack = append(stack, Pos{row, col})

	nines := make(map[Pos]bool)

	for len(stack) != 0 {
		pos := stack[len(stack)-1]
		stack = stack[:len(stack)-1]
		value := lines[pos.row][pos.col]
		if value == 9 {
			nines[pos] = true
		}

		if pos.row-1 >= 0 && lines[pos.row-1][pos.col] == value+1 {
			stack = append(stack, Pos{pos.row - 1, pos.col})
		}
		if pos.row+1 < len(lines) && lines[pos.row+1][pos.col] == value+1 {
			stack = append(stack, Pos{pos.row + 1, pos.col})
		}
		if pos.col-1 >= 0 && lines[pos.row][pos.col-1] == value+1 {
			stack = append(stack, Pos{pos.row, pos.col - 1})
		}
		if pos.col+1 < len(lines[0]) && lines[pos.row][pos.col+1] == value+1 {
			stack = append(stack, Pos{pos.row, pos.col + 1})
		}

	}

	return len(nines)
}

func part2(lines [][]int) int {
	sum := 0
	for row, line := range lines {
		for col, n := range line {
			if n == 0 {
				sum += trailHeadRating(lines, row, col)
			}
		}
	}
	return sum
}

func trailHeadRating(lines [][]int, row, col int) int {
	var stack []Pos
	stack = append(stack, Pos{row, col})

	var nines []Pos

	for len(stack) != 0 {
		pos := stack[len(stack)-1]
		stack = stack[:len(stack)-1]
		value := lines[pos.row][pos.col]
		if value == 9 {
			nines = append(nines, pos)
		}

		if pos.row-1 >= 0 && lines[pos.row-1][pos.col] == value+1 {
			stack = append(stack, Pos{pos.row - 1, pos.col})
		}
		if pos.row+1 < len(lines) && lines[pos.row+1][pos.col] == value+1 {
			stack = append(stack, Pos{pos.row + 1, pos.col})
		}
		if pos.col-1 >= 0 && lines[pos.row][pos.col-1] == value+1 {
			stack = append(stack, Pos{pos.row, pos.col - 1})
		}
		if pos.col+1 < len(lines[0]) && lines[pos.row][pos.col+1] == value+1 {
			stack = append(stack, Pos{pos.row, pos.col + 1})
		}

	}

	return len(nines)
}
