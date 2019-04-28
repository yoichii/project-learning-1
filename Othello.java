class Othello {
    void getNextBorder(int[][] pieces, int[][] puttable, int[] move, int myColor) {

        // update pieces
        pieces[move[0]][move[1]] = myColor;
        puttable[move[0]][move[1]] = 0;

        // around the piece
        for(int i = move[0]-1; i <= move[0]+1; ++i) {
            for(int j = move[1]-1; j <= move[1]+1; ++j) {

                // if out of boundary, continue
                if(i < 0 || 7 < i || j < 0 || 7 < j)
                    continue;

                // if no piece, enabled
                if(pieces[i][j] == 0)
                    puttable[i][j] = 1;

            }
        }
    }
}
