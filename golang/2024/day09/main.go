package main

import (
	"fmt"
	"strconv"
	"time"

	"github.com/adrisalas/advent-of-code/golang/2024/utils"
)

func main() {
	lines := utils.ReadFileToString("day09")

	start := time.Now()
	fmt.Printf("Part1: %d\n", part1(lines))
	utils.PrintTimeTook(start)
	fmt.Printf("Part2: %d\n", part2(lines))
	utils.PrintTimeTook(start)
}

func part1(lines string) int {
	disk, _ := getInitialDisk(lines)

	fragmentDisk(disk)

	return checksum(disk)
}

func part2(lines string) int {
	disk, fileBlocks := getInitialDisk(lines)

	compactFiles(fileBlocks, disk)

	return checksum(disk)
}

type Block struct {
	id    int
	start int
	end   int
}

func getInitialDisk(lines string) ([]int, []Block) {
	var disk []int
	var fileBlocks []Block
	isFreeSpace := false

	for pos, r := range lines {
		n, _ := strconv.Atoi(string(r))
		if isFreeSpace {
			for range n {
				disk = append(disk, -1)
			}
		} else {
			file := Block{
				id:    pos / 2,
				start: len(disk),
				end:   len(disk) + n,
			}
			fileBlocks = append(fileBlocks, file)
			for range n {
				disk = append(disk, file.id)
			}
		}
		isFreeSpace = !isFreeSpace
	}
	return disk, fileBlocks
}

func fragmentDisk(disk []int) {
	size := len(disk)
	for i := 0; i < size; i++ {
		for disk[size-1] == -1 {
			size--
		}
		if disk[i] == -1 {
			disk[i], disk[size-1] = disk[size-1], -1
			size--
		}
	}
}

func checksum(disk []int) int {
	checksum := 0
	for i := 0; i < len(disk); i++ {
		if disk[i] != -1 {
			checksum += i * disk[i]
		}
	}
	return checksum
}

func compactFiles(fileBlocks []Block, disk []int) {
	for i := len(fileBlocks) - 1; i >= 0; i-- {
		file := &fileBlocks[i]
		fileSize := file.end - file.start

		for j := 0; j < file.start; j++ {
			enoughSpaceInPosJ := true
			for k := j; k < j+fileSize; k++ {
				if disk[k] != -1 {
					enoughSpaceInPosJ = false
					break
				}
			}
			if enoughSpaceInPosJ {
				for k := 0; k < fileSize; k++ {
					disk[j+k], disk[file.start+k] = file.id, -1
				}
				break
			}
		}
	}
}
