package main

import (
	"fmt"
	"strconv"
	"time"

	"github.com/adrisalas/advent-of-code/golang/2024/utils"
)

func main() {
	lines := utils.ReadFileToIntMatrix("day11")

	start := time.Now()
	fmt.Printf("Part1: %d\n", part1(lines[0]))
	utils.PrintTimeTook(start)
	fmt.Printf("Part2: %d\n", part2(lines[0]))
	utils.PrintTimeTook(start)
}

func part1(line []int) int {
	rocksMap := blink(line, 25)

	numRocks := 0

	for _, v := range rocksMap {
		numRocks += v
	}
	return numRocks
}

func part2(line []int) int {
	rocksMap := blink(line, 75)

	numRocks := 0

	for _, v := range rocksMap {
		numRocks += v
	}
	return numRocks
}

func blink(line []int, times int) map[int]int {
	rocks := make(map[int]int)

	for _, r := range line {
		rocks[r] = rocks[r] + 1
	}

	for i := 0; i < times; i++ {
		aux := make(map[int]int)

		for k, v := range rocks {
			str := strconv.Itoa(k)
			digits := len(str)

			if k == 0 {
				aux[1] = aux[1] + v
			} else if digits%2 == 0 {
				middle := digits / 2

				part1 := str[:middle]
				part2 := str[middle:]

				n1, _ := strconv.Atoi(part1)
				aux[n1] = aux[n1] + v
				n2, _ := strconv.Atoi(part2)
				aux[n2] = aux[n2] + v
				continue
			} else {
				aux[2024*k] = aux[2024*k] + v
			}
		}
		rocks = aux
	}
	return rocks
}
