package connectfour

const val BLANK = ' '
val PLAYER_SYMBOLS = listOf('o', '*')

fun main() {
    println("Connect Four")

    // Getting the names
    val players = mutableListOf<String>()
    println("First player's name:")
    players.add(readLine()!!)
    println("Second player's name:")
    players.add(readLine()!!)

    val (rows, columns) = initializeGrid()

    val numberOfGames = selectNumberOfGames()

    println("${players[0]} VS ${players[1]}")
    println("$rows X $columns board")

    if (numberOfGames == 1) {
        println("Single game")
        playGame(initializeGrid(rows, columns), players)
    } else {
        println("Total $numberOfGames games")
        val score = mutableListOf(0, 0)
        for (i in 1..numberOfGames) {
            println("Game #$i")
            val winner = playGame(initializeGrid(rows, columns), players, (i - 1) % 2)
            if (winner == -1) {
                break
            } else if (winner in 0..1) {
                score[winner] += 2
            } else {
                score[0]++
                score[1]++
            }
            println("Score")
            println("${players[0]}: ${score[0]} ${players[1]}: ${score[1]}")
        }
    }
    println("Game over!")
}

fun playGame(grid: List<MutableList<Char>>, players: List<String>, initialPlayer: Int = 0): Int {
    printGrid(grid)

    val columns = grid[0].size

    var currentPlayer = initialPlayer - 1
    var winner = -1
    while (winner == -1) {
        currentPlayer = (currentPlayer + 1) % 2

        var column = 0
        while (column !in 1..columns) {
            println("${players[currentPlayer]}'s turn:")
            val turn = readLine()!!
            if (turn == "end") {
                println("Game over!")
                return -1
            }
            try {
                column = turn.toInt()
                if (column !in 1..columns) {
                    println("The column number is out of range (1 - $columns)")
                    continue
                } else if (columnFull(grid, column)) {
                    println("Column $column is full")
                    column = 0
                    continue
                } else {
                    val row = addToGrid(grid, PLAYER_SYMBOLS[currentPlayer], column)
                    printGrid(grid)
                    winner = getWinner(grid, row, column)
                }
            } catch (e: NumberFormatException) {
                println("Incorrect column number")
            }
        }
    }

    when (winner) {
        in 0 .. 1 -> println("Player ${players[winner]} won")
        2 -> println("It is a draw")
    }
    return winner
}

fun initializeGrid() : List<Int> {
    while (true) {
        // Get the board dimensions
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        val dimensionString = readLine()!!.lowercase().trim()
        if (dimensionString.isBlank()) {
            return listOf(6, 7)
        } else if (dimensionString.matches("\\d+\\s*x\\s*\\d+".toRegex())) {
            var (rows, columns) = dimensionString.split("\\s*x\\s*".toRegex()).map { it.toInt() }
            if (rows !in 5..9) {
                println("Board rows should be from 5 to 9")
                continue
            }
            if (columns !in 5..9) {
                println("Board columns should be from 5 to 9")
                continue
            }
            return listOf(rows, columns)
        } else {
            println("Invalid input")
        }
    }
}
fun initializeGrid(rows: Int, columns: Int) : List<MutableList<Char>> {
    return List(rows) {
        MutableList(columns) {
            BLANK
        }
    }
}

fun selectNumberOfGames(): Int {
    while(true) {
        println("Do you want to play single or multiple games?")
        println("For a single game, input 1 or press Enter")
        println("Input a number of games:")
        val response = readLine()!!.trim()
        if (response.isBlank() || response == "1")
            return 1
        val number = try { response.toInt() } catch (e: NumberFormatException) { 0 }
        if (number <= 0) {
            println("Invalid input")
            continue
        } else {
            return number
        }
    }
}

fun printGrid(grid : List<List<Char>>) {
    val rows = grid.size
    val columns = grid[0].size
    // Header
    for (j in 1..columns) {
        print(" $j")
    }
    println()

    // Rows
    for (row in grid) {
        print('║')
        for (c in row) {
            print("$c║")
        }
        println()
    }

    // Footer
    print('╚')
    for (j in 1 until columns) {
        print("═╩")
    }
    println("═╝")
}

fun getWinner(grid: List<List<Char>>, row: Int, column: Int): Int {
    // Cell last added is grid[row][column - 1]
    val symbolToCheck = grid[row][column - 1]

    // Vertical check -> only check up to 3 rows below
    if (grid.size - row >= 4) {
        var possibleWin = true
        for (r in 1 .. 3) {
            possibleWin = grid[row + r][column-1] == symbolToCheck
            if (! possibleWin)
                break
        }
        if (possibleWin)
            return PLAYER_SYMBOLS.indexOf(symbolToCheck)
    }

    // Horizontal check
    if (checkCells(grid, symbolToCheck, {c -> row}, column))
        return PLAYER_SYMBOLS.indexOf(symbolToCheck)

    // Diagonal check - \
    if (checkCells(grid, symbolToCheck, {c -> row + c}, column))
        return PLAYER_SYMBOLS.indexOf(symbolToCheck)

    // Diagonal check - /
    if (checkCells(grid, symbolToCheck, {c -> row - c}, column))
        return PLAYER_SYMBOLS.indexOf(symbolToCheck)

    for (c in 1 .. grid[0].size) {
        if (!columnFull(grid, c)) {
            return -1
        }
    }
    return 2
}

fun checkCells(grid: List<List<Char>>, char: Char, getRow: (Int) -> Int, column: Int): Boolean {
    var countAdjacent = 0
    for (c in 1 until column) {
        if (checkCell(grid, char, getRow(c), column-1-c)) {
            countAdjacent++
        } else {
            break
        }
    }
    for (c in column until grid[0].size) {
        if (checkCell(grid, char, getRow(c), c)) {
            countAdjacent++
        } else {
            break
        }
    }
    return countAdjacent >= 3
}

fun checkCell(grid: List<List<Char>>, char: Char, row: Int, c: Int): Boolean {
    return (row in grid.indices) && (grid[row][c] == char)
}

fun columnFull(grid: List<List<Char>>, column: Int): Boolean {
    return grid[0][column -1] != BLANK
}

fun addToGrid(grid: List<MutableList<Char>>, char: Char, column: Int): Int {
    for (row in grid.lastIndex downTo 0) {
        if (grid[row][column-1] == BLANK) {
            grid[row][column-1] = char
            return row
        }
    }
    return -1
}