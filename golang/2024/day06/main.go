package main

import (
	"fmt"
	"slices"

	"github.com/adrisalas/advent-of-code/golang/2024/utils"
)

func main() {
	lines := utils.ReadFileToStringSlice("day06")

	fmt.Println(part1(lines))
	fmt.Println(part2(lines))
}

type Pos struct {
	row int
	col int
}

type Direction int

const (
	UP Direction = iota
	RIGHT
	DOWN
	LEFT
)

func part1(lines []string) int {
	startPos := getStartingPos(lines)

	visited := walkNodes(lines, startPos)

	return len(visited)
}

func getStartingPos(lines []string) Pos {
	startPos := Pos{-1, -1}

	for row, line := range lines {
		for col, c := range line {
			if c == '^' {
				startPos = Pos{row, col}
				break
			}
		}
		if startPos.col != -1 {
			break
		}
	}

	return startPos
}

func walkNodes(lines []string, startPos Pos) []Pos {
	var visited []Pos
	visited = append(visited, startPos)
	current := startPos
	direction := UP

	for current.row != 0 && current.row != len(lines)-1 && current.col != 0 && current.col != len(lines[0])-1 {
		switch direction {
		case UP:
			if lines[current.row-1][current.col] == '#' {
				direction = RIGHT
			} else {
				current = Pos{current.row - 1, current.col}
			}
		case RIGHT:
			if lines[current.row][current.col+1] == '#' {
				direction = DOWN
			} else {
				current = Pos{current.row, current.col + 1}
			}
		case DOWN:
			if lines[current.row+1][current.col] == '#' {
				direction = LEFT
			} else {
				current = Pos{current.row + 1, current.col}
			}
		case LEFT:
			if lines[current.row][current.col-1] == '#' {
				direction = UP
			} else {
				current = Pos{current.row, current.col - 1}
			}
		}
		if !slices.Contains(visited, current) {
			visited = append(visited, current)
		}
	}
	return visited
}

func part2(lines []string) int {
	var runeSlice [][]rune
	for _, line := range lines {
		runeSlice = append(runeSlice, []rune(line))
	}

	startPos := getStartingPos(lines)
	possiblePositions := 0
	path := walkNodes(lines, startPos)

	for _, obstruction := range path {
		if obstruction == startPos {
			continue
		}

		runeSlice[obstruction.row][obstruction.col] = '#'

		if isThereALoop(runeSlice, startPos) {
			possiblePositions++
		}
		runeSlice[obstruction.row][obstruction.col] = '.'
	}

	return possiblePositions
}

type PosWithDirection struct {
	pos      Pos
	diretion Direction
}

func isThereALoop(lines [][]rune, startPos Pos) bool {
	visited := make(map[PosWithDirection]bool)
	current := PosWithDirection{startPos, UP}

	for current.pos.row != 0 &&
		current.pos.row != len(lines)-1 &&
		current.pos.col != 0 &&
		current.pos.col != len(lines[0])-1 {

		row := current.pos.row
		col := current.pos.col
		switch current.diretion {
		case UP:
			if lines[row-1][col] == '#' {
				current = PosWithDirection{Pos{row, col}, RIGHT}
			} else {
				current = PosWithDirection{Pos{row - 1, col}, UP}
			}
		case RIGHT:
			if lines[row][col+1] == '#' {
				current = PosWithDirection{Pos{row, col}, DOWN}
			} else {
				current = PosWithDirection{Pos{row, col + 1}, RIGHT}
			}
		case DOWN:
			if lines[row+1][col] == '#' {
				current = PosWithDirection{Pos{row, col}, LEFT}
			} else {
				current = PosWithDirection{Pos{row + 1, col}, DOWN}
			}
		case LEFT:
			if lines[row][col-1] == '#' {
				current = PosWithDirection{Pos{row, col}, UP}
			} else {
				current = PosWithDirection{Pos{row, col - 1}, LEFT}
			}
		}
		if visited[current] {
			return true
		}
		visited[current] = true
	}
	return false
}
