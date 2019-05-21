public class Othello {
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

    private int[] totalPieces = {0, 0};

    Player player = null;


    Othello(Player player) {
        this.player = player;
    }


    // initialize puttable[][]
    public void initPuttable() {
        if (player.getMyColor()==1) {
            puttable[2][3] = 1;
            puttable[3][2] = 1;
            puttable[4][5] = 1;
            puttable[5][4] = 1;
        }
    }


    public boolean pieceEquals(int i, int j, int equal) {
        return pieces[i][j] == equal;
    }


    public boolean puttableEquals(int i, int j) {
        return puttable[i][j] == 1;
    }


	public void getNextBorder(int[] move,int color){

        // upadte pieces[][]
        getNextPieces(move, color);
        getNextTotalPieces();
        // if my turn, update puttable[][]
        if(color != player.getMyColor()) {
            getNextPuttable(player.getMyColor());
        } else
            fillPuttableZero();
    }


    private void getNextPieces(int[] move, int myColor) {
       	    /*盤面の更新*/
		for(int i=0;i<ROW;i++) {
			int x=move[0];
			int y=move[1];
			int count=0;//0:開始，1:カウント，2:カウント終了，3:反転不可能
			int c=0;
            pieces[move[0]][move[1]]=myColor;
            while(count<2) {
                x=x+dir[i][0];
                y=y+dir[i][1];
                if(x>=0&&x<ROW&&y>=0&&y<COLUMN) {
                    if(pieces[x][y]==0) {
                        count=3;
                    }
                    else if(pieces[x][y]==myColor) {
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
                        pieces[s][t]=myColor;
                }
            }
		}
    }

    private void getNextTotalPieces() {
        totalPieces[0] = 0;
        totalPieces[1] = 0;

        for(int i = 0; i < ROW; ++i) {
            for(int j = 0; j < COLUMN; ++j) {
                if(pieces[i][j] == 1)
                    totalPieces[0] += 1;
                if(pieces[i][j] == 2)
                    totalPieces[1] += 1;
            }
        }
    }

    private void getNextPuttable(int myColor) {
    	/*駒を置けるか否かの情報の更新*/
		for(int i=0;i<ROW;i++) {
			for(int j=0;j<COLUMN;j++) {
				puttable[i][j]=0;
				if(pieces[i][j]==0) {
					for(int m=0;m<ROW;m++) {
						int count=0;
                        int x = i;
                        int y = j;
						while(count<2) {
								x+=dir[m][0];
								y+=dir[m][1];
								if(x>=0&&x<ROW&&y>=0&&y<COLUMN) {
									if(pieces[x][y]==0) {
										count=3;
									}
									else if(pieces[x][y]==myColor) {
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
							break;
						}
					}
				}
			}
		}
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
