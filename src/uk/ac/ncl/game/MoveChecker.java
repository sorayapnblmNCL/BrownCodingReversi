/**
 * @author Kostiantyn Potomkin
 * @version 0.9
 * @since 05-03-2020
 */
package uk.ac.ncl.game;

import uk.ac.ncl.entity.CellStatus;
import uk.ac.ncl.entity.DirectedMove;
import uk.ac.ncl.entity.Cell;

import java.util.ArrayList;

import static uk.ac.ncl.Constants.*;

/**
 *
 * Main logic of the game.
 *
 */
public class MoveChecker {

    private Cell[][] cells;

    public MoveChecker(Cell[][] cells) {
        this.cells = cells;
    }

    /**
     * // error fixed
     * Generates a move of the opponent. Returns a cell with the biggest score move.
     *
     * @param cellStatus - colour of the opponent
     * @return a cell to move with the biggest score
     */
    // 2 ERRORS FIXED
    public Cell generateOpponent(CellStatus cellStatus) {
        ArrayList<Cell> potentialMoves = findPotentialMoves(cellStatus);
        Cell opponentsMove = null;
        for (Cell cell : potentialMoves){
            // error fixed
                opponentsMove = ((opponentsMove == null)
                        || (cell.getMove().getScore() > opponentsMove.getMove().getScore()))
                        ? cell
                        : opponentsMove;
        }
        return opponentsMove;
    }

    /**
     * // error fixed
     * Flips pieces between selected piece in directions of valid moves.
     *
     * @param cell - cell we have just put on the board
     * @param colour - colour of the current player
     */
    // 2 ERRORS FIXED
    public void flipPieces(Cell cell, CellStatus colour) {
        // error fixed
        cell.colourTemp(colour == OPPONENTS_CELL_STATUS ? OPPONENTS_COLOUR : PLAYERS_COLOUR, false);
        for (DirectedMove move : cell.getMove().getMoves()) {
            int[] dir = move.getDirection();
            int d_row = cell.getRow();
            int d_col = cell.getColumn();

            while (d_col != move.getCell().getColumn() || d_row != move.getCell().getRow()) {
                this.cells[d_row][d_col].setValue(colour);
                d_row += dir[0];
                d_col += dir[1];
            }
        }
    }

    /**
     * // error fixed
     * Returns potential moves on the board for the specified colour
     *
     * @param colour - colour of the current player
     * @return a list of pieces for which there exist valid moves
     */
    // 2 ERRORS FIXED
    public ArrayList<Cell> findPotentialMoves(CellStatus colour) {
        ArrayList<Cell> potentialMoves = new ArrayList<Cell>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (Cell cell : this.cells[i]) {
                if (cell.getValue() == CellStatus.EMPTY){
                    if (cell.isLegal(colour, cells)){
                        // error fixed
                        potentialMoves.add(cell);
                    }
                }
            }
        }
        return potentialMoves;
    }

    /**
     * // error fixed
     * Updates the status of the cells in the "cells" list
     *
     * @param cells - list of cells to update
     * @param colour - new colour (status)
     */
    // 1 ERROR FIXED
    public void colourPieces(ArrayList<Cell> cells, CellStatus colour) {
        // error fixed
        for (int i = 0; i < cells.size(); i++){
            cells.get(i).setValue(colour);
        }
    }

    /**
     * Sums up results of the game
     *
     * @return string with the results of the game
     */
    // 3 ERRORS FIXED
    public String getFinalScore(){
        int lights = 0;
        int darks = 0;

        for (int row = 0; row < BOARD_SIZE; row++) {
            // error fixed
            for (Cell cell : this.cells[row]) {
                if (cell.getValue() == CellStatus.DARK){
                    darks++;
                } else if (cell.getValue() == CellStatus.LIGHT){
                    lights++;
                }
            }
        }

        if (darks == lights)
            return "The game is over. It is a draw. Each player has " + darks + " pieces";
        // error fixed
        String winner = darks >  lights ? "Dark" : "Light";
        // error fixed
        return "The game is over."
                + winner + " has won with the result: Dark - " + darks + " Light - " + lights;
    }

    /**
     * Cleans up potential moves from the board cells
     *
     * @param grayCells - pieces with valid moves
     */
    // 2 ERRORS FIXED
    public void removeMoves(ArrayList<Cell> grayCells){
        // error fixed
        if (grayCells != null) {
            for (Cell cell : grayCells) {
                // error fixed
                cell.setMove(null);
            }
        }
    }
}
