package main

import (
	"fmt"
	"math"
	"sort"

	"github.com/adrisalas/advent-of-code/golang/2024/utils"
)

func main() {
	lines := utils.ReadFileToIntMatrix("day01")

	fmt.Println(part1(lines))
	fmt.Println(part2(lines))
}

func part1(lines [][]int) int {
	var leftList []int
	var rightList []int

	for _, line := range lines {
		leftList = append(leftList, line[0])
		rightList = append(rightList, line[1])
	}

	sort.Ints(leftList)
	sort.Ints(rightList)

	sum := 0
	for i, num := range leftList {
		sum += int(math.Abs(float64(num) - float64(rightList[i])))
	}
	return sum
}

func part2(lines [][]int) int {
	var leftList []int
	var rightList []int

	for _, line := range lines {
		leftList = append(leftList, line[0])
		rightList = append(rightList, line[1])
	}

	score := make(map[int]int)
	for _, num := range rightList {
		score[num]++
	}

	sum := 0
	for _, num := range leftList {
		sum += num * score[num]
	}

	return sum
}
