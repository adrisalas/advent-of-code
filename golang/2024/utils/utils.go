package utils

import (
	"os"
	"strconv"
	"strings"
)

func ReadFileToString(filename string) string {
	content, err := os.ReadFile("./2024/inputs/" + filename + ".txt")

	if err != nil {
		panic(err)
	}

	return string(content)
}

func ReadFileToStringSlice(filename string) []string {
	content := ReadFileToString(filename)

	return strings.Split(content, "\n")
}

func ReadFileToIntMatrix(filename string) [][]int {
	lines := ReadFileToStringSlice(filename)

	var matrix [][]int

	for _, line := range lines {
		words := strings.Fields(line)
		var row []int
		for _, word := range words {
			number, _ := strconv.Atoi(word)
			row = append(row, number)
		}
		matrix = append(matrix, row)
	}
	return matrix
}

func Abs(x int) int {
	if x < 0 {
		return -x
	}
	return x
}
