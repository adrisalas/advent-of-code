package main

import (
	"fmt"

	"github.com/adrisalas/advent-of-code/golang/2024/utils"
)

func main() {
	reports := utils.ReadFileToIntMatrix("day02")

	fmt.Println(part1(reports))
	fmt.Println(part2(reports))
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
		levelsWithoutI := append([]int{}, levels[:i]...)
		levelsWithoutI = append(levelsWithoutI, levels[i+1:]...)
		if isValid(levelsWithoutI) {
			return true
		}
	}

	return false
}
