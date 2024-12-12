package main

import (
	"fmt"
	"time"

	"github.com/adrisalas/advent-of-code/golang/2024/utils"
)

func main() {
	lines := utils.ReadFileToStringSlice("day12")

	start := time.Now()
	fmt.Printf("Part1: %d\n", part1(lines))
	utils.PrintTimeTook(start)
	fmt.Printf("Part2: %d\n", part2(lines))
	utils.PrintTimeTook(start)
}

func part1(lines []string) int {
	var plantation [][]rune
	for _, line := range lines {
		plantation = append(plantation, []rune(line))
	}

	sum := 0
	for row, line := range plantation {
		for column, plant := range line {
			if plant == '#' {
				continue
			}
			area, perimeter := removeRegionGettingAreaAndPerimeter(plantation, row, column)
			sum += area * perimeter
		}
	}

	return sum
}

type Pos struct {
	row    int
	column int
}

const VISITED rune = '#'

func removeRegionGettingAreaAndPerimeter(plantation [][]rune, row int, column int) (int, int) {
	plant := plantation[row][column]
	var stack []Pos
	explored := make(map[Pos]bool)
	stack = append(stack, Pos{row, column})
	perimeter := 0
	area := 0

	for len(stack) > 0 {
		current := stack[0]
		stack = stack[1:]

		if _, ok := explored[current]; ok {
			continue
		}

		insideMatrix := current.row >= 0 && current.row < len(plantation) &&
			current.column >= 0 && current.column < len(plantation[0])

		if insideMatrix && plantation[current.row][current.column] == plant {
			explored[current] = true

			area++

			stack = append(stack, Pos{current.row - 1, current.column})
			stack = append(stack, Pos{current.row + 1, current.column})
			stack = append(stack, Pos{current.row, current.column - 1})
			stack = append(stack, Pos{current.row, current.column + 1})
		} else {
			perimeter++
		}

	}

	for pos := range explored {
		plantation[pos.row][pos.column] = VISITED
	}

	return area, perimeter
}

func part2(lines []string) int {
	var plantation [][]rune
	for _, line := range lines {
		plantation = append(plantation, []rune(line))
	}

	sum := 0
	for row, line := range plantation {
		for column, plant := range line {
			if plant == '#' {
				continue
			}
			area, sides := removeRegionGettingAreaAndSide(plantation, row, column)
			sum += area * sides
		}
	}

	return sum
}

func removeRegionGettingAreaAndSide(plantation [][]rune, row int, column int) (int, int) {
	plant := plantation[row][column]
	var stack []Pos
	explored := make(map[Pos]bool)
	stack = append(stack, Pos{row, column})
	sides := 0
	area := 0

	for len(stack) > 0 {
		current := stack[0]
		stack = stack[1:]

		if _, ok := explored[current]; ok {
			continue
		}

		insideMatrix := current.row >= 0 && current.row < len(plantation) &&
			current.column >= 0 && current.column < len(plantation[0])

		if insideMatrix && plantation[current.row][current.column] == plant {
			explored[current] = true

			area++

			stack = append(stack, Pos{current.row - 1, current.column})
			stack = append(stack, Pos{current.row + 1, current.column})
			stack = append(stack, Pos{current.row, current.column - 1})
			stack = append(stack, Pos{current.row, current.column + 1})

			corners := countCorners(plantation, plant, current.row, current.column)
			sides += corners
		}

	}

	for pos := range explored {
		plantation[pos.row][pos.column] = VISITED
	}

	return area, sides
}

func countCorners(plantation [][]rune, plant rune, row int, column int) int {
	corners := 0

	isTopInArea := row-1 >= 0 && plantation[row-1][column] == plant
	isDownInArea := row+1 < len(plantation) && plantation[row+1][column] == plant
	isLeftInArea := column-1 >= 0 && plantation[row][column-1] == plant
	isRightInArea := column+1 < len(plantation[0]) && plantation[row][column+1] == plant
	isTopRightInArea := row-1 >= 0 && column+1 < len(plantation[0]) && plantation[row-1][column+1] == plant
	isTopLeftInArea := row-1 >= 0 && column-1 >= 0 && plantation[row-1][column-1] == plant
	isDownRightInArea := row+1 < len(plantation) && column+1 < len(plantation[0]) && plantation[row+1][column+1] == plant
	isDownLeftInArea := row+1 < len(plantation) && column-1 >= 0 && plantation[row+1][column-1] == plant

	if !isTopInArea && !isRightInArea {
		corners++
	}
	if !isTopInArea && !isLeftInArea {
		corners++
	}
	if !isDownInArea && !isRightInArea {
		corners++
	}
	if !isDownInArea && !isLeftInArea {
		corners++
	}
	if isTopInArea && isRightInArea && !isTopRightInArea {
		corners++
	}
	if isTopInArea && isLeftInArea && !isTopLeftInArea {
		corners++
	}
	if isDownInArea && isRightInArea && !isDownRightInArea {
		corners++
	}
	if isDownInArea && isLeftInArea && !isDownLeftInArea {
		corners++
	}

	return corners
}
