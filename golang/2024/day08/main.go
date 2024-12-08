package main

import (
	"fmt"
	"time"

	"github.com/adrisalas/advent-of-code/golang/2024/utils"
)

func main() {
	lines := utils.ReadFileToStringSlice("day08")

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

func part1(lines []string) int {
	antennas := getAntennas(lines)

	antinode := make(map[Pos]bool)

	rowLimit := len(lines) - 1
	colLimit := len(lines[0]) - 1
	for _, list := range antennas {
		for i := 0; i < len(list); i++ {
			for j := i + 1; j < len(list); j++ {
				a1 := list[i]
				a2 := list[j]

				rowDiff := a2.row - a1.row
				colDiff := a2.col - a1.col

				beforeA1 := Pos{a1.row - rowDiff, a1.col - colDiff}
				if isInsideLimits(beforeA1, rowLimit, colLimit) {
					antinode[beforeA1] = true
				}
				afterA2 := Pos{a2.row + rowDiff, a2.col + colDiff}
				if isInsideLimits(afterA2, rowLimit, colLimit) {
					antinode[afterA2] = true
				}
			}
		}
	}

	return len(antinode)
}

func getAntennas(lines []string) map[rune][]Pos {
	antennas := make(map[rune][]Pos)

	for row, line := range lines {
		for col, c := range line {
			if c != '.' {
				antennas[c] = append(antennas[c], Pos{row, col})
			}
		}
	}
	return antennas
}

func isInsideLimits(pos Pos, rowLimit int, colLimit int) bool {
	return pos.row >= 0 && pos.row <= rowLimit && pos.col >= 0 && pos.col <= colLimit
}

func part2(lines []string) int {

	antennas := getAntennas(lines)

	antinode := make(map[Pos]bool)

	rowLimit := len(lines) - 1
	colLimit := len(lines[0]) - 1
	for _, list := range antennas {
		for i := 0; i < len(list); i++ {
			for j := i + 1; j < len(list); j++ {
				a1 := list[i]
				antinode[a1] = true
				a2 := list[j]
				antinode[a2] = true

				rowDiff := a2.row - a1.row
				colDiff := a2.col - a1.col

				beforeA1 := Pos{a1.row - rowDiff, a1.col - colDiff}
				for isInsideLimits(beforeA1, rowLimit, colLimit) {
					antinode[beforeA1] = true
					beforeA1 = Pos{beforeA1.row - rowDiff, beforeA1.col - colDiff}
				}
				afterA2 := Pos{a2.row + rowDiff, a2.col + colDiff}
				for isInsideLimits(afterA2, rowLimit, colLimit) {
					antinode[afterA2] = true
					afterA2 = Pos{afterA2.row + rowDiff, afterA2.col + colDiff}
				}
			}
		}
	}

	return len(antinode)
}
