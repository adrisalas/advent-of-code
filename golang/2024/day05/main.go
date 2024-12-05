package main

import (
	"fmt"
	"strconv"
	"strings"

	"github.com/adrisalas/advent-of-code/golang/2024/utils"
)

func main() {
	lines := utils.ReadFileToString("day05")

	fmt.Println(part1(lines))
	fmt.Println(part2(lines))
}

func part1(text string) int {
	parts := strings.Split(text, "\n\n")

	order := make(map[int][]int)

	for _, line := range strings.Split(parts[0], "\n") {
		parts := strings.Split(line, "|")
		x1, _ := strconv.Atoi(parts[0])
		x2, _ := strconv.Atoi(parts[1])
		order[x1] = append(order[x1], x2)
	}

	var print [][]int
	for _, line := range strings.Split(parts[1], "\n") {
		var nums []int
		for _, s := range strings.Split(line, ",") {
			n, _ := strconv.Atoi(s)
			nums = append(nums, n)
		}
		print = append(print, nums)
	}

	count := 0

	for _, p := range print {
		if isInRightOrder(p, order) {
			count += p[len(p)/2]
		}
	}

	return count
}

// brute force
func isInRightOrder(pages []int, order map[int][]int) bool {
	for i := len(pages) - 1; i >= 0; i-- {
		pagesAfter := order[pages[i]]
		for j := 0; j < i; j++ {
			p := pages[j]
			for _, b := range pagesAfter {
				if p == b {
					return false
				}
			}
		}
	}
	return true
}

func part2(text string) int {
	parts := strings.Split(text, "\n\n")

	order := make(map[int][]int)

	for _, line := range strings.Split(parts[0], "\n") {
		parts := strings.Split(line, "|")
		x1, _ := strconv.Atoi(parts[0])
		x2, _ := strconv.Atoi(parts[1])
		order[x1] = append(order[x1], x2)
	}

	var print [][]int
	for _, line := range strings.Split(parts[1], "\n") {
		var nums []int
		for _, s := range strings.Split(line, ",") {
			n, _ := strconv.Atoi(s)
			nums = append(nums, n)
		}
		print = append(print, nums)
	}

	count := 0

	for _, p := range print {
		if !isInRightOrder(p, order) {
			p := orderPages(p, order)
			count += p[len(p)/2]
		}
	}

	return count
}

// Notice original pages is modified
func orderPages(pages []int, order map[int][]int) []int {
	for !isInRightOrder(pages, order) {
		for i := len(pages) - 1; i >= 0; i-- {
			pagesAfter := order[pages[i]]
			for j := 0; j < i; j++ {
				p := pages[j]
				for _, b := range pagesAfter {
					if p == b {
						pages[i], pages[j] = pages[j], pages[i]
						break
					}
				}
			}
		}
	}

	return pages
}
