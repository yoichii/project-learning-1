class Othello {
    // 0: no piece, 1: black, 2: white
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
    // 0: not puttable, 1: puttable
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

    // 8*8 board
    int row = 8;
    int column = 8;

    // initialize puttable[][]
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


    boolean pieceEquals(int i, int j, int equal) {
        return pieces[i][j] == equal;
    }


    boolean puttableEquals(int i, int j, int equal) {
        return puttable[i][j] == equal;
    }


    // (前話した関数の仕様と若干変わってます)
    //
    // moveとmyColorを元に、次の盤面の様子を計算する関数
    //
    // ---- 引数 ----
    // * move: 置かれた手の場所を表すint型二次元配列（5列目3行目だったら{5,3})
    // * myColor: 置かれた手の色を表すint (1: 黒, 2: 白)
    // --------------
    //
    // ---- してほしいこと ----
    // piecesとputtable(=盤面の様子)を更新してほしい
    // * pieces: どこに何色の駒が置かれているかを表すint型二次元配列 (0: 駒なし, 1: 黒, 2: 白)
    // * puttqble: そこに駒が置けるかどうか(0: 置けない, 1: 置ける)
    //  -----------------------
    //
    //  ---- 現在サンプルで書いてあるコード ----
    //  * moveの位置にpiecesを追加する(getNextPieces関数)
    //  * moveの周りで駒が置かれていない場所を追加でputtableにする(getNextPuttalbe関数)
    //  ----------------------------------------
    //
    //  結構作るの複雑で大変だと思うけど、このサンプルコード見つつなんとか、、笑
    //  まずはgetNextPieces()だけでもできればいいんじゃないかと、、、
    //  分けた方が読みやすくなるかなと思って、getNextBorder()を２つの関数に分割してるけど、まとめて処理した方がやりやすいならそちらでも問題なしです、、
    //  よくわかんなくなったら相談乗ります、、
    //
    void getNextBorder(int[] move, int myColor) {
        getNextPieces();
        getNextPuttable();
    }


    void getNextPieces() {
         // update pieces
        pieces[move[0]][move[1]] = myColor;
        puttable[move[0]][move[1]] = 0;
    }


    void getNextPuttable() {
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
