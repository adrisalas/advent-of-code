package main

import (
	"fmt"
	"regexp"
	"strconv"
	"time"

	"github.com/adrisalas/advent-of-code/golang/2024/utils"
)

func main() {
	lines := utils.ReadFileToStringSlice("day14")

	start := time.Now()
	fmt.Printf("Part1: %d\n", part1(lines, 101, 103))
	utils.PrintTimeTook(start)
	fmt.Printf("Part2: %d\n", part2(lines, 101, 103))
	utils.PrintTimeTook(start)

	time.Sleep(1 * time.Second) // Part2 will print a tree :D
}

type Pos struct {
	X int
	Y int
}

type Robot struct {
	Position Pos
	Velocity Pos
}

func part1(lines []string, areaWidth int, areaHeight int) int {
	robots := getRobots(lines)

	robots = moveRobots(robots, areaWidth, areaHeight, 100)

	quadrants := robotsInQuadrants(robots, areaWidth, areaHeight)

	q1 := Pos{-1, -1}
	q2 := Pos{1, -1}
	q3 := Pos{-1, 1}
	q4 := Pos{1, 1}
	return quadrants[q1] * quadrants[q2] * quadrants[q3] * quadrants[q4]
}

func part2(lines []string, areaWidth int, areaHeight int) int {
	robots := getRobots(lines)

	time := 0
	seen := make(map[Pos]int)
	for len(seen) != len(robots) {
		seen = make(map[Pos]int)
		time++
		for _, r := range moveRobots(robots, areaWidth, areaHeight, time) {
			seen[r.Position]++
		}
	}

	go printRobots(robots, 101, 103, time) // Wait for this :D

	return time
}

func getRobots(lines []string) []Robot {
	var robots []Robot

	for _, line := range lines {
		re := regexp.MustCompile(`p=(\d+),(\d+) v=(-?\d+),(-?\d+)`)
		matches := re.FindStringSubmatch(line)
		robot := Robot{}
		robot.Position.X, _ = strconv.Atoi(matches[1])
		robot.Position.Y, _ = strconv.Atoi(matches[2])
		robot.Velocity.X, _ = strconv.Atoi(matches[3])
		robot.Velocity.Y, _ = strconv.Atoi(matches[4])
		robots = append(robots, robot)
	}

	return robots
}

func moveRobots(robots []Robot, areaWidth int, areaHeight int, time int) []Robot {
	var robotsMoved []Robot

	for _, robot := range robots {
		robot.Position.X = (time*robot.Velocity.X + robot.Position.X) % areaWidth
		if robot.Position.X < 0 {
			robot.Position.X = robot.Position.X + areaWidth
		}

		robot.Position.Y = (time*robot.Velocity.Y + robot.Position.Y) % areaHeight
		if robot.Position.Y < 0 {
			robot.Position.Y = robot.Position.Y + areaHeight
		}
		robotsMoved = append(robotsMoved, robot)
	}

	return robotsMoved
}

func robotsInQuadrants(robots []Robot, areaWidth int, areaHeight int) map[Pos]int {
	quadrants := make(map[Pos]int)

	for _, robot := range robots {
		q := Pos{
			signum(robot.Position.X - (areaWidth)/2),
			signum(robot.Position.Y - (areaHeight)/2),
		}
		quadrants[q]++
	}

	return quadrants
}

func signum(i int) int {
	if i < 0 {
		return -1
	} else if i > 0 {
		return 1
	}
	return 0
}

func printRobots(robots []Robot, areaWidth int, areaHeight int, time int) {
	var matrix [][]rune

	for row := 0; row < areaHeight; row++ {
		var line []rune
		for column := 0; column < areaWidth; column++ {
			line = append(line, ' ')
		}
		matrix = append(matrix, line)
	}

	for _, r := range moveRobots(robots, areaWidth, areaHeight, time) {
		matrix[r.Position.Y][r.Position.X] = '*'
	}

	for _, line := range matrix {
		println(string(line))
	}
}
