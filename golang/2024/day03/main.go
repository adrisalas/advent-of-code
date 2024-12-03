package main

import (
	"fmt"
	"regexp"
	"strconv"
	"strings"

	"github.com/adrisalas/advent-of-code/golang/2024/utils"
)

func main() {
	lines := utils.ReadFileToString("day03")

	fmt.Println(part1(lines))
	fmt.Println(part2(lines))
}

func part1(text string) int {
	return sumAllMulInstructions(text)
}

func sumAllMulInstructions(text string) int {
	re, _ := regexp.Compile(`mul\(\d{1,3},\d{1,3}\)`)

	sum := 0
	for _, match := range re.FindAllString(text, -1) {
		aux := strings.Split(strings.Split(match, "mul(")[1], ",")
		x1, _ := strconv.Atoi(aux[0])
		x2, _ := strconv.Atoi(strings.Split(aux[1], ")")[0])

		sum += x1 * x2
	}
	return sum
}

func part2(text string) int {
	aux := strings.Split(text, "don't()")

	instructions := aux[0]

	for i := 1; i < len(aux); i++ {
		aux2 := strings.Split(aux[i], "do()")
		for j := 1; j < len(aux2); j++ {
			instructions += "_"
			instructions += aux2[j]
		}
	}

	return sumAllMulInstructions(instructions)
}