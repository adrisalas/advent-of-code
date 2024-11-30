package utils

import (
	"os"
	"strings"
)

func ReadFileToString(filename string) string {
	content, err := os.ReadFile("./2023/inputs/" + filename + ".txt")

	if err != nil {
		panic(err)
	}

	return string(content)
}

func ReadFileToStringSlice(filename string) []string {
	content := ReadFileToString(filename)

	return strings.Split(content, "\n")
}
