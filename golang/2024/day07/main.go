package main

import (
	"fmt"
	"slices"
	"strconv"
	"strings"
	"time"

	"github.com/adrisalas/advent-of-code/golang/2024/utils"
)

func main() {
	lines := utils.ReadFileToStringSlice("day07")

	start := time.Now()
	fmt.Printf("Part1: %d\n", part1(lines))
	utils.PrintTimeTook(start)
	fmt.Printf("Part2: %d\n", part2(lines))
	utils.PrintTimeTook(start)
}

func part1(lines []string) int {
	count := 0

	for _, line := range lines {
		aux := strings.Split(line, ": ")
		result, _ := strconv.Atoi(aux[0])
		words := strings.Fields(aux[1])

		var nums []int
		for _, w := range words {
			n, _ := strconv.Atoi(w)
			nums = append(nums, n)
		}

		var results []int
		generateResults(nums, 1, nums[0], &results, false)

		if slices.Contains(results, result) {
			count += result
		}
	}

	return count
}

func generateResults(nums []int, index int, acc int, results *[]int, part2 bool) {
	if index == 0 {
		panic("Undefined, index shouuld start in 1 to have the base case in accumulator")
	}
	if index == len(nums) {
		*results = append(*results, acc)
		return
	}
	generateResults(nums, index+1, acc+nums[index], results, part2)
	generateResults(nums, index+1, acc*nums[index], results, part2)
	if part2 {
		n, _ := strconv.Atoi(fmt.Sprintf("%d%d", acc, nums[index]))
		generateResults(nums, index+1, n, results, part2)
	}
}

func part2(lines []string) int {
	count := 0

	for _, line := range lines {
		aux := strings.Split(line, ": ")
		result, _ := strconv.Atoi(aux[0])
		words := strings.Fields(aux[1])

		var nums []int
		for _, w := range words {
			n, _ := strconv.Atoi(w)
			nums = append(nums, n)
		}

		var results []int
		generateResults(nums, 1, nums[0], &results, true)

		if slices.Contains(results, result) {
			count += result
		}
	}

	return count
}
