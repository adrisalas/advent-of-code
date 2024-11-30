package main

import (
	"fmt"
	"regexp"
	"strconv"

	"github.com/adrisalas/advent-of-code/golang/2023/utils"
)

func main() {
	lines := utils.ReadFileToStringSlice("day01")

	fmt.Println(part1(lines))
	fmt.Println(part2(lines))
}

func part1(lines []string) int {
	re, _ := regexp.Compile("[0-9]")

	sum := 0
	for _, line := range lines {
		results := re.FindAllString(line, -1)

		firstDigit := results[0]
		lastDigit := results[len(results)-1]

		num, _ := strconv.Atoi(firstDigit + lastDigit)

		sum += num
	}

	return sum
}

func part2(lines []string) int {
	re, _ := regexp.Compile("[0-9]|one|two|three|four|five|six|seven|eight|nine")

	sum := 0
	for _, line := range lines {
		results := re.FindAllString(line, -1)

		firstDigit := results[0]
		var lastDigit string
		pos := 0
		for {
			got := re.FindString(line[pos:])
			if got == "" {
				break
			}
			lastDigit = got
			pos++
		}

		num := fromStringToDigit(firstDigit)*10 + fromStringToDigit(lastDigit)

		// Add to the sum
		sum += num
	}

	return sum
}

func fromStringToDigit(s string) int {
	switch s {
	case "one", "1":
		{
			return 1
		}
	case "two", "2":
		{
			return 2
		}
	case "three", "3":
		{
			return 3
		}
	case "four", "4":
		{
			return 4
		}
	case "five", "5":
		{
			return 5
		}
	case "six", "6":
		{
			return 6
		}
	case "seven", "7":
		{
			return 7
		}
	case "eight", "8":
		{
			return 8
		}
	case "nine", "9":
		{
			return 9
		}
	default:
		return 0
	}
}
