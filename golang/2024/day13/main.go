package main

import (
	"fmt"
	"regexp"
	"strconv"
	"strings"
	"time"

	"github.com/adrisalas/advent-of-code/golang/2024/utils"
)

func main() {
	data := utils.ReadFileToString("day13")

	start := time.Now()
	fmt.Printf("Part1: %d\n", part1(data))
	utils.PrintTimeTook(start)
	fmt.Printf("Part2: %d\n", part2(data))
	utils.PrintTimeTook(start)
}

type Game struct {
	AX     int
	AY     int
	BX     int
	BY     int
	PrizeX int
	PrizeY int
}

func part1(data string) int {
	games := getGames(data)

	sum := 0
	for _, game := range games {
		sum += calculateTokens(game)
	}

	return sum
}

func getGames(data string) []Game {
	var games []Game

	for _, chunk := range strings.Split(data, "\n\n") {
		re := regexp.MustCompile(`Button A: X\+(\d+), Y\+(\d+)\nButton B: X\+(\d+), Y\+(\d+)\nPrize: X=(\d+), Y=(\d+)`)
		matches := re.FindStringSubmatch(chunk)
		game := Game{}
		game.AX, _ = strconv.Atoi(matches[1])
		game.AY, _ = strconv.Atoi(matches[2])
		game.BX, _ = strconv.Atoi(matches[3])
		game.BY, _ = strconv.Atoi(matches[4])
		game.PrizeX, _ = strconv.Atoi(matches[5])
		game.PrizeY, _ = strconv.Atoi(matches[6])
		games = append(games, game)
	}

	return games
}

func calculateTokens(game Game) int {
	// Ax + By = C
	// Dx + Ey = F
	// https://en.wikipedia.org/wiki/Cramer%27s_rule
	pressA := (game.BY*game.PrizeX - game.BX*game.PrizeY) / (game.AX*game.BY - game.AY*game.BX)
	pressB := (game.AY*game.PrizeX - game.AX*game.PrizeY) / (game.AY*game.BX - game.AX*game.BY)
	if (pressA*game.AX+pressB*game.BX) == game.PrizeX && (pressA*game.AY+pressB*game.BY) == game.PrizeY {
		return pressA*3 + pressB
	}
	return 0
}

func part2(data string) int {
	games := getGames(data)

	sum := 0
	for _, game := range games {
		game.PrizeX = game.PrizeX + 10000000000000
		game.PrizeY = game.PrizeY + 10000000000000
		sum += calculateTokens(game)
	}

	return sum
}
