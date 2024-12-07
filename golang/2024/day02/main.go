package main

import (
	"fmt"
	"time"

	"github.com/adrisalas/advent-of-code/golang/2024/utils"
)

func main() {
	reports := utils.ReadFileToIntMatrix("day02")

	start := time.Now()
	fmt.Printf("Part1: %d\n", part1(reports))
	utils.PrintTimeTook(start)
	fmt.Printf("Part2: %d\n", part2(reports))
	utils.PrintTimeTook(start)
}

func part1(reports [][]int) int {
	numSafe := 0

	for _, levels := range reports {
		if isValid(levels) {
			numSafe++
		}
	}

	return numSafe
}

func isValid(levels []int) bool {
	inc := 0

	for i := 0; i < len(levels)-1; i++ {
		delta := levels[i] - levels[i+1]

		if i == 0 {
			inc = delta
		}

		slopeChange := (delta > 0 && inc < 0) || (delta < 0 && inc > 0)
		if delta > 3 || delta < -3 || delta == 0 || slopeChange {
			return false
		}
	}

	return true
}

func part2(reports [][]int) int {
	numSafe := 0

	for _, levels := range reports {
		if isValidWithTolerance1(levels) {
			numSafe++
		}
	}

	return numSafe
}

func isValidWithTolerance1(levels []int) bool {
	for i := 0; i < len(levels); i++ {
		levelsWithoutI := utils.RemoveElement(levels, i)
		if isValid(levelsWithoutI) {
			return true
		}
	}

	return false
}
