package main

import (
	"fmt"

	"github.com/adrisalas/advent-of-code/golang/2024/utils"
)

func main() {
	lines := utils.ReadFileToStringSlice("day04")

	fmt.Println(part1(lines))
	fmt.Println(part2(lines))
}

func part1(lines []string) int {
	count := 0

	for row, line := range lines {
		for col, r := range line {
			if r == 'X' {
				if isXmasRight(lines, row, col) {
					count++
				}
				if isXmasDownRight(lines, row, col) {
					count++
				}
				if isXmasDown(lines, row, col) {
					count++
				}
				if isXmasDownLeft(lines, row, col) {
					count++
				}
				if isXmasLeft(lines, row, col) {
					count++
				}
				if isXmasUpLeft(lines, row, col) {
					count++
				}
				if isXmasUp(lines, row, col) {
					count++
				}
				if isXmasUpRight(lines, row, col) {
					count++
				}
			}
		}
	}

	return count
}

func isXmasRight(lines []string, row int, col int) bool {
	word := "XMAS"

	for j := col; j < len(lines[0]) && len(word) > 0; j++ {
		if lines[row][j] != word[0] {
			break
		}
		word = word[1:]
	}

	return len(word) == 0
}

func isXmasLeft(lines []string, row int, col int) bool {
	word := "XMAS"

	for j := col; j >= 0 && len(word) > 0; j-- {
		if lines[row][j] != word[0] {
			break
		}
		word = word[1:]
	}

	return len(word) == 0
}

func isXmasUp(lines []string, row int, col int) bool {
	word := "XMAS"

	for i := row; i >= 0 && len(word) > 0; i-- {
		if lines[i][col] != word[0] {
			break
		}
		word = word[1:]
	}

	return len(word) == 0
}

func isXmasDown(lines []string, row int, col int) bool {
	word := "XMAS"

	for i := row; i < len(lines) && len(word) > 0; i++ {
		if lines[i][col] != word[0] {
			break
		}
		word = word[1:]
	}

	return len(word) == 0
}

func isXmasDownRight(lines []string, row int, col int) bool {
	word := "XMAS"
	i := row
	j := col
	for i < len(lines) && j < len(lines[0]) && len(word) > 0 {
		if lines[i][j] != word[0] {
			break
		}
		word = word[1:]
		i++
		j++
	}

	return len(word) == 0
}

func isXmasDownLeft(lines []string, row int, col int) bool {
	word := "XMAS"
	i := row
	j := col
	for i < len(lines) && j >= 0 && len(word) > 0 {
		if lines[i][j] != word[0] {
			break
		}
		word = word[1:]
		i++
		j--
	}

	return len(word) == 0
}

func isXmasUpRight(lines []string, row int, col int) bool {
	word := "XMAS"
	i := row
	j := col
	for i >= 0 && j < len(lines[0]) && len(word) > 0 {
		if lines[i][j] != word[0] {
			break
		}
		word = word[1:]
		i--
		j++
	}

	return len(word) == 0
}

func isXmasUpLeft(lines []string, row int, col int) bool {
	word := "XMAS"
	i := row
	j := col
	for i >= 0 && j >= 0 && len(word) > 0 {
		if lines[i][j] != word[0] {
			break
		}
		word = word[1:]
		i--
		j--
	}

	return len(word) == 0
}

func part2(lines []string) int {
	count := 0

	for row, line := range lines {
		for col, _ := range line {
			if isThereMasInXShape(lines, row, col) {
				count++
			}
		}
	}

	return count
}

func isThereMasInXShape(lines []string, row int, col int) bool {
	if row == 0 || col == 0 || row == len(lines)-1 || col == len(lines[0])-1 {
		return false
	}

	if lines[row][col] != 'A' {
		return false
	}

	ul := lines[row-1][col-1]
	ur := lines[row-1][col+1]
	dl := lines[row+1][col-1]
	dr := lines[row+1][col+1]

	if ul == 'M' &&
		ur == 'M' &&
		dl == 'S' &&
		dr == 'S' {
		return true
	}
	if ul == 'M' &&
		ur == 'S' &&
		dl == 'M' &&
		dr == 'S' {
		return true
	}
	if ul == 'S' &&
		ur == 'S' &&
		dl == 'M' &&
		dr == 'M' {
		return true
	}
	if ul == 'S' &&
		ur == 'M' &&
		dl == 'S' &&
		dr == 'M' {
		return true
	}

	return false

}
