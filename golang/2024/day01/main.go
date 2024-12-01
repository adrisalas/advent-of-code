package main

import (
	"fmt"
	"sort"

	"github.com/adrisalas/advent-of-code/golang/2024/utils"
)

func main() {
	lines := utils.ReadFileToIntMatrix("day01")

	fmt.Println(part1(lines))
	fmt.Println(part2(lines))
}

func part1(lines [][]int) int {
	left, right := getTheTwoSlices(lines)

	sort.Ints(left)
	sort.Ints(right)

	sum := 0
	for i := range left {
		sum += utils.Abs(left[i] - right[i])
	}
	return sum
}

func getTheTwoSlices(lines [][]int) ([]int, []int) {
	var left []int
	var right []int

	for _, line := range lines {
		left = append(left, line[0])
		right = append(right, line[1])
	}

	return left, right
}

func part2(lines [][]int) int {
	left, right := getTheTwoSlices(lines)

	score := make(map[int]int)
	for _, num := range right {
		score[num]++
	}

	sum := 0
	for _, num := range left {
		sum += num * score[num]
	}

	return sum
}
