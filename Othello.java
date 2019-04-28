class Othello {
    private int pieces[][] = {
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,2,1,0,0,0},
        {0,0,0,1,2,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0}
    };
    private int puttable[][] = {
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0}
    };

    int row = 8;
    int column = 8;

    void initPuttable(int myColor) {
        if (myColor==1) {
            puttable[2][3] = 1;
            puttable[3][2] = 1;
            puttable[4][5] = 1;
            puttable[5][4] = 1;
        } else if (myColor==2) {
            puttable[2][4] = 1;
            puttable[4][2] = 1;
            puttable[3][5] = 1;
            puttable[5][3] = 1;
        }
    }


    boolean piecesEqual(int i, int j, int equal) {
        return pieces[i][j] == equal;
    }


    boolean puttableEqual(int i, int j, int equal) {
        return puttable[i][j] == equal;
    }


    void getNextBorder(int[] move, int myColor) {

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
