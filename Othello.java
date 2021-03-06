import projectlearning1.*;
public class Othello {
	// 0: no piece, 1: black, 2: white
    private int pieces[][][] = {
	    {
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,2,1,0,0,0},
            {0,0,0,1,2,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0}
        },
        {
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0}
        }
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
    private int index = 0;
    public boolean isMyTurn = false;

    //move[0]:置いた駒のｘ座標、move[1]:置いた駒のy座標、myColor:置いた駒の色
    //駒の周囲８方向の座標
    private final int[][] dir={{-1,-1},// 左下
            { 0,-1},// 下
            { 1,-1},// 右下
            { 1, 0},//右
            { 1, 1},//右上
            { 0, 1},//上
            {-1, 1},//左上
            {-1, 0}};//左

     // 8*8 board
    public final int ROW = 8;
    public final int COLUMN = 8;

    private int[] totalPieces = {2, 2};

    Player player = null;


    Othello(Player player) {
        this.player = player;
    }


    // initialize puttable[][]
    public void initPuttable() {
        if (player.getMyColor()==1) {
            isMyTurn = true;
            puttable[2][3] = 1;
            puttable[3][2] = 1;
            puttable[4][5] = 1;
            puttable[5][4] = 1;
        }
    }

    public int getStatus(int i, int j, int myColor) {
        if(puttable[i][j] == 1 && myColor == 1)
            return 1;
        else if(puttable[i][j] == 1 && myColor == 2)
            return 2;
        else if(pieces[index][i][j] == 0)
            return 0;
        else if(pieces[1-index][i][j] == 0 && pieces[index][i][j] == 1)
            return 3;
        else if(pieces[1-index][i][j] == 0 && pieces[index][i][j] == 2)
            return 4;
        else if(pieces[1-index][i][j] == 2 && pieces[index][i][j] == 1)
            return 5;
        else if(pieces[1-index][i][j] == 1 && pieces[index][i][j] == 2)
            return 6;
        else if(pieces[1-index][i][j] == 1 && pieces[index][i][j] == 1)
            return 7;
        else if(pieces[1-index][i][j] == 2 && pieces[index][i][j] == 2)
            return 8;
        return -1;
    }



    public boolean pieceEquals(int i, int j, int equal) {
        return pieces[index][i][j] == equal;
    }


    public boolean puttableEquals(int i, int j) {
        return puttable[i][j] == 1;
    }


	public boolean getNextBorder(int[] move,int color){

        index = 1 - index;
        // upadte pieces[][], if not pass
        if(move[0] != -1) {
            getNextPieces(move, color);
            getNextTotalPieces();
        } else {
            for(int i = 0; i < ROW; ++i) {
                for(int j = 0; j < COLUMN; ++j) {
                    pieces[index][i][j] = pieces[1-index][i][j];
                }
            }
        }
        
        // if my turn, update puttable[][]
        if(color != player.getMyColor()) {
            isMyTurn = true;
            return getNextPuttable(player.getMyColor());
        } else {
            isMyTurn = false;
            fillPuttableZero();
            return false;
        }

    }


    private void getNextPieces(int[] move, int myColor) {
       	    /*盤面の更新*/
        for(int i = 0; i < ROW; ++i) {
            for(int j = 0; j < COLUMN; ++j) {
                pieces[index][i][j] = pieces[1-index][i][j];
            }
        }

		for(int i=0;i<ROW;i++) {
			int x=move[0];
			int y=move[1];
			int count=0;//0:開始，1:カウント，2:カウント終了，3:反転不可能
			int c=0;
            pieces[index][move[0]][move[1]]=myColor;
            while(count<2) {
                x=x+dir[i][0];
                y=y+dir[i][1];
                if(x>=0&&x<ROW&&y>=0&&y<COLUMN) {
                    if(pieces[index][x][y]==0) {
                        count=3;
                    }
                    else if(pieces[index][x][y]==myColor) {
                        if(count==1) {
                            count=2;
                        }
                        else {
                            count=3;
                        }
                    }
                    else {
                        count=1;
                        c=c+1;
                    }
                } else {
                    count=3;
                }
            }
            if(count==2) {
                int s=move[0];
                int t=move[1];
                for(int n=0;n<c;n++) {
                        s=s+dir[i][0];
                        t=t+dir[i][1];
                        pieces[index][s][t]=myColor;
                }
            }
		}
    }

    private void getNextTotalPieces() {
        totalPieces[0] = 0;
        totalPieces[1] = 0;

        for(int i = 0; i < ROW; ++i) {
            for(int j = 0; j < COLUMN; ++j) {
                if(pieces[index][i][j] == 1)
                    totalPieces[0] += 1;
                if(pieces[index][i][j] == 2)
                    totalPieces[1] += 1;
            }
        }
    }

    private boolean getNextPuttable(int myColor) {
        boolean pass = true;

    	/*駒を置けるか否かの情報の更新*/
		for(int i=0;i<ROW;i++) {
			for(int j=0;j<COLUMN;j++) {
				puttable[i][j]=0;
				if(pieces[index][i][j]==0) {
					for(int m=0;m<ROW;m++) {
						int count=0;
                        int x = i;
                        int y = j;
						while(count<2) {
								x+=dir[m][0];
								y+=dir[m][1];
								if(x>=0&&x<ROW&&y>=0&&y<COLUMN) {
									if(pieces[index][x][y]==0) {
										count=3;
									}
									else if(pieces[index][x][y]==myColor) {
										if(count==1) {
											count=2;
										}
										else {
											count=3;
										}
									}
									else {
										count=1;
									}
								}
								else {
									count=3;
								}
						}
						if(count==2) {
							puttable[i][j]=1;
                            pass = false;
							break;
						}
					}
				}
			}
		}

        return pass;
    }


    private void fillPuttableZero() {
        for (int i = 0; i < ROW; ++i) {
            for (int j = 0; j < COLUMN; ++j) {
                puttable[i][j] = 0;
            }
        }
    }

    public int[] getTotalPieces() {
        return totalPieces;
    }

}
