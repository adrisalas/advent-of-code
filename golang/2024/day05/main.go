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
	lines := utils.ReadFileToString("day05")

	start := time.Now()
	fmt.Printf("Part1: %d\n", part1(lines))
	utils.PrintTimeTook(start)
	fmt.Printf("Part2: %d\n", part2(lines))
	utils.PrintTimeTook(start)
}

func part1(text string) int {
	parts := strings.Split(text, "\n\n")

	order := getOrder(parts)
	print := getPrint(parts)

	count := 0

	for _, p := range print {
		if isInRightOrder(p, order) {
			count += p[len(p)/2]
		}
	}

	return count
}

func getOrder(parts []string) map[int][]int {
	order := make(map[int][]int)

	for _, line := range strings.Split(parts[0], "\n") {
		parts := strings.Split(line, "|")
		x1, _ := strconv.Atoi(parts[0])
		x2, _ := strconv.Atoi(parts[1])
		order[x1] = append(order[x1], x2)
	}
	return order
}

func getPrint(parts []string) [][]int {
	var print [][]int
	for _, line := range strings.Split(parts[1], "\n") {
		var nums []int
		for _, s := range strings.Split(line, ",") {
			n, _ := strconv.Atoi(s)
			nums = append(nums, n)
		}
		print = append(print, nums)
	}
	return print
}

// brute force
func isInRightOrder(pages []int, order map[int][]int) bool {
	seen := make(map[int]bool)

	for i := 0; i < len(pages); i++ {
		p := pages[i]
		seen[p] = true

		for _, pageAfter := range order[p] {
			if _, ok := seen[pageAfter]; ok {
				return false
			}
		}
	}
	return true
}

func part2(text string) int {
	parts := strings.Split(text, "\n\n")

	order := getOrder(parts)
	print := getPrint(parts)

	count := 0
	comparator := customComparator(order)

	for _, p := range print {
		if !isInRightOrder(p, order) {
			slices.SortFunc(p, comparator)

			count += p[len(p)/2]
		}
	}

	return count
}

func customComparator(order map[int][]int) func(a int, b int) int {
	return func(a int, b int) int {
		if slices.Contains(order[a], b) {
			return -1
		}
		if slices.Contains(order[b], a) {
			return 1
		}
		return 0
	}
}
