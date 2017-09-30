package ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public final class PVPBoardPanel extends PBoardPanel implements MouseListener {

    public interface OnDrawPieceListener{
        void point(int x,int y);
    }
    private OnDrawPieceListener onDrawPieceListener;
    public void setOnDrawPieceListener(OnDrawPieceListener l){
        onDrawPieceListener = l;
    }

    public PVPBoardPanel() {
        super();
    }
    /**
     * 重绘一个棋子
     *
     * @param tempX x坐标
     * @param tempY y坐标
     */
    protected void drawRealPiece(int tempX, int tempY) {
        Point p = px2index(tempX, tempY);
        int x = p.x, y = p.y;
        if (panel[y][x] != 0)
            return;
        if (isBlack == true) {
            panel[y][x] = 1;
            int xxx;
            drawBlackPiece(tempX, tempY);
        } else {
            panel[y][x] = -1;

            drawWhitePiece(tempX, tempY);
        }
        if (myTurn) {
            onDrawPieceListener.point(x,y);
        }
        if (win(x, y) != 0) {
            listener.win(isBlack);
            finish = true;
        }
        if (!finish) {
            otherTrun();
        }
        isBlack = !isBlack;
    }

    private boolean myTurn = false;

    public void myFirst(boolean first) {
        myTurn = first;
    }

    public void otherTrun() {
        myTurn = !myTurn;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int c = e.getButton();
        System.out.println("x = " + e.getPoint().x + "   y = " + e.getPoint().y) ;
        if (!myTurn || finish) {
            return;
        }
        //左键按下
        if (c == MouseEvent.BUTTON1) {
            System.out.println("click");
            Point raw = e.getPoint();
            Point p = getStandardPx(raw.x, raw.y);
            if (p.x < 25 || p.y < 25)
                return;
            if (havePiece(p.x, p.y) || finish) {
                return;
            }
            drawRealPiece(p.x, p.y);
        }
    }

    public boolean getTurn() {
        return myTurn;
    }

}
